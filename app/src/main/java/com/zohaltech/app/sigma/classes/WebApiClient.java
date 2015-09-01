package com.zohaltech.app.sigma.classes;

import android.os.Build;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

public class WebApiClient {

    private static final String HOST_URL = "http://zohaltech.com/api/app/post";
    private static final String POST_SUCCESS = "POST_SUCCESS";
    private JSONObject jsonObject;

    public enum PostAction {
        INSTALL,
        REGISTER
    }

    private JSONObject getJsonObject() {
        return jsonObject;
    }

    private void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void postSubscriberData(PostAction action) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (action == PostAction.INSTALL) {
            jsonObject.accumulate("AppId", "1");
            jsonObject.accumulate("DeviceId", Helper.getDeviceId());
            jsonObject.accumulate("DeviceBrand", Build.MANUFACTURER);
            jsonObject.accumulate("DeviceModel", Build.MODEL);
            jsonObject.accumulate("AndroidVersion", Build.VERSION.RELEASE);
            jsonObject.accumulate("ApiVersion", Build.VERSION.SDK_INT + "");
            jsonObject.accumulate("OperatorId", Helper.getOperator().ordinal());
            jsonObject.accumulate("IsPurchased", "0");

            setJsonObject(jsonObject);


        } else {
            jsonObject.accumulate("AppId", "1");
            jsonObject.accumulate("DeviceId", Helper.getDeviceId());
            jsonObject.accumulate("IsPurchased", "1");

            setJsonObject(jsonObject);

        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean result = post(getJsonObject());
                App.preferences.edit().putBoolean(POST_SUCCESS, result).commit();
            }
        });

        thread.start();
    }

    private Boolean post(JSONObject jsonObject) {
        String result = "";
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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    private static JSONObject getJsonObjectFromMap(Map params) throws JSONException {

        //all the passed parameters from the post request
        //iterator used to loop through all the parameters
        //passed in the post request
        Iterator iter = params.entrySet().iterator();

        //Stores JSON
        JSONObject holder = new JSONObject();

        //using the earlier example your first entry would get email
        //and the inner while would get the value which would be 'foo@bar.com'
        //{ fan: { email : 'foo@bar.com' } }

        //While there is another entry
        while (iter.hasNext()) {
            //gets an entry in the params
            Map.Entry pairs = (Map.Entry) iter.next();

            //creates a key for Map
            String key = (String) pairs.getKey();

            //Create a new map
            Map m = (Map) pairs.getValue();

            //object for storing Json
            JSONObject data = new JSONObject();

            //gets the value
            for (Object o : m.entrySet()) {
                Map.Entry pairs2 = (Map.Entry) o;
                data.put((String) pairs2.getKey(), (String) pairs2.getValue());
            }

            //puts email and 'foo@bar.com'  together in map
            holder.put(key, data);
        }
        return holder;
    }
}
