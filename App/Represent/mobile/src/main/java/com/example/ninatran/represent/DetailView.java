package com.example.ninatran.represent;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailView extends AppCompatActivity {
    private String title;
    private String party;
    private String term_end;
    private String rep_id;
    private String committeeURL;
    private String billURL;
    private String url;
    private String currInfo;
    private JSONObject jsonCommittee;
    private JSONObject jsonBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        title = extras.getString("title");
        party = extras.getString("party");
        term_end = extras.getString("term_end");
        rep_id = extras.getString("rep_id");
        Log.d("REP_ID", "it is " + rep_id);

        TextView mTitle = (TextView) findViewById(R.id.rep_title);
        TextView mParty = (TextView) findViewById(R.id.rep_party);
        TextView mTermEnd = (TextView) findViewById(R.id.rep_termEnd);

        mTitle.setText(title);
        mParty.setText(party);
        mTermEnd.setText("Term End: " + term_end);

        committeeURL = "http://congress.api.sunlightfoundation.com/committees?member_ids="
                + rep_id + "&apikey=8b176fc0921b4bbb80c9907cffddb21a";

        billURL = "http://congress.api.sunlightfoundation.com/bills?sponsor_id="
                + rep_id + "&apikey=8b176fc0921b4bbb80c9907cffddb21a";


        myCommittee();
        myBills();



    }
    public void myBills() {
        url = billURL;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            DownloadTask mTask = new DownloadTask(billURL, new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    String error = "Unable to retrieve web page. URL may be invalid.";
                    if (output != null && !output.equalsIgnoreCase(error)) {
                        Log.d("OUTPUT", "output: " + output);
                        String[] bills = new String[2];
                        String[] dates = new String[2];
                        try {
                            jsonBill = new JSONObject(output);

                            JSONArray bill_array = jsonBill.getJSONArray("results");


                            for (int i = 0; i < 2; i++) {
                                JSONObject billObject = bill_array.getJSONObject(i);
                                String bill = billObject.getString("official_title");
                                bills[i] = bill;
                                String date = billObject.getString("introduced_on");
                                dates[i] = date;
                                Log.d("Bill", "bill:" + bills[i]);
                            }

                            TextView mB1 = (TextView) findViewById(R.id.b1);
                            TextView mB2 = (TextView) findViewById(R.id.b2);
//                            TextView mB3 = (TextView) findViewById(R.id.b3);

                            mB1.setText(bills[0] + "\nIntroduced on " + dates[0]);
                            mB2.setText(bills[1] + "\nIntroduced on " + dates[1]);
//                            mB3.setText(bills[2] + " introduced on " + dates[2]);

                        } catch (Exception e) {
                            Log.d("Committee", "ERROR");
                        }
                    }
                }
            });
            mTask.execute(url);
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }

    public void myCommittee() {
        url = committeeURL;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            DownloadTask mTask = new DownloadTask(committeeURL, new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    String error = "Unable to retrieve web page. URL may be invalid.";
                    if (output != null && !output.equalsIgnoreCase(error)) {
                        currInfo = output;
                        Log.d("OUTPUT", "output: " + currInfo);
                        String[] coms = new String[2];
                        try {
                            jsonCommittee = new JSONObject(currInfo);
                            Log.d("CREATEOBJ", "create object");
                            JSONArray committee_array = jsonCommittee.getJSONArray("results");
                            Log.d("ARRAY", "HERE");

                            for (int i = 0; i < 2; i++) {
                                JSONObject nameObject = committee_array.getJSONObject(i);
                                String name = nameObject.getString("name");
                                coms[i] = name;
                                Log.d("Name", "name:" + coms[i]);
                            }

                            TextView mCmt1 = (TextView) findViewById(R.id.cmt1);
                            TextView mCmt2 = (TextView) findViewById(R.id.cmt2);
//                            TextView mCmt3 = (TextView) findViewById(R.id.cmt3);

                            mCmt1.setText(coms[0]);
                            mCmt2.setText(coms[1]);
//                            mCmt3.setText(coms[2]);

                        } catch (Exception e) {
                            Log.d("Committee", "ERROR");
                        }
                    }
                }
            });
            mTask.execute(url);
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }


}
