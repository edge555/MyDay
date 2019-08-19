package com.example.myday;

import android.util.Log;

import java.util.Vector;

public class Extracter {
    public Vector<Integer>getdatetime(String a) {

        Vector<Integer>vec = new Vector<Integer>();
        String y="",m="",d="",min="",h="",rand="";
        int i;
        for(i=0;i<a.length();i++){
            if(i>=0 && i<4){
                y+=a.charAt(i);
            }
            else if(i==4 || i==5){
                m+=a.charAt(i);
            }
            else if(i==6 || i==7){
                d+=a.charAt(i);
            }
            else if(i==8 || i==9){
                h+=a.charAt(i);
            }
            else if(i==10 || i==11){
                min+=a.charAt(i);
            }
            else if(i>=12 && i<15){
                rand+=a.charAt(i);
            }
        }
        vec.add(getint(y));
        vec.add(getint(m));
        vec.add(getint(d));
        vec.add(getint(h));
        vec.add(getint(min));
        vec.add(getint(rand));
        return vec;
    }
    public String getname(String a){
        String task="";
        for(int i=15;i<a.length();i++){
            task+=a.charAt(i);
        }
        return task;
    }
    public int getint(String a){
        int n=0;
        for(int i=0;i<a.length();i++){
            n*=10;
            n+=Character.getNumericValue(a.charAt(i));
        }
        Log.d("Long", String.valueOf(n));
        return n;
    }
    public String getdatepart(String a){
        String part = "";
        for(int i=0;i<a.length();i++){
            if(i<15) {
                part += a.charAt(i);
            }
        }
        return part;
    }
    public long getlong(String a){
        long n=0;
        for(int i=0;i<a.length();i++){
            n*=10;
            n+=Character.getNumericValue(a.charAt(i));
        }
        return n;
    }
}
