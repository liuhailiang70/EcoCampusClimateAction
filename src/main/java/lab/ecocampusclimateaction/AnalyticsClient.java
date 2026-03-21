/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.ecocampusclimateaction;

/**
 *
 * @author liuhailiang
 */

import generated.analytics.AnalyticsAlertServiceGrpc;
import generated.analytics.AnalyticsAlertServiceGrpc.AnalyticsAlertServiceBlockingStub;
import generated.analytics.AnalyticsAlertServiceGrpc.AnalyticsAlertServiceStub;
import generated.analytics.BaselineModel;
import generated.analytics.BaselineRequest;
import generated.analytics.MitigationCommand;
import generated.analytics.MitigationFeedback;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyticsClient {

    private static final Logger logger = Logger.getLogger(AnalyticsClient.class.getName());

    private static AnalyticsAlertServiceBlockingStub blockingStub;
    private static AnalyticsAlertServiceStub asyncStub;

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 50052;

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        blockingStub = AnalyticsAlertServiceGrpc.newBlockingStub(channel);
        asyncStub = AnalyticsAlertServiceGrpc.newStub(channel);

        try {
            requestBaseline();
            runMitigationLoop();

            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
    }

    private static void requestBaseline() {
        BaselineRequest request = BaselineRequest.newBuilder()
                .setBuildingId("Engineering_Block")
                .setTimeRange("LAST_7_DAYS")
                .build();

        BaselineModel response = blockingStub.getBaseline(request);

        System.out.println("===== ANALYTICS UNARY RESPONSE =====");
        System.out.println("Building ID: " + response.getBuildingId());
        System.out.println("Expected kW Load: " + response.getExpectedKwLoad());
        System.out.println("Status: " + response.getStatus());
        System.out.println();
    }

    private static void runMitigationLoop() {
        System.out.println("===== ANALYTICS BIDI RESPONSES =====");

        StreamObserver<MitigationFeedback> responseObserver = new StreamObserver<MitigationFeedback>() {

            @Override
            public void onNext(MitigationFeedback response) {
                System.out.println("Applied: " + response.getApplied()
                        + ", Mode: " + response.getMode()
                        + ", Reason: " + response.getReason()
                        + ", Estimated kW Reduction: " + response.getEstKwReduction());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Mitigation stream completed.");
            }
        };

        StreamObserver<MitigationCommand> requestObserver = asyncStub.mitigationLoop(responseObserver);

        try {
            requestObserver.onNext(MitigationCommand.newBuilder()
                    .setBuildingId("Engineering_Block")
                    .setPolicyType("HVAC_SETPOINT")
                    .setHvacSetpointC(22.0)
                    .setDurationMin(30)
                    .build());
            System.out.println("Client sent mitigation command: HVAC 22.0C");
            Thread.sleep(500);

            requestObserver.onNext(MitigationCommand.newBuilder()
                    .setBuildingId("Engineering_Block")
                    .setPolicyType("HVAC_SETPOINT")
                    .setHvacSetpointC(15.0)
                    .setDurationMin(20)
                    .build());
            System.out.println("Client sent mitigation command: HVAC 15.0C");
            Thread.sleep(500);

            requestObserver.onCompleted();

            Thread.sleep(5000);

        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}