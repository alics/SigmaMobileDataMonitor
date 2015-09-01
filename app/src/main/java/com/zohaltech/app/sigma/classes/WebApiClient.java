package com.zohaltech.app.sigma.classes;

import android.os.Build;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;



public class WebApiClient {

    public enum PostAction {
        INSTALL,
        REGISTER
    }

    public static final  String SUCCESS_INSTALL  = "SUCCESS_INSTALL";
    public static final  String SUCCESS_REGISTER = "SUCCESS_REGISTER";
    private static final String HOST_URL         = "http://zohaltech.com/api/app/post";

    private JSONObject jsonObject;

    private JSONObject getJsonObject() {
        return jsonObject;
    }

    private void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void postSubscriberData(final PostAction action) {
        if (action == PostAction.INSTALL) {
            if (!App.preferences.getBoolean(WebApiClient.SUCCESS_INSTALL, false)) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            if (ConnectionManager.getInternetStatus() == ConnectionManager.InternetStatus.Connected) {
                                jsonObject.accumulate("AppId", 1);
                                jsonObject.accumulate("DeviceId", Helper.getDeviceId());
                                jsonObject.accumulate("DeviceBrand", Build.MANUFACTURER);
                                jsonObject.accumulate("DeviceModel", Build.MODEL);
                                jsonObject.accumulate("AndroidVersion", Build.VERSION.RELEASE);
                                jsonObject.accumulate("ApiVersion", Build.VERSION.SDK_INT);
                                jsonObject.accumulate("OperatorId", Helper.getOperator().ordinal());
                                jsonObject.accumulate("IsPurchased", false);
                                setJsonObject(jsonObject);
                                Boolean result = post(getJsonObject());
                                App.preferences.edit().putBoolean(SUCCESS_INSTALL, result).apply();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        } else {
            if (!App.preferences.getBoolean(WebApiClient.SUCCESS_REGISTER, false)) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            if (ConnectionManager.getInternetStatus() == ConnectionManager.InternetStatus.Connected) {
                                jsonObject.accumulate("AppId", 1);
                                jsonObject.accumulate("DeviceId", Helper.getDeviceId());
                                jsonObject.accumulate("DeviceBrand", Build.MANUFACTURER);
                                jsonObject.accumulate("DeviceModel", Build.MODEL);
                                jsonObject.accumulate("AndroidVersion", Build.VERSION.RELEASE);
                                jsonObject.accumulate("ApiVersion", Build.VERSION.SDK_INT);
                                jsonObject.accumulate("OperatorId", Helper.getOperator().ordinal());
                                jsonObject.accumulate("IsPurchased", true);
                                setJsonObject(jsonObject);
                                Boolean result = post(getJsonObject());
                                App.preferences.edit().putBoolean(SUCCESS_REGISTER, result).apply();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        }

    }

    private Boolean post(JSONObject jsonObject) {
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(HOST_URL);
            String json = "";
            // 3. build jsonObject
            //JSONObject jsonObject = getJsonObject();

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);
            // 5. set json to StringEntity
            StringEntity stringEntity = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(stringEntity);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            // httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            StatusLine statusLine = httpResponse.getStatusLine();
            int resultCode = statusLine.getStatusCode();
            return resultCode == 200;

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return false;
    }

    public static void sendUserData(WebApiClient.PostAction postAction) {
        WebApiClient webApiClient = new WebApiClient();
        webApiClient.postSubscriberData(postAction);
    }
}
