package com.vhddev.moblabtester;

public class Tests
{
    String testName, testSpecimen;

    public Tests(String testName, String testSpecimen) {
        this.testName = testName;
        this.testSpecimen = testSpecimen;
    }

    public String getTestName() {
        return testName;
    }

    public String getTestSpecimen() {
        return testSpecimen;
    }

}