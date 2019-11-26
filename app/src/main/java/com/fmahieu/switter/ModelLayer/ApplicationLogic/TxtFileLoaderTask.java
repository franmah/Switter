package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class TxtFileLoaderTask extends AsyncTask<Void, Void, String> {
    private TextView view;
    private String link;


    public TxtFileLoaderTask(TextView view, String link) {
        this.view = view;
        this.link = link;
    }

    @Override
    protected String doInBackground(Void... params) {
        // download attachedPicture
        Log.i("___", "starting downloading process for TXT file, link: " + link);

        try {
            // Create a URL for the desired page
            URL url = new URL(link);

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            StringBuilder response = new StringBuilder();
            while ((str = in.readLine()) != null) {
                response.append(str.trim());
                // str is one line of text; readLine() strips the newline character(s)
            }
            in.close();

            return response.toString();

        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
            return "could not find file";
        }

    }

    @Override
    protected void onPostExecute(String response) {
        if(view == null){
            Log.i("__TESING", "view is null");
        }
        else if(response == null){
            Log.i("__TESTING", "txt file came back null");
        }
        else {
            view.setText(response);
        }
    }

}