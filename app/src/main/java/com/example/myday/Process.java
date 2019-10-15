package com.example.myday;

import android.util.Log;

import java.util.ArrayList;

public class Process {

    public int intToDay(int jd) {
        return jd%7;
    }
    public int dateToInt(int y,int m,int d) {
        return 1461* (y + 4800 + (m - 14) / 12) / 4 +
                367 * (m - 2 - (m - 14) / 12 * 12) / 12 -
                3 * ((y + 4900 + (m - 14) / 12) / 100) / 4 +
                d - 32075;
    }
    public ArrayList <Integer> intToDate(int jd) {
        int x,n,i,j;
        x = jd + 68569;
        n = 4 * x / 146097;
        x -= (146097 * n + 3) / 4;
        i = (4000 * (x + 1)) / 1461001;
        x -= 1461 * i / 4 - 31;
        j = 80 * x / 2447;
        int d = x - 2447 * j / 80;
        x = j / 11;
        int m = j + 2 - 12 * x;
        int y = 100 * (n - 49) + i + x;
        ArrayList <Integer> arr = new ArrayList<>();
        arr.add(y);
        arr.add(m);
        arr.add(d);
        return arr;
    }

    public ArrayList<Integer> getdatelist(String s) {
        String ys = s.substring(0,4);
        String ms = s.substring(4,6);
        String ds = s.substring(6,8);
        int y = Integer.parseInt(ys);
        int m = Integer.parseInt(ms);
        int d = Integer.parseInt(ds);
        ArrayList <Integer> arr = new ArrayList<>();
        arr.add(y);
        arr.add(m);
        arr.add(d);
        return arr;
    }

    public String incrementmonth(String s) {
        String m = s.substring(4,6);
        int month = Integer.valueOf(m);
        String d = s.substring(0,4)+String.valueOf(month+1)+s.substring(6,8);
        return d;
    }
}
