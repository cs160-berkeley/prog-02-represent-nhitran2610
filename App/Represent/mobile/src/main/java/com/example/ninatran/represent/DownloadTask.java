package com.example.ninatran.represent;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ninatran on 3/11/16.
 */
public class DownloadTask extends AsyncTask<String, Void, String> {
        public AsyncResponse delegate = null;
        public String s;
        public DownloadTask(String s, AsyncResponse delegate) {
            super();
            this.delegate = delegate;
            this.s = s;
        }
        public DownloadTask() {
            super();
        }
        @Override
        protected String doInBackground(String... urls) {

            try {
                URL mUrl = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }

            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (delegate == null) {
                return;
            }
            delegate.processFinish(result);

        }

}
