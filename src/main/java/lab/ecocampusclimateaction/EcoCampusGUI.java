/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.ecocampusclimateaction;

/**
 *
 * @author liuhailiang
 */

import generated.analytics.AnalyticsAlertServiceGrpc;
import generated.analytics.AnalyticsAlertServiceGrpc.AnalyticsAlertServiceBlockingStub;
import generated.analytics.AnalyticsAlertServiceGrpc.AnalyticsAlertServiceStub;
import generated.analytics.BaselineModel;
import generated.analytics.BaselineRequest;
import generated.analytics.MitigationCommand;
import generated.analytics.MitigationFeedback;
import generated.carbon.CarbonROIServiceGrpc;
import generated.carbon.CarbonROIServiceGrpc.CarbonROIServiceBlockingStub;
import generated.carbon.CarbonROIServiceGrpc.CarbonROIServiceStub;
import generated.carbon.CarbonReport;
import generated.carbon.ConsumptionSample;
import generated.carbon.RoiRequest;
import generated.carbon.RoiResult;
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
import io.grpc.stub.StreamObserver;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.jmdns.ServiceInfo;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class EcoCampusGUI extends JFrame {

    private final JTextArea outputArea;

    private final JButton telemetrySnapshotButton;
    private final JButton telemetryStreamButton;
    private final JButton telemetryConfigButton;

    private final JButton analyticsBaselineButton;
    private final JButton analyticsMitigationButton;

    private final JButton carbonUploadButton;
    private final JButton carbonRoiButton;

    public EcoCampusGUI() {
        setTitle("EcoCampus Climate Action Platform");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        telemetrySnapshotButton = new JButton("Telemetry - Get Snapshot");
        telemetryStreamButton = new JButton("Telemetry - Stream Load");
        telemetryConfigButton = new JButton("Telemetry - Update Config");

        analyticsBaselineButton = new JButton("Analytics - Get Baseline");
        analyticsMitigationButton = new JButton("Analytics - Mitigation Loop");

        carbonUploadButton = new JButton("Carbon - Upload Consumption");
        carbonRoiButton = new JButton("Carbon - Calculate ROI");

        JPanel topPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Service Controls"));

        JPanel telemetryPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        telemetryPanel.setBorder(BorderFactory.createTitledBorder("Telemetry Service"));
        telemetryPanel.add(telemetrySnapshotButton);
        telemetryPanel.add(telemetryStreamButton);
        telemetryPanel.add(telemetryConfigButton);

        JPanel analyticsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        analyticsPanel.setBorder(BorderFactory.createTitledBorder("Analytics Service"));
        analyticsPanel.add(analyticsBaselineButton);
        analyticsPanel.add(analyticsMitigationButton);

        JPanel carbonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        carbonPanel.setBorder(BorderFactory.createTitledBorder("Carbon Service"));
        carbonPanel.add(carbonUploadButton);
        carbonPanel.add(carbonRoiButton);

        topPanel.add(telemetryPanel);
        topPanel.add(analyticsPanel);
        topPanel.add(carbonPanel);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(850, 400));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        wireActions();
    }

    private void wireActions() {
        telemetrySnapshotButton.addActionListener(e ->
                runTask("Telemetry Get Snapshot", this::callTelemetrySnapshot));

        telemetryStreamButton.addActionListener(e ->
                runTask("Telemetry Stream Load", this::callTelemetryStream));

        telemetryConfigButton.addActionListener(e ->
                runTask("Telemetry Update Config", this::callTelemetryConfig));

        analyticsBaselineButton.addActionListener(e ->
                runTask("Analytics Get Baseline", this::callAnalyticsBaseline));

        analyticsMitigationButton.addActionListener(e ->
                runTask("Analytics Mitigation Loop", this::callAnalyticsMitigation));

        carbonUploadButton.addActionListener(e ->
                runTask("Carbon Upload Consumption", this::callCarbonUpload));

        carbonRoiButton.addActionListener(e ->
                runTask("Carbon Calculate ROI", this::callCarbonRoi));
    }

    private void runTask(String taskName, Runnable task) {
        appendOutput("\n==============================");
        appendOutput("Running: " + taskName);
        appendOutput("==============================");

        new Thread(() -> {
            try {
                task.run();
            } catch (Exception ex) {
                appendOutput("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }).start();
    }

    private void callTelemetrySnapshot() {
        ManagedChannel channel = null;
        try {
            int port = discoverPort("_telemetry._tcp.local.", "TelemetryService");

            channel = ManagedChannelBuilder
                    .forAddress("localhost", port)
                    .usePlaintext()
                    .build();

            TelemetryServiceBlockingStub blockingStub = TelemetryServiceGrpc.newBlockingStub(channel);

            GetSnapshotRequest request = GetSnapshotRequest.newBuilder()
                    .setMeterId("METER-01")
                    .build();

            LoadSnapshot response = blockingStub.getSnapshot(request);

            appendOutput("Meter ID: " + response.getMeterId());
            appendOutput("Building ID: " + response.getBuildingId());
            appendOutput("kW Load: " + response.getKwLoad());
            appendOutput("Timestamp: " + response.getTimestamp());
            appendOutput("Status: " + response.getStatus());

        } finally {
            shutdownChannel(channel);
        }
    }

    private void callTelemetryStream() {
        ManagedChannel channel = null;
        try {
            int port = discoverPort("_telemetry._tcp.local.", "TelemetryService");

            channel = ManagedChannelBuilder
                    .forAddress("localhost", port)
                    .usePlaintext()
                    .build();

            TelemetryServiceBlockingStub blockingStub = TelemetryServiceGrpc.newBlockingStub(channel);

            StreamLoadRequest request = StreamLoadRequest.newBuilder()
                    .setMeterId("METER-01")
                    .setNumberOfSamples(5)
                    .build();

            Iterator<LoadSample> samples = blockingStub.streamLoad(request);

            while (samples.hasNext()) {
                LoadSample sample = samples.next();
                appendOutput("Meter ID: " + sample.getMeterId()
                        + ", kW Load: " + sample.getKwLoad()
                        + ", Timestamp: " + sample.getTimestamp());
            }

        } finally {
            shutdownChannel(channel);
        }
    }

    private void callTelemetryConfig() {
        ManagedChannel channel = null;
        try {
            int port = discoverPort("_telemetry._tcp.local.", "TelemetryService");

            channel = ManagedChannelBuilder
                    .forAddress("localhost", port)
                    .usePlaintext()
                    .build();

            TelemetryServiceBlockingStub blockingStub = TelemetryServiceGrpc.newBlockingStub(channel);

            MeterConfigUpdate request = MeterConfigUpdate.newBuilder()
                    .setMeterId("METER-01")
                    .setIntervalSec(10)
                    .addEnabledChannels("power")
                    .addEnabledChannels("voltage")
                    .build();

            MeterConfigStatus response = blockingStub.updateMeterConfig(request);

            appendOutput("Applied: " + response.getApplied());
            appendOutput("Reason: " + response.getReason());
            appendOutput("Effective Interval Sec: " + response.getEffectiveIntervalSec());

        } finally {
            shutdownChannel(channel);
        }
    }

    private void callAnalyticsBaseline() {
        ManagedChannel channel = null;
        try {
            int port = discoverPort("_analytics._tcp.local.", "AnalyticsService");

            channel = ManagedChannelBuilder
                    .forAddress("localhost", port)
                    .usePlaintext()
                    .build();

            AnalyticsAlertServiceBlockingStub blockingStub =
                    AnalyticsAlertServiceGrpc.newBlockingStub(channel);

            BaselineRequest request = BaselineRequest.newBuilder()
                    .setBuildingId("Engineering_Block")
                    .setTimeRange("LAST_7_DAYS")
                    .build();

            BaselineModel response = blockingStub.getBaseline(request);

            appendOutput("Building ID: " + response.getBuildingId());
            appendOutput("Expected kW Load: " + response.getExpectedKwLoad());
            appendOutput("Status: " + response.getStatus());

        } finally {
            shutdownChannel(channel);
        }
    }

    private void callAnalyticsMitigation() {
        ManagedChannel channel = null;
        try {
            int port = discoverPort("_analytics._tcp.local.", "AnalyticsService");

            channel = ManagedChannelBuilder
                    .forAddress("localhost", port)
                    .usePlaintext()
                    .build();

            AnalyticsAlertServiceStub asyncStub = AnalyticsAlertServiceGrpc.newStub(channel);
            CountDownLatch latch = new CountDownLatch(1);

            StreamObserver<MitigationFeedback> responseObserver = new StreamObserver<MitigationFeedback>() {
                @Override
                public void onNext(MitigationFeedback response) {
                    appendOutput("Applied: " + response.getApplied()
                            + ", Mode: " + response.getMode()
                            + ", Reason: " + response.getReason()
                            + ", Estimated kW Reduction: " + response.getEstKwReduction());
                }

                @Override
                public void onError(Throwable t) {
                    appendOutput("Mitigation stream error: " + t.getMessage());
                    latch.countDown();
                }

                @Override
                public void onCompleted() {
                    appendOutput("Mitigation stream completed.");
                    latch.countDown();
                }
            };

            StreamObserver<MitigationCommand> requestObserver = asyncStub.mitigationLoop(responseObserver);

            requestObserver.onNext(MitigationCommand.newBuilder()
                    .setBuildingId("Engineering_Block")
                    .setPolicyType("HVAC_SETPOINT")
                    .setHvacSetpointC(22.0)
                    .setDurationMin(30)
                    .build());
            appendOutput("Client sent mitigation command: HVAC 22.0C");

            requestObserver.onNext(MitigationCommand.newBuilder()
                    .setBuildingId("Engineering_Block")
                    .setPolicyType("HVAC_SETPOINT")
                    .setHvacSetpointC(15.0)
                    .setDurationMin(20)
                    .build());
            appendOutput("Client sent mitigation command: HVAC 15.0C");

            requestObserver.onCompleted();
            latch.await(5, TimeUnit.SECONDS);

        } catch (Exception e) {
            appendOutput("Analytics mitigation error: " + e.getMessage());
        } finally {
            shutdownChannel(channel);
        }
    }

    private void callCarbonUpload() {
        ManagedChannel channel = null;
        try {
            int port = discoverPort("_carbon._tcp.local.", "CarbonService");

            channel = ManagedChannelBuilder
                    .forAddress("localhost", port)
                    .usePlaintext()
                    .build();

            CarbonROIServiceStub asyncStub = CarbonROIServiceGrpc.newStub(channel);
            CountDownLatch latch = new CountDownLatch(1);

            StreamObserver<CarbonReport> responseObserver = new StreamObserver<CarbonReport>() {
                @Override
                public void onNext(CarbonReport response) {
                    appendOutput("Total kgCO2e: " + response.getKgco2ETotal());
                    appendOutput("Cost Amount: " + response.getCostAmount());
                    appendOutput("Currency: " + response.getCurrency());
                    appendOutput("Insights: " + response.getInsightsList());
                }

                @Override
                public void onError(Throwable t) {
                    appendOutput("Carbon upload error: " + t.getMessage());
                    latch.countDown();
                }

                @Override
                public void onCompleted() {
                    appendOutput("Carbon upload completed.");
                    latch.countDown();
                }
            };

            StreamObserver<ConsumptionSample> requestObserver = asyncStub.uploadConsumption(responseObserver);

            requestObserver.onNext(ConsumptionSample.newBuilder()
                    .setTsEpochMs(System.currentTimeMillis())
                    .setKwh(12.5)
                    .setMeterId("METER-01")
                    .build());
            appendOutput("Client sent sample 1");

            requestObserver.onNext(ConsumptionSample.newBuilder()
                    .setTsEpochMs(System.currentTimeMillis())
                    .setKwh(15.0)
                    .setMeterId("METER-01")
                    .build());
            appendOutput("Client sent sample 2");

            requestObserver.onNext(ConsumptionSample.newBuilder()
                    .setTsEpochMs(System.currentTimeMillis())
                    .setKwh(10.0)
                    .setMeterId("METER-01")
                    .build());
            appendOutput("Client sent sample 3");

            requestObserver.onCompleted();
            latch.await(5, TimeUnit.SECONDS);

        } catch (Exception e) {
            appendOutput("Carbon upload error: " + e.getMessage());
        } finally {
            shutdownChannel(channel);
        }
    }

    private void callCarbonRoi() {
        ManagedChannel channel = null;
        try {
            int port = discoverPort("_carbon._tcp.local.", "CarbonService");

            channel = ManagedChannelBuilder
                    .forAddress("localhost", port)
                    .usePlaintext()
                    .build();

            CarbonROIServiceBlockingStub blockingStub = CarbonROIServiceGrpc.newBlockingStub(channel);

            RoiRequest request = RoiRequest.newBuilder()
                    .setBuildingId("Engineering_Block")
                    .setTariffPerKwh(0.25)
                    .setEmissionFactorKgco2EPerKwh(0.35)
                    .setExpectedSavingsPct(12.0)
                    .build();

            RoiResult response = blockingStub.calculateROI(request);

            appendOutput("Annual Cost Saving: " + response.getAnnualCostSaving());
            appendOutput("Annual kgCO2e Reduction: " + response.getAnnualKgco2EReduction());
            appendOutput("Payback Years: " + response.getPaybackYears());

        } finally {
            shutdownChannel(channel);
        }
    }

    private int discoverPort(String serviceType, String serviceName) {
        ExampleServiceDiscovery discovery = null;
        try {
            discovery = new ExampleServiceDiscovery(serviceType, serviceName);
            ServiceInfo serviceInfo = discovery.discoverService(10000);

            if (serviceInfo == null) {
                throw new RuntimeException("Service not found: " + serviceName);
            }

            return serviceInfo.getPort();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Service discovery interrupted: " + serviceName, e);
        } finally {
            if (discovery != null) {
                try {
                    discovery.close();
                } catch (IOException e) {
                    appendOutput("Discovery close error: " + e.getMessage());
                }
            }
        }
    }

    private void shutdownChannel(ManagedChannel channel) {
        if (channel != null) {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void appendOutput(String text) {
        SwingUtilities.invokeLater(() -> {
            outputArea.append(text + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EcoCampusGUI gui = new EcoCampusGUI();
            gui.setVisible(true);
        });
    }
}
