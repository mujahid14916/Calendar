package com.calendar.my.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Jameel on 21-Aug-16.
 */
public class MyCalendar {


    private static long year;

    public static void getCalendar(Context context, Activity activity, long year) {

        MyCalendar.year = year;
        Calendar cal = Calendar.getInstance();
        int currentYear,currentMonth,currentDate;
        currentDate = cal.get(Calendar.DATE);
        currentMonth = cal.get(Calendar.MONTH);
        currentYear = cal.get(Calendar.YEAR);
        int totalDays;
        TextView day;
        TableRow row;
        int position = dayPosition();
        ArrayList<TableLayout> table = new ArrayList<>(Arrays.asList(
                (TableLayout) activity.findViewById(R.id.jan),
                (TableLayout) activity.findViewById(R.id.feb),
                (TableLayout) activity.findViewById(R.id.mar),
                (TableLayout) activity.findViewById(R.id.apr),
                (TableLayout) activity.findViewById(R.id.may),
                (TableLayout) activity.findViewById(R.id.jun),
                (TableLayout) activity.findViewById(R.id.jul),
                (TableLayout) activity.findViewById(R.id.aug),
                (TableLayout) activity.findViewById(R.id.sep),
                (TableLayout) activity.findViewById(R.id.oct),
                (TableLayout) activity.findViewById(R.id.nov),
                (TableLayout) activity.findViewById(R.id.dec)
        ));

        //Month

        for (int i = 1; i <= 12; i++) {
            row = new TableRow(context);
            int preDays = days(i - 1);

            //Days of previous Month

            for (int j = preDays - position + 1; j <= preDays; j++) {
                day = new TextView(context);
                day.setText("" + j);
                day.setTextColor(Color.parseColor("#888888"));
                day.setTypeface(null, Typeface.BOLD);
                day.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                day.setBackgroundResource(R.drawable.stroke);

                row.addView(day);
            }
            totalDays = days(i);

            //Days of current Month

            for (int j = 1; j <= totalDays; j++) {
                day = new TextView(context);
                day.setText("" + j);
                day.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                day.setTextColor(Color.parseColor("#333333"));
                day.setTypeface(null, Typeface.BOLD);
//                day.setBackgroundResource(R.drawable.stroke);

                if (position == 0) {
                    day.setTextColor(context.getResources().getColor(R.color.sun));
                }

                //Highlight Today's Date

                if (currentYear == year && currentMonth + 1 == i && currentDate == j) {
                    day.setTextColor(Color.parseColor("#FFFFFF"));
                    day.setBackgroundResource(R.drawable.today);
                }
                row.addView(day);
                position++;

                if (position == 7) {
                    position = 0;
                    table.get(i - 1).addView(row);
                    row = new TableRow(context);
                }

            }

            //Days of next Month

            int t = position;
            if (t != 0) {
                for (int j = 1; t <= 6; j++, t++) {
                    day = new TextView(context);
                    day.setText("" + j);
                    day.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    day.setTextColor(Color.parseColor("#888888"));
                    day.setTypeface(null, Typeface.BOLD);
//                    day.setBackgroundResource(R.drawable.stroke);
                    row.addView(day);
                }
                table.get(i - 1).addView(row);
            }

        }


    }



    public static byte yearCode(long year) {
        long lsbs, msbs, corrector, leaps;
        lsbs = year % 100;
        msbs = year / 100;
        leaps = lsbs / 4;
        if (msbs % 4 == 0) corrector = 0;
        else if (msbs % 4 == 1) corrector = 5;
        else if (msbs % 4 == 2) corrector = 3;
        else corrector = 1;
        return (byte) ((lsbs + leaps + corrector) % 7);
    }


    public static int days(int m) {
        if (m == 4 || m == 6 || m == 9 || m == 11)
            return 30;
        else if (m == 2) {
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
                return 29;
            else return 28;
        } else return 31;
    }
    private static int dayPosition() {
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
            return (6 + yearCode(year)) % 7;
        else
            return (7 + yearCode(year)) % 7;
    }



}
