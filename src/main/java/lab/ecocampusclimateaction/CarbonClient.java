/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.ecocampusclimateaction;

/**
 *
 * @author liuhailiang
 */

import generated.carbon.CarbonROIServiceGrpc;
import generated.carbon.CarbonROIServiceGrpc.CarbonROIServiceBlockingStub;
import generated.carbon.CarbonROIServiceGrpc.CarbonROIServiceStub;
import generated.carbon.CarbonReport;
import generated.carbon.ConsumptionSample;
import generated.carbon.RoiRequest;
import generated.carbon.RoiResult;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.jmdns.ServiceInfo;

public class CarbonClient {

    private static final Logger logger = Logger.getLogger(CarbonClient.class.getName());
    private static CarbonROIServiceStub asyncStub;
    private static CarbonROIServiceBlockingStub blockingStub;

    public static void main(String[] args) throws Exception {
        ExampleServiceDiscovery discovery = new ExampleServiceDiscovery(
                "_carbon._tcp.local.",
                "CarbonService"
        );

        ServiceInfo serviceInfo = discovery.discoverService(10000);

        if (serviceInfo == null) {
            System.out.println("Carbon service not found.");
            discovery.close();
            return;
        }

        String host = "localhost";

        int port = serviceInfo.getPort();

        System.out.println("Connecting to Carbon service at " + host + ":" + port);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        asyncStub = CarbonROIServiceGrpc.newStub(channel);
        blockingStub = CarbonROIServiceGrpc.newBlockingStub(channel);

        try {
            uploadConsumptionSamples();
            requestROI();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            try {
                discovery.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void uploadConsumptionSamples() {
        System.out.println("===== CARBON CLIENT STREAMING RESPONSE =====");

        StreamObserver<CarbonReport> responseObserver = new StreamObserver<CarbonReport>() {

            @Override
            public void onNext(CarbonReport response) {
                System.out.println("Total kgCO2e: " + response.getKgco2ETotal());
                System.out.println("Cost Amount: " + response.getCostAmount());
                System.out.println("Currency: " + response.getCurrency());
                System.out.println("Insights: " + response.getInsightsList());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("===== CARBON REMOTE ERROR DEMO =====");
                System.out.println("UploadConsumption error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Carbon upload completed.");
                System.out.println();
            }
        };

        StreamObserver<ConsumptionSample> requestObserver = asyncStub.uploadConsumption(responseObserver);

        try {
            requestObserver.onNext(ConsumptionSample.newBuilder()
                    .setTsEpochMs(System.currentTimeMillis())
                    .setKwh(12.5)
                    .setMeterId("METER-01")
                    .build());
            System.out.println("Client sent sample 1");
            Thread.sleep(500);

            requestObserver.onNext(ConsumptionSample.newBuilder()
                    .setTsEpochMs(System.currentTimeMillis())
                    .setKwh(10.0)
                    .setMeterId("METER-01")
                    .build());
            System.out.println("Client sent sample 2");
            Thread.sleep(500);

            requestObserver.onNext(ConsumptionSample.newBuilder()
                    .setTsEpochMs(System.currentTimeMillis())
                    .setKwh(0.0)
                    .setMeterId("METER-01")
                    .build());
            System.out.println("Client sent sample 3");
            Thread.sleep(500);

            requestObserver.onCompleted();

            Thread.sleep(3000);

        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void requestROI() {
        RoiRequest request = RoiRequest.newBuilder()
                .setBuildingId("Engineering_Block")
                .setTariffPerKwh(0.25)
                .setEmissionFactorKgco2EPerKwh(0.35)
                .setExpectedSavingsPct(12.0)
                .build();

        RoiResult response = blockingStub.calculateROI(request);

        System.out.println("===== CARBON UNARY RESPONSE =====");
        System.out.println("Annual Cost Saving: " + response.getAnnualCostSaving());
        System.out.println("Annual kgCO2e Reduction: " + response.getAnnualKgco2EReduction());
        System.out.println("Payback Years: " + response.getPaybackYears());
    }
}