package com.manna.temperatureReceiver;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.util.ByteUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ZNetReceiverExample {

    //private final static Logger log = Logger.getLogger(ZNetReceiverExample.class);

    private ZNetReceiverExample() throws Exception {
        XBee xbee = new XBee();

        try {
            // replace with the com port of your receiving XBee (typically your end device)
            // router
            xbee.open("/dev/ttyUSB0", 9600);

            while (true) {

                try {
                    // we wait here until a packet is received.
                    XBeeResponse response = xbee.getResponse();

                    System.out.println("received response " + response.toString());

                    if (response.getApiId() == ApiId.ZNET_RX_RESPONSE) {
                        // we received a packet from ZNetSenderTest.java
                        ZNetRxResponse rx = (ZNetRxResponse) response;

//                        System.out.println("Received RX packet, option is " + rx.getOption() + ", sender 64 address is " + ByteUtils.toBase16(rx.getRemoteAddress64().getAddress()) + ", remote 16-bit address is " + ByteUtils.toBase16(rx.getRemoteAddress16().getAddress()) + ", data is " + ByteUtils.toBase16(rx.getData()));

                        int toFloat = (rx.getData()[3] << 24)  | (rx.getData()[2] << 16)  | (rx.getData()[1] << 8) | rx.getData()[0];
                        System.out.println("Teste :" + Float.intBitsToFloat(toFloat));

                        // optionally we may want to get the signal strength (RSSI) of the last hop.
                        // keep in mind if you have routers in your network, this will be the signal of the last hop.
                        AtCommand at = new AtCommand("DB");
                        xbee.sendAsynchronous(at);
                        XBeeResponse atResponse = xbee.getResponse();

                        if (atResponse.getApiId() == ApiId.AT_RESPONSE) {
                            // remember rssi is a negative db value
                            System.out.println("RSSI of last response is " + -((AtCommandResponse) atResponse).getValue()[0]);
                        } else {
                            // we didn't get an AT response
                            System.out.println("expected RSSI, but received " + atResponse.toString());
                        }
                    } else {
                        System.out.println("received unexpected packet " + response.toString());
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } finally {
            if (xbee != null && xbee.isConnected()) {
                xbee.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // init log4j
//        PropertyConfigurator.configure("log4j.properties");
        new ZNetReceiverExample();
    }
}

