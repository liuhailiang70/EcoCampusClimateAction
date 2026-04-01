/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.ecocampusclimateaction;

/**
 *
 * @author liuhailiang
 */


import generated.telemetry.GetSnapshotRequest;
import generated.telemetry.LoadSample;
import generated.telemetry.LoadSnapshot;
import generated.telemetry.MeterConfigStatus;
import generated.telemetry.MeterConfigUpdate;
import generated.telemetry.StreamLoadRequest;
import generated.telemetry.TelemetryServiceGrpc.TelemetryServiceImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

public class TelemetryServer extends TelemetryServiceImplBase {

    private static final Logger logger = Logger.getLogger(TelemetryServer.class.getName());

    public static void main(String[] args) {
        TelemetryServer telemetryServer = new TelemetryServer();
        int port = 50051;

        try {
            Server server = ServerBuilder.forPort(port)
                    .addService(telemetryServer)
                    .build()
                    .start();

            logger.info("Telemetry server started, listening on " + port);
            System.out.println("***** Telemetry server started, listening on " + port);

            ExampleServiceRegistration serviceRegistration = ExampleServiceRegistration.getInstance();
            serviceRegistration.registerService(
                    "_telemetry._tcp.local.",
                    "TelemetryService",
                    port,
                    "gRPC telemetry service"
            );

            server.awaitTermination();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSnapshot(GetSnapshotRequest request, StreamObserver<LoadSnapshot> responseObserver) {

        if (request.getMeterId() == null || request.getMeterId().trim().isEmpty()) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("meterId is required")
                            .asRuntimeException()
            );
            return;
        }

        if (!request.getMeterId().equals("METER-01")) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription("Meter not found: " + request.getMeterId())
                            .asRuntimeException()
            );
            return;
        }

        System.out.println("Received getSnapshot request for meter: " + request.getMeterId());

        LoadSnapshot reply = LoadSnapshot.newBuilder()
                .setMeterId(request.getMeterId())
                .setBuildingId("Engineering_Block")
                .setKwLoad(42.5)
                .setTimestamp(System.currentTimeMillis())
                .setStatus("NORMAL")
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void streamLoad(StreamLoadRequest request, StreamObserver<LoadSample> responseObserver) {

        if (request.getMeterId() == null || request.getMeterId().trim().isEmpty()) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("meterId is required")
                            .asRuntimeException()
            );
            return;
        }

        if (request.getNumberOfSamples() <= 0 || request.getNumberOfSamples() > 10) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("numberOfSamples must be between 1 and 10")
                            .asRuntimeException()
            );
            return;
        }

        System.out.println("Received streamLoad request for meter: " + request.getMeterId());

        int count = request.getNumberOfSamples();

        for (int i = 1; i <= count; i++) {
            LoadSample sample = LoadSample.newBuilder()
                    .setMeterId(request.getMeterId())
                    .setKwLoad(40.0 + i)
                    .setTimestamp(System.currentTimeMillis())
                    .build();

            responseObserver.onNext(sample);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                responseObserver.onError(
                        Status.INTERNAL
                                .withDescription("Stream interrupted")
                                .withCause(e)
                                .asRuntimeException()
                );
                return;
            }
        }

        responseObserver.onCompleted();
    }

    @Override
    public void updateMeterConfig(MeterConfigUpdate request, StreamObserver<MeterConfigStatus> responseObserver) {

        if (request.getMeterId() == null || request.getMeterId().trim().isEmpty()) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("meterId is required")
                            .asRuntimeException()
            );
            return;
        }

        if (request.getIntervalSec() <= 0) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("intervalSec must be greater than 0")
                            .asRuntimeException()
            );
            return;
        }

        System.out.println("Received updateMeterConfig request for meter: " + request.getMeterId()
                + ", interval: " + request.getIntervalSec()
                + ", channels: " + request.getEnabledChannelsList());

        boolean applied = true;
        String reason = "Configuration applied successfully";
        int effectiveIntervalSec = request.getIntervalSec();

        if (request.getIntervalSec() < 5) {
            applied = false;
            reason = "Interval too low, minimum is 5 seconds";
            effectiveIntervalSec = 5;
        }

        MeterConfigStatus reply = MeterConfigStatus.newBuilder()
                .setApplied(applied)
                .setReason(reason)
                .setEffectiveIntervalSec(effectiveIntervalSec)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}

