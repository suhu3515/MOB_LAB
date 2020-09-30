package com.vhddev.moblab;

public class User
{
    private int uid;

    private String uname,udob,uhname,uplace,upin,umobile,uemail;

    public User(int uid, String uname, String udob, String uhname, String uplace, String upin, String umobile, String uemail) {
        this.uid = uid;
        this.uname = uname;
        this.udob = udob;
        this.uhname = uhname;
        this.uplace = uplace;
        this.upin = upin;
        this.umobile = umobile;
        this.uemail = uemail;
    }

    public int getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public String getUdob() {
        return udob;
    }

    public String getUhname() {
        return uhname;
    }

    public String getUplace() {
        return uplace;
    }

    public String getUpin() {
        return upin;
    }

    public String getUmobile() {
        return umobile;
    }

    public String getUemail() {
        return uemail;
    }
}
