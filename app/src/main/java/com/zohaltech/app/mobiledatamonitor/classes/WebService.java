package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.DownloadManager;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Webservice {

    private static final String SOAP_ACTION_VERIFICATION = "http://tempuri.org/MobileDataMonitorVerification";
    private static final String OPERATION_VERIFICATION = "MobileDataMonitorVerification";
    private static final String SOAP_ACTION_PURCHASE = "http://tempuri.org/PurchaseMobileDataMonitorApp";
    private static final String OPERATION_PURCHASE = "PurchaseMobileDataMonitorApp";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ADDRESS = "http://192.168.0.100:80/ValidateAppUsers.asmx";
    private static final String USER_NAME = "zohaltech-cs";
    private static final String PASSWORD = "zoha@ltech8113";

    public Webservice() {
    }

    public static String verify(String androidId, String operatorName) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_VERIFICATION);
        request.addProperty("username", USER_NAME);
        request.addProperty("password", PASSWORD);
        request.addProperty("username", USER_NAME);
        request.addProperty("androidId", androidId);
        request.addProperty("operatorName", operatorName);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Object response = null;
        try {
            httpTransport.call(SOAP_ACTION_VERIFICATION, envelope);
            response = envelope.getResponse();
        } catch (Exception exception) {
            response = exception.toString();
        }
        return response.toString();
    }

    public static String purchase(String androidId) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_PURCHASE);
        request.addProperty("username", USER_NAME);
        request.addProperty("password", PASSWORD);
        request.addProperty("username", USER_NAME);
        request.addProperty("androidId", androidId);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Object response = null;
        try {
            httpTransport.call(SOAP_ACTION_PURCHASE, envelope);
            response = envelope.getResponse();
        } catch (Exception exception) {
            response = exception.toString();
        }
        return response.toString();
    }

}
