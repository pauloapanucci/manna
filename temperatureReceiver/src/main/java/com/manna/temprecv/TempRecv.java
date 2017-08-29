package com.manna.temprecv;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.ubidots.ApiClient;
import com.ubidots.DataSource;
import com.ubidots.Variable;

public class TempRecv {
    private TempRecv() {
        XBee xbee = new XBee();

        int version = 1;
        System.out.println("VERSION: " + version);

        try {
            xbee.open("/dev/ttyUSB0", 9600);

            ApiClient api;
            DataSource dataSource = null;
            Variable variable = null;

            try {
                api = new ApiClient().fromToken("A1E-etVdy6HQOYuHhw9EkdAMyb9hTt3R9C");
                dataSource = api.getDataSource("599f17b8c03f9762237f5acc");
                variable = api.getVariable("59a053a17625427aa3dbb7a8");
            } catch (Exception e) {
                // ERRO DE INTERNET OU CONEXÃO
                System.err.println("Não foi possível fazer alguma coisa no UBIDOTS!");
                e.printStackTrace();
            } finally {
                while (true) {
                    try {
                        XBeeResponse response = xbee.getResponse();
                        if (response.getApiId() == ApiId.ZNET_RX_RESPONSE) {
                            ZNetRxResponse rx = (ZNetRxResponse) response;

                            int toFloat = (rx.getData()[3] << 24) | (rx.getData()[2] << 16) | (rx.getData()[1] << 8) | rx.getData()[0];
                            double readValue = (double) Float.intBitsToFloat(toFloat);

                            if (dataSource != null && variable != null) {
                                System.out.println("READ+POST to [" + dataSource.getName() + ":" + variable.getName() + "] '" + readValue + "'");
                                variable.saveValue(readValue);
                            } else {
                                System.out.println("READ '" + readValue + "'");
                            }

                            AtCommand at = new AtCommand("DB");
                            xbee.sendAsynchronous(at);
                            XBeeResponse atResponse = xbee.getResponse();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (XBeeException e) {
            // ERRO DE CONEXÃO COM O XBEE
            e.printStackTrace();
        } finally {
            if (xbee != null && xbee.isConnected()) {
                xbee.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new TempRecv();
    }
}

