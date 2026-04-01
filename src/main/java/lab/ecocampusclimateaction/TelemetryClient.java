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
            // Unary call 1: GetSnapshot
            GetSnapshotRequest snapshotRequest = GetSnapshotRequest.newBuilder()
                    .setMeterId("METER-01")
                    .build();

            LoadSnapshot snapshotResponse = blockingStub.getSnapshot(snapshotRequest);

            System.out.println("===== TELEMETRY UNARY RESPONSE =====");
            System.out.println("Meter ID: " + snapshotResponse.getMeterId());
            System.out.println("Building ID: " + snapshotResponse.getBuildingId());
            System.out.println("kW Load: " + snapshotResponse.getKwLoad());
            System.out.println("Timestamp: " + snapshotResponse.getTimestamp());
            System.out.println("Status: " + snapshotResponse.getStatus());
            System.out.println();

            // Server streaming call: StreamLoad
            StreamLoadRequest streamRequest = StreamLoadRequest.newBuilder()
                    .setMeterId("METER-01")
                    .setNumberOfSamples(5)
                    .build();

            Iterator<LoadSample> samples = blockingStub.streamLoad(streamRequest);

            System.out.println("===== TELEMETRY SERVER STREAMING RESPONSES =====");

            while (samples.hasNext()) {
                LoadSample sample = samples.next();
                System.out.println("Meter ID: " + sample.getMeterId()
                        + ", kW Load: " + sample.getKwLoad()
                        + ", Timestamp: " + sample.getTimestamp());
            }

            System.out.println();

            // Unary call 2: UpdateMeterConfig
            MeterConfigUpdate configRequest = MeterConfigUpdate.newBuilder()
                    .setMeterId("METER-01")
                    .setIntervalSec(10)
                    .addEnabledChannels("power")
                    .addEnabledChannels("voltage")
                    .build();

            MeterConfigStatus configResponse = blockingStub.updateMeterConfig(configRequest);

            System.out.println("===== TELEMETRY CONFIG UPDATE RESPONSE =====");
            System.out.println("Applied: " + configResponse.getApplied());
            System.out.println("Reason: " + configResponse.getReason());
            System.out.println("Effective Interval Sec: " + configResponse.getEffectiveIntervalSec());
            System.out.println();

            // Remote error handling demo
            try {
                GetSnapshotRequest invalidRequest = GetSnapshotRequest.newBuilder()
                        .setMeterId("UNKNOWN-METER")
                        .build();

                blockingStub.getSnapshot(invalidRequest);

            } catch (StatusRuntimeException e) {
                System.out.println("===== TELEMETRY REMOTE ERROR DEMO =====");
                System.out.println("Error Code: " + e.getStatus().getCode());
                System.out.println("Description: " + e.getStatus().getDescription());
                System.out.println();
            }

            // Deadline demo
            try {
                TelemetryServiceBlockingStub deadlineStub = TelemetryServiceGrpc.newBlockingStub(channel)
                        .withDeadlineAfter(1, TimeUnit.SECONDS);

                StreamLoadRequest deadlineRequest = StreamLoadRequest.newBuilder()
                        .setMeterId("METER-01")
                        .setNumberOfSamples(5)
                        .build();

                Iterator<LoadSample> deadlineSamples = deadlineStub.streamLoad(deadlineRequest);

                System.out.println("===== TELEMETRY DEADLINE DEMO =====");
                while (deadlineSamples.hasNext()) {
                    LoadSample sample = deadlineSamples.next();
                    System.out.println("Meter ID: " + sample.getMeterId()
                            + ", kW Load: " + sample.getKwLoad()
                            + ", Timestamp: " + sample.getTimestamp());
                }

            } catch (StatusRuntimeException e) {
                System.out.println("===== TELEMETRY DEADLINE DEMO =====");
                System.out.println("Error Code: " + e.getStatus().getCode());
                System.out.println("Description: " + e.getStatus().getDescription());
                System.out.println();
            }

        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING,
                    "RPC failed: code={0}, description={1}",
                    new Object[]{e.getStatus().getCode(), e.getStatus().getDescription()});
        } finally {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}