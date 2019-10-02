package com.example.myday;

public class Info {
    private String title, des, add, date, time, full;

    public Info() {
    }

    public Info(String title, String des, String add, String date, String time, String full) {
        this.title = title;
        this.des = des;
        this.add = add;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
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
