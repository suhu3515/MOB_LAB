package com.vhddev.moblabtester;

public class Tester
{
    private int tid;

    private String tname,tdob,thname,tplace,tpin,tmobile,temail;

    public Tester(int tid, String tname, String tdob, String thname, String tplace, String tpin, String tmobile, String temail) {
        this.tid = tid;
        this.tname = tname;
        this.tdob = tdob;
        this.thname = thname;
        this.tplace = tplace;
        this.tpin = tpin;
        this.tmobile = tmobile;
        this.temail = temail;
    }

    public int getTid() {
        return tid;
    }

    public String getTname() {
        return tname;
    }

    public String getTdob() {
        return tdob;
    }

    public String getThname() {
        return thname;
    }

    public String getTplace() {
        return tplace;
    }

    public String getTpin() {
        return tpin;
    }

    public String getTmobile() {
        return tmobile;
    }

    public String getTemail() {
        return temail;
    }
}
