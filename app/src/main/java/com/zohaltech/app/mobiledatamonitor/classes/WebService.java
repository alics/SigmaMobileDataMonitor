package com.zohaltech.app.mobiledatamonitor.classes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {
    public static String login(String value) {
        try {
            String json = "{\"appkey\":\"123456987\",\"us\":\"%s\",\"ps\":\"%s\"}";
            if (ConnectionManager.getNetworkStatus() == ConnectionManager.NetworkStatus.Connected) {
                SoapObject request = new SoapObject("urn:server", "login");
                HttpTransportSE androidHttpTransport = new HttpTransportSE("http://zohaltech.com/webservice.asmx?wsdl");
                request.addProperty("value", json);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = false;
                envelope.setOutputSoapObject(request);
                androidHttpTransport.call("urn:server#login", envelope);
                return envelope.getResponse().toString().trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
