package com.fmahieu.switter.ServerCommunication;

import android.util.Log;

import com.fmahieu.switter.ModelLayer.models.httpModel.JsonHolder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class ConnectionHttp {
    private final String TAG = "__ConnectionHttp";

    /**
     * url to lambda server
     */
    private final String SERVER_URL =
"https://kyjrwlzchc.execute-api.us-west-2.amazonaws.com/testing/";

    // return the json result from the server
    public String sendRequest(String partialUrl, String method, String requestBody, Map<String, String> headers) throws Exception{

        try{

            Log.i(TAG, "starting connexion...");
            URL url = new URL(SERVER_URL + partialUrl);
            Log.i(TAG,"FULL URL: " + url);
            Log.i(TAG, "request body:" + requestBody);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);


            // add headers if any
            if(headers != null){
                for (Map.Entry<String,String> entry : headers.entrySet())
                    con.setRequestProperty(entry.getKey(), entry.getValue());
            }

            if(method.equals("GET")){
                // AWS's kinda slow, so don't be in a hurry
                //con.setConnectTimeout(999999999);
                //con.setReadTimeout(999999999);

                int status = con.getResponseCode();


                if (status > 299) {
                    Log.i(TAG, "error");
                    JsonHolder.getInstance().setContent(null);
                } else {
                    Log.i(TAG, "received a result");

                }
            }
            else{

                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                if(requestBody != null) {

                    Log.i(TAG, "requestBody in jsonFormat: \n" + requestBody);

                    // add json requestBody into con
                    OutputStream os = con.getOutputStream();
                    byte[] input = requestBody.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

            }

            // Read response
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                Log.i(TAG, "Content returned: " + response.toString());
                return response.toString();
            }

        }
        catch (Exception e){
            throw new Exception(e);
        }

    }
}
