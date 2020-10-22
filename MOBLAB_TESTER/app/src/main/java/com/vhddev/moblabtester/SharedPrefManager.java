package com.vhddev.moblabtester;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager
{
    private static final String SHARED_PREF_NAME = "moblabsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_USERDOB = "keyuserdob";
    private static final String KEY_USERHNAME = "keyuserhname";
    private static final String KEY_USERPLACE = "keyuserplace";
    private static final String KEY_USERPIN = "keyuserpin";
    private static final String KEY_USERMOBILE = "keyusermobile";
    private static final String KEY_USEREMAIL = "keyuseremail";
    private static final String KEY_USERID = "keyuserid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context)
    {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void testerLogin(Tester tester)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USERID, tester.getTid());
        editor.putString(KEY_USERNAME,tester.getTname());
        editor.putString(KEY_USERDOB, tester.getTdob());
        editor.putString(KEY_USERHNAME, tester.getThname());
        editor.putString(KEY_USERPLACE,tester.getTplace());
        editor.putString(KEY_USERPIN,tester.getTpin());
        editor.putString(KEY_USERMOBILE, tester.getTmobile());
        editor.putString(KEY_USEREMAIL, tester.getTemail());
        editor.apply();
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null) != null;
    }

    public Tester getTester()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Tester(
                sharedPreferences.getInt(KEY_USERID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_USERDOB, null),
                sharedPreferences.getString(KEY_USERHNAME, null),
                sharedPreferences.getString(KEY_USERPLACE, null),
                sharedPreferences.getString(KEY_USERPIN, null),
                sharedPreferences.getString(KEY_USERMOBILE, null),
                sharedPreferences.getString(KEY_USEREMAIL, null)
        );
    }

    public void logout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }
}
