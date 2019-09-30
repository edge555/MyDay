package com.example.myday;

public class Info {
    private String title,des,add;

    public Info(String title, String des, String add) {
        this.title = title;
        this.des = des;
        this.add = add;
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


}
