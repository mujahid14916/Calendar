package com.calendar.my.calendar;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public int year;
    private TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        TextView yearText = (TextView) findViewById(R.id.textView);
        yearText.setTextColor(Color.parseColor("#000000"));

        //Calendar object to get Current Date

        Calendar cal = Calendar.getInstance();

        TextView cDate = (TextView) findViewById(R.id.currentDate);
        cDate.setText("Date: " + cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR));

        year = cal.get(Calendar.YEAR);
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        int position = dayCode(cal.get(Calendar.MONTH) + 1);
        TextView tv;
        TableRow tr;
        int height;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            height = (int) (this.getResources().getDimension(R.dimen.landDayHeight));

            getWindow().setBackgroundDrawableResource(R.drawable.land);

            ((TextView) findViewById(R.id.sun)).setHeight(height);
            ((TextView) findViewById(R.id.mon)).setHeight(height);
            ((TextView) findViewById(R.id.tue)).setHeight(height);
            ((TextView) findViewById(R.id.wed)).setHeight(height);
            ((TextView) findViewById(R.id.thu)).setHeight(height);
            ((TextView) findViewById(R.id.fri)).setHeight(height);
            ((TextView) findViewById(R.id.sat)).setHeight(height);

            if (getResources().getDisplayMetrics().heightPixels > getResources().getDimension(R.dimen.minHeight)) {
                (findViewById(R.id.textClock)).setVisibility(View.VISIBLE);
            }
            if (getResources().getDisplayMetrics().heightPixels > getResources().getDimension(R.dimen.minHeightPlus)) {
                (findViewById(R.id.currentDate)).setVisibility(View.VISIBLE);
            }

        } else {
            height = (int) (this.getResources().getDimension(R.dimen.dayHeight));

//            findViewById(R.id.relativeLayout).setBackgroundColor(Color.parseColor("#EEEEEE"));

            getWindow().setBackgroundDrawableResource(R.drawable.port);

            ((TextView) findViewById(R.id.sun)).setHeight(height);
            ((TextView) findViewById(R.id.mon)).setHeight(height);
            ((TextView) findViewById(R.id.tue)).setHeight(height);
            ((TextView) findViewById(R.id.wed)).setHeight(height);
            ((TextView) findViewById(R.id.thu)).setHeight(height);
            ((TextView) findViewById(R.id.fri)).setHeight(height);
            ((TextView) findViewById(R.id.sat)).setHeight(height);
            (findViewById(R.id.textClock)).setVisibility(View.VISIBLE);
            (findViewById(R.id.currentDate)).setVisibility(View.VISIBLE);
        }
        tv = (TextView) findViewById(R.id.month);
        tv.setText(monthName(cal.get(Calendar.MONTH) + 1));
        tr = new TableRow(this);
        tr.setLayoutParams(rowLayout);
        for (int i = 1; i <= position; i++) {
            tv = new TextView(this);
            tv.setHeight(height);
//            tv.setBackgroundResource(R.drawable.stroke);
            tr.addView(tv);
        }
        int month = MyCalendar.days(cal.get(Calendar.MONTH) + 1);
        for (int i = 1; i <= month; i++) {
            tv = new TextView(this);
            tv.setText("" + i);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextColor(Color.parseColor("#000000"));
//            tv.setBackgroundResource(R.drawable.stroke);
            tv.setHeight(height);
            if (cal.get(Calendar.DATE) == i) {
                tv.setTextColor(Color.parseColor("#FFFFFF"));
                tv.setBackgroundResource(R.drawable.today);
            }
            tr.addView(tv);
            position++;
            if (position == 7) {
                position = 0;
                tl.addView(tr);
                tr = new TableRow(this);
            }
        }
        if (position != 0) {
            for (int j = 1; position <= 6; j++, position++) {
                tv = new TextView(this);
                tv.setHeight(height);
//                tv.setBackgroundResource(R.drawable.stroke);
                tr.addView(tv);
            }
            tl.addView(tr);
        }

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

    public void start(View v) {
        EditText et = (EditText) findViewById(R.id.editText);
        String s = et.getText().toString();
        try {
            long x = Long.parseLong(s);
            if (x < 0)
                Toast.makeText(this, "Year cannot be negative", Toast.LENGTH_LONG).show();
            else {
                TextActivity.set(x);
                startActivity(new Intent(this, TextActivity.class));
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Year", Toast.LENGTH_LONG).show();
        }


    }


    private int monthCode(int i) {
        switch (i) {
            case 1:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    return 5;
                else return 6;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    return 1;
                else return 2;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 0;
            case 6:
                return 3;
            case 7:
                return 5;
            case 8:
                return 1;
            case 9:
                return 4;
            case 10:
                return 6;
            case 11:
                return 2;
            case 12:
                return 4;
        }
        return 0;
    }

    private int dayCode(int i) {
        return (1 + monthCode(i) + MyCalendar.yearCode(year)) % 7;
    }


    private String monthName(int mc) {
        if (mc == 1) return "January";
        else if (mc == 2) return "February";
        else if (mc == 3) return "March";
        else if (mc == 4) return "April";
        else if (mc == 5) return "May";
        else if (mc == 6) return "June";
        else if (mc == 7) return "July";
        else if (mc == 8) return "August";
        else if (mc == 9) return "September";
        else if (mc == 10) return "October";
        else if (mc == 11) return "November";
        else return "December";
    }
}
