package com.vhddev.moblabtester;

public class Results
{
    String test_name, value, ref, specimen;

    public Results(String test_name, String value, String ref, String specimen) {
        this.test_name = test_name;
        this.value = value;
        this.ref = ref;
        this.specimen = specimen;
    }

    public String getTest_name() {
        return test_name;
    }

    public String getValue() {
        return value;
    }

    public String getRef() {
        return ref;
    }

    public String getSpecimen() {
        return specimen;
    }
}
