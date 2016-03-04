package com.example.ninatran.represent;

import android.os.Bundle;
import android.app.Activity;
import android.support.wearable.view.GridViewPager;

public class CongressActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congress);

        final GridViewPager mGridPager = (GridViewPager) findViewById(R.id.pager);
        mGridPager.setAdapter(new MyGridPagerAdapter(this, getFragmentManager()));
    }

}
