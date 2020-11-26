package com.vhddev.moblab;

public class Requests
{
    String doc_name, tester_name, tester_mobile, test_date, pay_stat, test_stat;

    public Requests(String doc_name, String tester_name, String tester_mobile, String test_date, String pay_stat, String test_stat) {
        this.doc_name = doc_name;
        this.tester_name = tester_name;
        this.tester_mobile = tester_mobile;
        this.test_date = test_date;
        this.pay_stat = pay_stat;
        this.test_stat = test_stat;
    }

    public String getTester_mobile() {
        return tester_mobile;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public String getTester_name() {
        return tester_name;
    }

    public String getTest_date() {
        return test_date;
    }

    public String getPay_stat() {
        return pay_stat;
    }

    public String getTest_stat() {
        return test_stat;
    }
}
