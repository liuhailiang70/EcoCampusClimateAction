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
import generated.telemetry.TelemetryServiceGrpc;
import generated.telemetry.TelemetryServiceGrpc.TelemetryServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelemetryClient {

    private static final Logger logger = Logger.getLogger(TelemetryClient.class.getName());

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 50051;

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        TelemetryServiceBlockingStub blockingStub = TelemetryServiceGrpc.newBlockingStub(channel);

        try {
            // Unary call
            GetSnapshotRequest snapshotRequest = GetSnapshotRequest.newBuilder()
                    .setMeterId("METER-01")
                    .build();

            LoadSnapshot snapshotResponse = blockingStub.getSnapshot(snapshotRequest);

            System.out.println("===== UNARY RESPONSE =====");
            System.out.println("Meter ID: " + snapshotResponse.getMeterId());
            System.out.println("Building ID: " + snapshotResponse.getBuildingId());
            System.out.println("kW Load: " + snapshotResponse.getKwLoad());
            System.out.println("Timestamp: " + snapshotResponse.getTimestamp());
            System.out.println("Status: " + snapshotResponse.getStatus());

            // Server streaming call
            StreamLoadRequest streamRequest = StreamLoadRequest.newBuilder()
                    .setMeterId("METER-01")
                    .setNumberOfSamples(5)
                    .build();

            Iterator<LoadSample> samples = blockingStub.streamLoad(streamRequest);

            System.out.println();
            System.out.println("===== SERVER STREAMING RESPONSES =====");

            while (samples.hasNext()) {
                LoadSample sample = samples.next();
                System.out.println("Meter ID: " + sample.getMeterId()
                        + ", kW Load: " + sample.getKwLoad()
                        + ", Timestamp: " + sample.getTimestamp());
            }

        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        } finally {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}