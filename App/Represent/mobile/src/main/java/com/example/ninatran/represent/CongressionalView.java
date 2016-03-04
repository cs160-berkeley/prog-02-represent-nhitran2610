package com.example.ninatran.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CongressionalView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void toSenator1(View v) {
        Intent intent = new Intent(this, DetailView.class);
        startActivity(intent);
    }

    public void toSenator2(View v) {
        Intent intent = new Intent(this, DetailView2.class);
        startActivity(intent);
    }

    public void toRep(View v) {
        Intent intent = new Intent(this, DetailView3.class);
        startActivity(intent);
    }


}
