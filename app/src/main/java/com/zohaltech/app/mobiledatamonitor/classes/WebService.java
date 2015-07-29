package com.zohaltech.app.mobiledatamonitor.classes;

import android.provider.Settings;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {

    private static final String SOAP_ACTION_VERIFICATION = "http://tempuri.org/MobileDataMonitorVerification";
    private static final String OPERATION_VERIFICATION   = "MobileDataMonitorVerification";
    private static final String SOAP_ACTION_PURCHASE     = "http://tempuri.org/PurchaseMobileDataMonitorApp";
    private static final String OPERATION_PURCHASE       = "PurchaseMobileDataMonitorApp";
    private static final String WSDL_TARGET_NAMESPACE    = "http://tempuri.org/";
    private static final String SOAP_ADDRESS             = "http://zohaltech.com/ValidateAppUsers.asmx?wsdl";
    private static final String USER_NAME                = "zohaltech-cs";
    private static final String PASSWORD                 = "zoha@ltech8113";

    private static String androidId;
    private static String operatorName;

    static {
        androidId = Settings.Secure.getString(App.context.getContentResolver(), Settings.Secure.ANDROID_ID);
        operatorName = Helper.getOperator().toString();
    }

    public static String verify() {
        if (ConnectionManager.getNetworkStatus() == ConnectionManager.NetworkStatus.Connected) {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_VERIFICATION);
            request.addProperty("username", USER_NAME);
            request.addProperty("password", PASSWORD);
            request.addProperty("androidId", androidId);
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
        if (ConnectionManager.getNetworkStatus() == ConnectionManager.NetworkStatus.Connected) {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_PURCHASE);
            request.addProperty("username", USER_NAME);
            request.addProperty("password", PASSWORD);
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
        return null;
    }
}
