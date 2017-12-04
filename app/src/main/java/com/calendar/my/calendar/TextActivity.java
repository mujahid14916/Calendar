package com.calendar.my.calendar;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class TextActivity extends AppCompatActivity {

    private static long year;
    private static boolean animation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            getWindow().setBackgroundDrawableResource(R.drawable.land);
        else
            getWindow().setBackgroundDrawableResource(R.drawable.port);

        setTitle("" + year);

        MyCalendar.getCalendar(TextActivity.this,this,year);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ArrayList<TableLayout> table = new ArrayList<>(Arrays.asList(
                (TableLayout) findViewById(R.id.jan),
                (TableLayout) findViewById(R.id.feb),
                (TableLayout) findViewById(R.id.mar),
                (TableLayout) findViewById(R.id.apr),
                (TableLayout) findViewById(R.id.may),
                (TableLayout) findViewById(R.id.jun),
                (TableLayout) findViewById(R.id.jul),
                (TableLayout) findViewById(R.id.aug),
                (TableLayout) findViewById(R.id.sep),
                (TableLayout) findViewById(R.id.oct),
                (TableLayout) findViewById(R.id.nov),
                (TableLayout) findViewById(R.id.dec)
        ));

        for (int i = 11; i >= 0; i--) {
            table.get(i).animate().translationY(0).translationX(0).rotationX(0).scaleX(1).scaleY(1).setDuration(0);
        }

        //Change Layout

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float min = dm.heightPixels/getResources().getDisplayMetrics().density;
        if(min > dm.widthPixels/getResources().getDisplayMetrics().density) {
            min = dm.widthPixels/getResources().getDisplayMetrics().density;
        }
        if(min < getResources().getDimension(R.dimen.restartWidth)/getResources().getDisplayMetrics().density) {
            startActivity(new Intent(this,TextActivity.class));
            finish();
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            getWindow().setBackgroundDrawableResource(R.drawable.land);
        else
            getWindow().setBackgroundDrawableResource(R.drawable.port);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void set(long x) {
        year = x;
    }

    //Animate on touch

    public void displace(View v) {

        long t = 200;
        ArrayList<TableLayout> table = new ArrayList<>(Arrays.asList(
                (TableLayout) findViewById(R.id.jan),
                (TableLayout) findViewById(R.id.feb),
                (TableLayout) findViewById(R.id.mar),
                (TableLayout) findViewById(R.id.apr),
                (TableLayout) findViewById(R.id.may),
                (TableLayout) findViewById(R.id.jun),
                (TableLayout) findViewById(R.id.jul),
                (TableLayout) findViewById(R.id.aug),
                (TableLayout) findViewById(R.id.sep),
                (TableLayout) findViewById(R.id.oct),
                (TableLayout) findViewById(R.id.nov),
                (TableLayout) findViewById(R.id.dec)
        ));

        if (animation == false) {
            ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
            float y = 0f;
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                for (int i = 0; i < 12; i++) {
                    table.get(i).animate().translationY(-y).setDuration(t);
                    y = y + (table.get(i).getHeight() - getResources().getDimension(R.dimen.runHeight));
                    t = t + 80;
                }
            }
            else
            {
                float height;
                float width;
                float[] arr = new float[3];
                float max = 0f;
                float W = table.get(0).getWidth();
                for(int i = 0; i < 12; i=i+3) {
                    width = -W/2;
                    for(int j = 0; j < 3; j++) {
                        height = max/2 - table.get(i+j).getHeight()/4;
                        table.get(i+j).animate().scaleX(0.5f).scaleY(0.5f).translationX(width).y(height).setDuration(t);
                        width = width + (W)/2;
                        arr[j] = table.get(i+j).getHeight();
                        t = t + 80;
                    }
                    max = max + large(arr);
                }
            }
            animation = true;
            ObjectAnimator.ofInt(sv,"ScrollY",0).setDuration(400).start();
        }
        else {
            ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
            for (int i = 11; i >= 0; i--) {
                table.get(i).animate().translationY(0).translationX(0).rotationX(0).scaleX(1).scaleY(1).setDuration(t);
                t = t + 80;
            }

            int h;
            if(table.get(0).getHeight() == table.get(2).getHeight())
                h = (int) (table.get(0).getHeight() + 0.5 * getResources().getDimension(R.dimen.monthMargin));
            else
                h = (int) (table.get(1).getHeight() + getResources().getDimension(R.dimen.monthMargin));

            switch (v.getId()) {
                case R.id.jan: sv.scrollTo(0,0);
                    break;
                case R.id.feb: ObjectAnimator.ofInt(sv,"ScrollY", h).setDuration(300).start();
                    break;
                case R.id.mar: ObjectAnimator.ofInt(sv,"ScrollY", h * 2).setDuration(400).start();
                    break;
                case R.id.apr: ObjectAnimator.ofInt(sv,"ScrollY", h * 3).setDuration(500).start();
                    break;
                case R.id.may: ObjectAnimator.ofInt(sv,"ScrollY", h * 4).setDuration(600).start();
                    break;
                case R.id.jun: ObjectAnimator.ofInt(sv,"ScrollY", h * 5).setDuration(700).start();
                    break;
                case R.id.jul: ObjectAnimator.ofInt(sv,"ScrollY", h * 6).setDuration(800).start();
                    break;
                case R.id.aug: ObjectAnimator.ofInt(sv,"ScrollY", h * 7).setDuration(900).start();
                    break;
                case R.id.sep: ObjectAnimator.ofInt(sv,"ScrollY", h * 8).setDuration(1000).start();
                    break;
                case R.id.oct: ObjectAnimator.ofInt(sv,"ScrollY", h * 9).setDuration(1100).start();
                    break;
                case R.id.nov: ObjectAnimator.ofInt(sv,"ScrollY", h * 10).setDuration(1200).start();
                    break;
                case R.id.dec: ObjectAnimator.ofInt(sv,"ScrollY", h * 11).setDuration(1300).start();
            }
            animation = false;
        }

    }

    public float large(float[] f) {
        float max = f[0];
        for(int i = 1; i < 3; i++)
            if(max < f[i])
                max = f[i];
        return max;
    }

}
