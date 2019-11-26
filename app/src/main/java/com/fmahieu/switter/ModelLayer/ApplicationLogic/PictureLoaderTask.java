package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PictureLoaderTask extends AsyncTask<Void, Void, String> {
    private ImageView view;
    private String link;


    public PictureLoaderTask(ImageView view, String link) {
        this.view = view;
        this.link = link;
    }

    @Override
    protected String doInBackground(Void... params) {
        // download attachedPicture
        Log.i("___", "starting downloading process");

        try {

            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            if (status > 299) {
                Log.i("___", "error");
                return null;
            } else {
                Log.i("___", "received a result");
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                //Log.i("___", "Content returned: " + response.toString());
                return response.toString();
            }
        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(String response) {
        // show attachedPicture;
        if (response == null) {
            Log.i("___", "pic came back null");
            return;
        }

        // decode pic
        InputStream stream = new ByteArrayInputStream(Base64.decode(response.getBytes(), Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        this.view.setImageBitmap(bitmap);

    }

}