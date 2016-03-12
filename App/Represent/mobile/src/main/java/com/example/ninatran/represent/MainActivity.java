package com.example.ninatran.represent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private Button mGo;
    private Button mCurrBttn;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String mLatitudeText;
    private String mLongitudeText;
    private String url;
    private String countyURL;
    private String jsonString;
    private String locationResponse;
    private String countyReponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGo = (Button) findViewById(R.id.buttonGo);
        mCurrBttn = (Button) findViewById(R.id.currentLocation);
        jsonString = loadJSONFromAsset();
        // Build GOOGLE API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(AppIndex.API).build();


        // Enter Zip Code and click GO
        mGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText zip_code = (EditText) findViewById(R.id.ZipCodeNum);
                String zipCode = zip_code.getText().toString();
                String combine_info = null;
                getInfoLocation(zipCode, null, null);
                getCounty(zipCode, null, null);
                try {
                    JSONObject jsonObject = new JSONObject(countyReponse);
                    JSONArray county_array = jsonObject.getJSONArray("results");

                    JSONObject json = county_array.getJSONObject(0);
                    JSONArray info_array = json.getJSONArray("address_components");

                    JSONObject county = info_array.getJSONObject(2);
                    String county_name = county.getString("long_name");
                    Log.d("COUNTY", "it is " + county_name);

                    JSONObject state = info_array.getJSONObject(3);
                    String state_name = state.getString("short_name");
                    String s = state.getString("long_name");
                    Log.d("STATE", "it is " + state_name);

                    String loc = county_name + ", " + state_name;
                    Log.d("LOC", "it is " + loc);

                    JSONObject jsonLoc = new JSONObject(jsonString);
                    JSONObject jsonVote = jsonLoc.getJSONObject(loc);
                    Double obama = jsonVote.getDouble("obama");
                    String obama_vote = String.valueOf(obama);
                    Log.d("OBAMA", "it is " + obama_vote);

                    Double romney = jsonVote.getDouble("romney");
                    String romney_vote = String.valueOf(romney);
                    Log.d("ROMNEY", "it is " + romney_vote);

                    combine_info = locationResponse + "|" + county_name + "|" + s
                                    + "|" + obama_vote + "|" + romney_vote;



                } catch (Exception e) {
                    Log.d("E", "ERROR");
                }



                Intent sendToCongress = new Intent(getBaseContext(), CongressView.class);
                Log.d("Location", "it is " + locationResponse);
                sendToCongress.putExtra("JSON", locationResponse);
                startActivity(sendToCongress);

                Intent sendToWatch = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendToWatch.putExtra("JSON", combine_info);
                startService(sendToWatch);

            }
        });
        mCurrBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get Current Location
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                            mGoogleApiClient);
                Log.d("T", "HERE");
                if (mLastLocation != null) {
                    mLatitudeText = (String.valueOf(mLastLocation.getLatitude()));
                    mLongitudeText = (String.valueOf(mLastLocation.getLongitude()));
                    Log.d("Distance", mLatitudeText + mLongitudeText);
                }
                String combine_info = null;
                getInfoLocation(null, mLatitudeText, mLongitudeText);
                getCounty(null, mLatitudeText, mLongitudeText);
                try {
                    JSONObject jsonObject = new JSONObject(countyReponse);
                    JSONArray county_array = jsonObject.getJSONArray("results");

                    JSONObject json = county_array.getJSONObject(0);
                    JSONArray info_array = json.getJSONArray("address_components");

                    JSONObject county = info_array.getJSONObject(3);
                    String county_name = county.getString("long_name");
                    Log.d("COUNTY", "it is " + county_name);

                    JSONObject state = info_array.getJSONObject(4);
                    String state_name = state.getString("short_name");
                    String s = state.getString("long_name");
                    Log.d("STATE", "it is " + state_name);

                    String loc = county_name + ", " + state_name;
                    Log.d("LOC", "it is " + loc);

                    JSONObject jsonLoc = new JSONObject(jsonString);
                    JSONObject jsonVote = jsonLoc.getJSONObject(loc);
                    Double obama = jsonVote.getDouble("obama");
                    String obama_vote = String.valueOf(obama);
                    Log.d("OBAMA", "it is " + obama_vote);

                    Double romney = jsonVote.getDouble("romney");
                    String romney_vote = String.valueOf(romney);
                    Log.d("ROMNEY", "it is " + romney_vote);

                    combine_info = locationResponse + "|" + county_name + "|" + s
                            + "|" + obama_vote + "|" + romney_vote;

                    Intent sendToCongress = new Intent(getBaseContext(), CongressView.class);
                    Log.d("Location", "it is " + locationResponse);
                    sendToCongress.putExtra("JSON", locationResponse);
                    startActivity(sendToCongress);

                    Intent sendToWatch = new Intent(getBaseContext(), PhoneToWatchService.class);
                    sendToWatch.putExtra("JSON", combine_info);
                    startService(sendToWatch);

                } catch (Exception e) {
                    Log.d("E", "ERROR");
                }




            }
        });



    }


    @Override
    public void onConnected(Bundle bundle) {
//        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {}

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    public void getInfoLocation(String zipCode, String mLat, String mLong) {
        if (mLat != null && mLong != null) {
            url = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + mLat
                    + "&longitude=" + mLong + "&apikey=8b176fc0921b4bbb80c9907cffddb21a";

        } else {
            url = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zipCode
                    + "&apikey=8b176fc0921b4bbb80c9907cffddb21a";
        }
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            DownloadTask mTask = new DownloadTask();
            mTask.execute(url);
            try {
                locationResponse = mTask.get();
                Log.d("L", " " + locationResponse);
            } catch (Exception e) {
                Log.d("A", "ERROR");
            }

        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }

    public void getCounty(String zipCode, String mLat, String mLong) {
        if (mLat != null && mLong != null) {
            countyURL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + mLat
                        + "," + mLong;
            Log.d("LURL", " " + countyURL);
        } else {
            countyURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipCode;
        }
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            DownloadTask mTask = new DownloadTask();
            mTask.execute(countyURL);
            try {
                countyReponse = mTask.get();
            } catch (Exception e) {
                Log.d("A", "ERROR");
            }

        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("election2012.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}


