package com.example.myday;

public class Exampleitem {

    private String title, des, date, time, full, repeat, marker;

    public Exampleitem(String title, String des, String date, String time, String full, String repeat, String marker) {
        this.title = title;
        this.des = des;
        this.date = date;
        this.time = time;
        this.full = full;
        this.repeat = repeat;
        this.marker = marker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
}
