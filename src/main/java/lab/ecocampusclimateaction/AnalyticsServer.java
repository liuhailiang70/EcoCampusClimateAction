/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.ecocampusclimateaction;

/**
 *
 * @author liuhailiang
 */


import generated.analytics.AnalyticsAlertServiceGrpc.AnalyticsAlertServiceImplBase;
import generated.analytics.BaselineModel;
import generated.analytics.BaselineRequest;
import generated.analytics.MitigationCommand;
import generated.analytics.MitigationFeedback;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class AnalyticsServer extends AnalyticsAlertServiceImplBase {

    private static final Logger logger = Logger.getLogger(AnalyticsServer.class.getName());

    public static void main(String[] args) {
        AnalyticsServer analyticsServer = new AnalyticsServer();
        int port = 50052;

        try {
            Server server = ServerBuilder.forPort(port)
                    .addService(analyticsServer)
                    .build()
                    .start();

            logger.info("Analytics server started, listening on " + port);
            System.out.println("***** Analytics server started, listening on " + port);

            ExampleServiceRegistration serviceRegistration = ExampleServiceRegistration.getInstance();
            serviceRegistration.registerService(
                    "_analytics._tcp.local.",
                    "AnalyticsService",
                    port,
                    "gRPC analytics service"
            );

            server.awaitTermination();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBaseline(BaselineRequest request, StreamObserver<BaselineModel> responseObserver) {

        if (request.getBuildingId() == null || request.getBuildingId().trim().isEmpty()) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("buildingId is required")
                            .asRuntimeException()
            );
            return;
        }

        if (request.getTimeRange() == null || request.getTimeRange().trim().isEmpty()) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("timeRange is required")
                            .asRuntimeException()
            );
            return;
        }

        System.out.println("Received getBaseline request for building: " + request.getBuildingId());

        BaselineModel reply = BaselineModel.newBuilder()
                .setBuildingId(request.getBuildingId())
                .setExpectedKwLoad(35.0)
                .setStatus("BASELINE_READY")
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<MitigationCommand> mitigationLoop(StreamObserver<MitigationFeedback> responseObserver) {

        AtomicBoolean streamClosed = new AtomicBoolean(false);

        return new StreamObserver<MitigationCommand>() {

            @Override
            public void onNext(MitigationCommand request) {

                if (streamClosed.get()) {
                    return;
                }

                if (request.getBuildingId() == null || request.getBuildingId().trim().isEmpty()) {
                    streamClosed.set(true);
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("buildingId is required")
                                    .asRuntimeException()
                    );
                    return;
                }

                if (request.getPolicyType() == null || request.getPolicyType().trim().isEmpty()) {
                    streamClosed.set(true);
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("policyType is required")
                                    .asRuntimeException()
                    );
                    return;
                }

                if (!request.getPolicyType().equals("HVAC_SETPOINT")) {
                    streamClosed.set(true);
                    responseObserver.onError(
                            Status.UNIMPLEMENTED
                                    .withDescription("Unsupported policyType: " + request.getPolicyType())
                                    .asRuntimeException()
                    );
                    return;
                }

                if (request.getDurationMin() <= 0) {
                    streamClosed.set(true);
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("durationMin must be greater than 0")
                                    .asRuntimeException()
                    );
                    return;
                }

                if (request.getHvacSetpointC() < 16 || request.getHvacSetpointC() > 26) {
                    streamClosed.set(true);
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("hvacSetpointC must be between 16 and 26")
                                    .asRuntimeException()
                    );
                    return;
                }

                System.out.println("Received mitigation command for building: "
                        + request.getBuildingId()
                        + ", policy: " + request.getPolicyType()
                        + ", hvac: " + request.getHvacSetpointC());

                MitigationFeedback feedback = MitigationFeedback.newBuilder()
                        .setApplied(true)
                        .setMode(request.getPolicyType())
                        .setReason("Command accepted")
                        .setEstKwReduction(2.5)
                        .build();

                responseObserver.onNext(feedback);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("mitigationLoop error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                if (!streamClosed.get()) {
                    System.out.println("mitigationLoop completed.");
                    responseObserver.onCompleted();
                }
            }
        };
    }
}