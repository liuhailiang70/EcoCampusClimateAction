/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.ecocampusclimateaction;

/**
 *
 * @author liuhailiang
 */

import generated.carbon.CarbonROIServiceGrpc.CarbonROIServiceImplBase;
import generated.carbon.CarbonReport;
import generated.carbon.ConsumptionSample;
import generated.carbon.RoiRequest;
import generated.carbon.RoiResult;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

public class CarbonServer extends CarbonROIServiceImplBase {

    private static final Logger logger = Logger.getLogger(CarbonServer.class.getName());

    public static void main(String[] args) {
        CarbonServer carbonServer = new CarbonServer();
        int port = 50053;

        try {
            Server server = ServerBuilder.forPort(port)
                    .addService(carbonServer)
                    .build()
                    .start();

            logger.info("Carbon server started, listening on " + port);
            System.out.println("***** Carbon server started, listening on " + port);

            ExampleServiceRegistration serviceRegistration = ExampleServiceRegistration.getInstance();
            serviceRegistration.registerService(
                    "_carbon._tcp.local.",
                    "CarbonService",
                    port,
                    "gRPC carbon service"
            );

            server.awaitTermination();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public StreamObserver<ConsumptionSample> uploadConsumption(StreamObserver<CarbonReport> responseObserver) {

        return new StreamObserver<ConsumptionSample>() {

            int count = 0;
            double totalKwh = 0.0;
            String lastMeterId = "";

            @Override
            public void onNext(ConsumptionSample request) {
                System.out.println("Received consumption sample from meter: "
                        + request.getMeterId()
                        + ", kWh: " + request.getKwh());

                count++;
                totalKwh += request.getKwh();
                lastMeterId = request.getMeterId();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("uploadConsumption error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                double emissionFactor = 0.35;
                double tariff = 0.25;

                double kgco2eTotal = totalKwh * emissionFactor;
                double costAmount = totalKwh * tariff;

                CarbonReport reply = CarbonReport.newBuilder()
                        .setKgco2ETotal(kgco2eTotal)
                        .setCostAmount(costAmount)
                        .setCurrency("EUR")
                        .addInsights("Samples received: " + count)
                        .addInsights("Last meter: " + lastMeterId)
                        .addInsights("Upload completed successfully")
                        .build();

                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void calculateROI(RoiRequest request, StreamObserver<RoiResult> responseObserver) {
        RoiResult reply = RoiResult.newBuilder()
                .setAnnualCostSaving(1200.0)
                .setAnnualKgco2EReduction(850.0)
                .setPaybackYears(3.5)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}