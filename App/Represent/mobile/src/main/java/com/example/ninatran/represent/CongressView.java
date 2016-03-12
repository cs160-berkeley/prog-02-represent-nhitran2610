package com.example.ninatran.represent;

import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CongressView extends AppCompatActivity {


    private static String[] titles;
    private static String[] parties;
    private static String[] emails;
    private static String[] websites;
    private static String[] rep_id;
    private static String[] term_end;
//    private static String[] tweet_id;
    private ListView repList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congress_view);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {

            String info = extras.getString("JSON");

            JSONObject jsonObject;
            try {

                Log.d("INFO1", "info" + info);
                jsonObject = new JSONObject(info);
                JSONArray rep_array = jsonObject.getJSONArray("results");


                int num_rep = jsonObject.getInt("count");
                titles = new String[num_rep];
                parties = new String[num_rep];
                emails = new String[num_rep];
                websites = new String[num_rep];
                rep_id = new String[num_rep];
                term_end = new String[num_rep];
                for (int i = 0; i < rep_array.length(); i++) {
                    JSONObject repObject = rep_array.getJSONObject(i);

                    // Get full name
                    String first_name = repObject.getString("first_name");
                    String last_name = repObject.getString("last_name");
                    String full_name = first_name + " " + last_name;
//                    rep[i] = full_name;

                    // Get Title
                    String chamber = repObject.getString("chamber");
                    String title = "";
                    if (chamber.equals("senate")) {
                        title = "Sen.";
                    }
                    if (chamber.equals("house")) {
                        title = "Rep.";
                    }
                    titles[i] = title + " " + full_name;


                    // Get Party
                    String mParty = repObject.getString("party");
                    String party = "";
                    if (mParty.equals("D")) {
                        party = "Democrat";
                    } else if (mParty.equals("R")) {
                        party = "Republican";
                    } else if (mParty.equals("I")){
                        party = "Independent";
                    }
                    parties[i] = party;

                    // Get emails:
                    String email = repObject.getString("oc_email");
                    emails[i] = email;
//
                    // Get websites:
                    String website = repObject.getString("website");
                    websites[i] = website;
//
//                    // Get rep_id:
                    String id = repObject.getString("bioguide_id");
                    rep_id[i] = id;
//
//                    // Get term_end:
                    String term = repObject.getString("term_end");
                    term_end[i] = term;
                    Log.d("REP", "number: " + i);
                }


            } catch (Exception e) {
                Log.d("NOT_GET_JSONObject", "Not get jsonObject");
            }

        }

        repList = (ListView) findViewById(R.id.listView);

        RepArrayAdapter adapter = new RepArrayAdapter(this, titles, parties, emails, websites);
        repList.setAdapter(adapter);

        repList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CongressView.this, DetailView.class);
                intent.putExtra("title", titles[position]);
                intent.putExtra("party", parties[position]);
                intent.putExtra("term_end", term_end[position]);
                intent.putExtra("rep_id", rep_id[position]);
                startActivity(intent);
            }
        });
    }

}
