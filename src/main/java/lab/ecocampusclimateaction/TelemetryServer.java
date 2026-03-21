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
import generated.telemetry.StreamLoadRequest;
import generated.telemetry.TelemetryServiceGrpc.TelemetryServiceImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
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

            server.awaitTermination();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSnapshot(GetSnapshotRequest request, StreamObserver<LoadSnapshot> responseObserver) {

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
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();
    }
}