package com.vhddev.moblab;

public class URLs
{
    private static final String ROOT_URL = "http://192.168.43.159/moblab/Api.php?apicall="; //umma
    //private static final String ROOT_URL = "http://172.20.10.4/moblab/Api.php?apicall="; //j2
    //private static final String ROOT_URL = "http://192.168.0.105/moblab/Api.php?apicall="; //wifi direct
    //private static final String ROOT_URL = "http://192.168.0.105/moblab/Api.php?apicall="; //nox


    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN = ROOT_URL + "user_login";
    public static final String URL_ADD_LOCATION = ROOT_URL + "add_loc";
    public static final String URL_ADD_TEST_REQUEST = ROOT_URL + "book_test";
    public static final String URL_ADD_USER_TESTS = ROOT_URL + "add_user_test";
    public static final String URL_CHANGE_PASSWORD = ROOT_URL + "change_pass";
    public static final String URL_USER_FEEDBACK = ROOT_URL + "user_feedback";
    public static final String URL_UPLOAD_PRESCRIPTION = "http://192.168.43.159/moblab/upload.php";
    public static final String URL_GET_SPECIMEN = "http://192.168.43.159/moblab/get_specimen.php";
    public static final String URL_GET_TESTS = "http://192.168.43.159/moblab/get_tests.php";
    public static final String URL_USER_RESULTS = "http://192.168.43.159/moblab/user_test_result_list.php";
    public static final String URL_USER_RESULT = "http://192.168.43.159/moblab/user_test_result_details.php";
    public static final String URL_USER_REQUESTS = "http://192.168.43.159/moblab/user_test_requests.php";

}