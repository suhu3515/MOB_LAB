package com.vhddev.moblab;

public class Result
{
    String test_name, value, reference, specimen;

    public Result(String test_name, String value, String reference, String specimen) {
        this.test_name = test_name;
        this.value = value;
        this.reference = reference;
        this.specimen = specimen;
    }

    public String getTest_name() {
        return test_name;
    }

    public String getValue() {
        return value;
    }

    public String getReference() {
        return reference;
    }

    public String getSpecimen() {
        return specimen;
    }
}
