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
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CarbonClient {

    private static final Logger logger = Logger.getLogger(CarbonClient.class.getName());
    private static CarbonROIServiceStub asyncStub;
    private static CarbonROIServiceBlockingStub blockingStub;

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 50053;

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        asyncStub = CarbonROIServiceGrpc.newStub(channel);
        blockingStub = CarbonROIServiceGrpc.newBlockingStub(channel);

        try {
            uploadConsumptionSamples();
            requestROI();

            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
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
                System.out.println("UploadConsumption error: " + t.getMessage());
                t.printStackTrace();
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
                    .setKwh(15.0)
                    .setMeterId("METER-01")
                    .build());
            System.out.println("Client sent sample 2");
            Thread.sleep(500);

            requestObserver.onNext(ConsumptionSample.newBuilder()
                    .setTsEpochMs(System.currentTimeMillis())
                    .setKwh(10.0)
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
