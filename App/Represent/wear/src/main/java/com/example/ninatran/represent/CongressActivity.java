package com.example.ninatran.represent;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CongressActivity extends Activity {
    private String mLat;
    private String mLong;
    private String url;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congress);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            data = extras.getString("JSON");

        }
//
        final GridViewPager mGridPager = (GridViewPager) findViewById(R.id.pager);
        mGridPager.setAdapter(new GridPagerAdapter(CongressActivity.this, getFragmentManager(), data));
    }


}
