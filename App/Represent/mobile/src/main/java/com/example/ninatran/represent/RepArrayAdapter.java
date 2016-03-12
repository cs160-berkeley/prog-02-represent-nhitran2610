package com.example.ninatran.represent;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ninatran on 3/8/16.
 */
public class RepArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] title;
    private String[] party;
    private String[] emails;
    private String[] websites;


    public RepArrayAdapter (Context context, String[] title, String[] party, String[] emails, String[] websites) {
        super(context, R.layout.congress_main, title);
        this.context = context;
        this.title = title;
        this.party = party;
        this.emails = emails;
        this.websites = websites;


    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View repRow = inflater.inflate(R.layout.congress_main, parent, false);

        TextView titleView = (TextView) repRow.findViewById(R.id.rep_title);
        TextView partyView = (TextView) repRow.findViewById(R.id.party_name);
        TextView emailView = (TextView) repRow.findViewById(R.id.email);
        TextView websiteView = (TextView) repRow.findViewById(R.id.website);


        titleView.setText(title[position]);
        partyView.setText(party[position]);
        emailView.setText("Email: " + emails[position]);
        websiteView.setText("Web: " + websites[position]);


        return repRow;
    }

}
