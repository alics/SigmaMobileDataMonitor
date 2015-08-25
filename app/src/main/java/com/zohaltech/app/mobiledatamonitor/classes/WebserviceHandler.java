package com.zohaltech.app.mobiledatamonitor.classes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebserviceHandler {

    private static final String SOAP_ACTION_VERIFICATION = "http://tempuri.org/MobileDataMonitorVerification";
    private static final String OPERATION_VERIFICATION   = "MobileDataMonitorVerification";
    private static final String SOAP_ACTION_PURCHASE     = "http://tempuri.org/PurchaseMobileDataMonitorApp";
    private static final String OPERATION_PURCHASE       = "PurchaseMobileDataMonitorApp";
    private static final String WSDL_TARGET_NAMESPACE    = "http://tempuri.org/";
    private static final String SOAP_ADDRESS             = "http://zohaltech.com/ValidateAppUsers.asmx?wsdl";

    private static String deviceId;
    private static String operatorName;

    static {
        deviceId = Helper.getDeviceId();
        operatorName = Helper.getOperator().toString();
    }

    public static String verify() {
        if (ConnectionManager.getInternetStatus() == ConnectionManager.InternetStatus.Connected) {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_VERIFICATION);
            request.addProperty("username", ConstantParams.USER_NAME);
            request.addProperty("password", ConstantParams.PASSWORD);
            request.addProperty("deviceId", deviceId);
            request.addProperty("operatorName", operatorName);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
        return null;
    }

    public static String purchase() {
        if (ConnectionManager.getInternetStatus() == ConnectionManager.InternetStatus.Connected) {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_PURCHASE);
            request.addProperty("username", ConstantParams.USER_NAME);
            request.addProperty("password", ConstantParams.PASSWORD);
            request.addProperty("deviceId", deviceId);

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
        return null;
    }
}
