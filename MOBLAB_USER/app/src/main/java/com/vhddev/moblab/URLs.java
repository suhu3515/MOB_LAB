package com.vhddev.moblab;

public class URLs
{
    private static final String ROOT_URL = "http://192.168.43.159/moblab/Api.php?apicall="; //umma
    //private static final String ROOT_URL = "http://192.168.0.105/moblab/Api.php?apicall="; //wifi direct
    //private static final String ROOT_URL = "http://192.168.0.105/moblab/Api.php?apicall="; //nox


    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN = ROOT_URL + "login";
    public static final String URL_ADD_LOCATION = ROOT_URL + "add_loc";
    public static final String URL_ADD_TEST_REQUEST = ROOT_URL + "book_test";
    public static final String URL_UPLOAD_PRESCRIPTION = "http://192.168.43.159/moblab/upload.php";
    public static final String URL_GET_SPECIMEN = ROOT_URL + "get_specimen";
    public static final String URL_ADD_USER_TESTS = ROOT_URL + "add_user_test";


}