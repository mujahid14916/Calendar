package com.calendar.my.calendar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            getWindow().setBackgroundDrawableResource(R.drawable.land);
        else
            getWindow().setBackgroundDrawableResource(R.drawable.port);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
