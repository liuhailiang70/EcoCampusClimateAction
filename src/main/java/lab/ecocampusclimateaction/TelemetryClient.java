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
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.ServiceInfo;

public class TelemetryClient {

    private static final Logger logger = Logger.getLogger(TelemetryClient.class.getName());

    public static void main(String[] args) throws Exception {
        ExampleServiceDiscovery discovery = new ExampleServiceDiscovery(
                "_telemetry._tcp.local.",
                "TelemetryService"
        );

        ServiceInfo serviceInfo = discovery.discoverService(10000);

        if (serviceInfo == null) {
            System.out.println("Telemetry service not found.");
            discovery.close();
            return;
        }

        String host = "localhost";

        int port = serviceInfo.getPort();

        System.out.println("Connecting to Telemetry service at " + host + ":" + port);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        TelemetryServiceBlockingStub blockingStub = TelemetryServiceGrpc.newBlockingStub(channel);

        try {
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
}