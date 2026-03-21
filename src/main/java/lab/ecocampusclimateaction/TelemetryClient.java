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
import generated.telemetry.LoadSnapshot;
import generated.telemetry.TelemetryServiceGrpc;
import generated.telemetry.TelemetryServiceGrpc.TelemetryServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.grpc.StatusRuntimeException;

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
            GetSnapshotRequest request = GetSnapshotRequest.newBuilder()
                    .setMeterId("METER-01")
                    .build();

            LoadSnapshot response = blockingStub.getSnapshot(request);

            System.out.println("Meter ID: " + response.getMeterId());
            System.out.println("Building ID: " + response.getBuildingId());
            System.out.println("kW Load: " + response.getKwLoad());
            System.out.println("Timestamp: " + response.getTimestamp());
            System.out.println("Status: " + response.getStatus());

        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        } finally {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
