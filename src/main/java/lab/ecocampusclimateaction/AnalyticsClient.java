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
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.ServiceInfo;

public class AnalyticsClient {

    private static final Logger logger = Logger.getLogger(AnalyticsClient.class.getName());

    private static AnalyticsAlertServiceBlockingStub blockingStub;
    private static AnalyticsAlertServiceStub asyncStub;

    public static void main(String[] args) throws Exception {
        ExampleServiceDiscovery discovery = new ExampleServiceDiscovery(
                "_analytics._tcp.local.",
                "AnalyticsService"
        );

        ServiceInfo serviceInfo = discovery.discoverService(10000);

        if (serviceInfo == null) {
            System.out.println("Analytics service not found.");
            discovery.close();
            return;
        }

        String host = "localhost";

        int port = serviceInfo.getPort();

        System.out.println("Connecting to Analytics service at " + host + ":" + port);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        blockingStub = AnalyticsAlertServiceGrpc.newBlockingStub(channel);
        asyncStub = AnalyticsAlertServiceGrpc.newStub(channel);

        try {
            requestBaseline();
            runMitigationLoop();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        } finally {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            try {
                discovery.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    try {
        BaselineRequest invalidRequest = BaselineRequest.newBuilder()
                .setBuildingId("")
                .setTimeRange("LAST_7_DAYS")
                .build();

        blockingStub.getBaseline(invalidRequest);

    } catch (StatusRuntimeException e) {
        System.out.println("===== ANALYTICS REMOTE ERROR DEMO =====");
        System.out.println("Error Code: " + e.getStatus().getCode());
        System.out.println("Description: " + e.getStatus().getDescription());
        System.out.println();
    }
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
                if (t instanceof StatusRuntimeException) {
                    StatusRuntimeException e = (StatusRuntimeException) t;
                    System.out.println("===== ANALYTICS BIDI ERROR DEMO =====");
                    System.out.println("Error Code: " + e.getStatus().getCode());
                    System.out.println("Description: " + e.getStatus().getDescription());
                } else {
                    t.printStackTrace();
                }
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
                    .setHvacSetpointC(30.0)
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