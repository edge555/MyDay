package com.example.myday;

public class Exampleitem {

    private String title,date,time,full;

    public Exampleitem(String title, String date, String time, String full) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.full = full;
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
}
