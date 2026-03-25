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
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class ExampleServiceRegistration {

    private static JmDNS jmdns;
    private static ExampleServiceRegistration theRegister;

    private ExampleServiceRegistration() throws IOException {
        jmdns = JmDNS.create();
        System.out.println("jmDNS registration started");
    }

    public static ExampleServiceRegistration getInstance() throws IOException {
        if (theRegister == null) {
            theRegister = new ExampleServiceRegistration();
        }
        return theRegister;
    }

    public void registerService(String type, String name, int port, String text) throws IOException {
        ServiceInfo serviceInfo = ServiceInfo.create(type, name, port, text);
        jmdns.registerService(serviceInfo);
        System.out.println("Registered Service " + serviceInfo.toString());
    }
}