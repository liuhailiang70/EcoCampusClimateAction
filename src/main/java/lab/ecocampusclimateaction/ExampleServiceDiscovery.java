/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.ecocampusclimateaction;

/**
 *
 * @author liuhailiang
 */

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

public class ExampleServiceDiscovery {

    private String requiredServiceType;
    private String requiredServiceName;
    private ServiceInfo foundService;
    private JmDNS jmdns;

    public ExampleServiceDiscovery(String inServiceType, String inServiceName) {
        this.requiredServiceType = inServiceType;
        this.requiredServiceName = inServiceName;
    }

    public ServiceInfo discoverService(long timeoutMilliseconds) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        try {
            jmdns = JmDNS.create();
            System.out.println("jmDNS discovery started");

            jmdns.addServiceListener(requiredServiceType, new ServiceListener() {

                @Override
                public void serviceAdded(ServiceEvent event) {
                    System.out.println("Service added: " + event.getName());
                    jmdns.requestServiceInfo(event.getType(), event.getName(), 2000);
                }

                @Override
                public void serviceRemoved(ServiceEvent event) {
                    System.out.println("Service removed: " + event.getInfo());
                }

                @Override
                public void serviceResolved(ServiceEvent event) {
                    System.out.println("Service resolved: " + event.getInfo());

                    ServiceInfo serviceInfo = event.getInfo();
                    String resolvedServiceName = serviceInfo.getName();

                    System.out.println("#### Service " + resolvedServiceName
                            + " resolved at port: " + serviceInfo.getPort());

                    if (resolvedServiceName.equals(requiredServiceName)) {
                        foundService = serviceInfo;
                        latch.countDown();
                    }
                }
            });

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        latch.await(timeoutMilliseconds, TimeUnit.MILLISECONDS);
        System.out.println("Discover Service returning: " + foundService);
        return foundService;
    }

    public void close() throws IOException {
        if (jmdns != null) {
            jmdns.close();
        }
    }
}