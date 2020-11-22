package com.vhddev.moblab;

public class ResultList
{
    String test_date,doctor_name;

    public ResultList(String test_date, String doctor_name) {
        this.test_date = test_date;
        this.doctor_name = doctor_name;
    }

    public String getTest_date() {
        return test_date;
    }

    public String getDoctor_name() {
        return doctor_name;
    }
}
