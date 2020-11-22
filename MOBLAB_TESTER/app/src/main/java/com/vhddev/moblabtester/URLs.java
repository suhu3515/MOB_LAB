package com.vhddev.moblabtester;

public class URLs
{
    private static final String ROOT_URL = "http://192.168.43.159/moblab/";
    //private static final String ROOT_URL = "http://192.168.43.123/moblab/";
    //private static final String ROOT_URL = "http://192.168.43.159/moblab/";

    public static final String URL_GET_SUBTEST = ROOT_URL + "get_subtests.php";
    public static final String URL_TASKS_LIST = ROOT_URL +"tasks_list.php";
    public static final String URL_CHECK_SUBTESTS = ROOT_URL + "Api.php?apicall=check_subtests";
    public static final String URL_TASKS = ROOT_URL + "tasks.php";
    public static final String URL_RESULTS = ROOT_URL + "tester_test_result_details.php";
    public static final String URL_SUBTESTS_RESULTS = ROOT_URL + "Api.php?apicall=add_subtest_result";
    public static final String URL_TESTS_RESULTS = ROOT_URL + "Api.php?apicall=test_result";
    public static final String URL_CHECK_STATUS = ROOT_URL + "Api.php?apicall=check_status";
    public static final String URL_UPDATE_STATUS = ROOT_URL + "Api.php?apicall=update_status";
}
