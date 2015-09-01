package com.zohaltech.app.sigma.classes;

import android.os.Build;
import android.util.Log;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.dal.Settings;
import com.zohaltech.app.sigma.entities.Setting;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class WebApiClient {
    private static final String HOST_URL = App.context.getString(R.string.host_name);
    private JSONObject jsonObject;

    public static void sendUserData(WebApiClient.PostAction postAction) {
        WebApiClient webApiClient = new WebApiClient();
        webApiClient.postSubscriberData(postAction);
    }

    private JSONObject getJsonObject() {
        return jsonObject;
    }

    private void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void postSubscriberData(final PostAction action) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Setting setting = Settings.getCurrentSettings();
                    JSONObject jsonObject = new JSONObject();
                    if (action == PostAction.INSTALL) {
                        if (setting.getInstalled() == false) {
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
                                setting.setInstalled(result);
                                Settings.update(setting);
                            }
                        }
                    } else {
                        if (setting.getRegistered() == false) {
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
                                setting.setRegistered(result);
                                Settings.update(setting);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
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

    public enum PostAction {
        INSTALL,
        REGISTER
    }
}
