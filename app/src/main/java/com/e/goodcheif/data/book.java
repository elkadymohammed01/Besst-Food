package com.e.goodcheif.data;

public class book {
    String user_email ,time;
    long appoint;
    int type;

    public book() {

    }

    public book(String user_email, String time, long appoint, int type) {
        this.user_email = user_email;
        this.time = time;
        this.appoint = appoint;
        this.type = type;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getAppoint() {
        return appoint;
    }

    public void setAppoint(long appoint) {
        this.appoint = appoint;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
