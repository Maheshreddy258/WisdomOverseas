package com.example.mahesh.wisdomoverseas.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mahesh.wisdomoverseas.models.requests.UserLoginRequest;

public class SharedPref {

    private static Context context;
    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_PASSWORD = "keypassword";

    private static  final String Std_Pass_yr = "std_pass_yr";




    public SharedPref(Context context){
        this.context = context;
    }

    public final static String PREFS_NAME = "WisdomOverseas_Pref";
    public final static String PREFS_NAME2 = "Forgot_Pref";
    public final static String PREFS_NAMESTDINFO= "Stdinfo_Pref";



    public boolean userLogin(UserLoginRequest userLoginRequest) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userLoginRequest.userName);
        editor.putString(KEY_PASSWORD, userLoginRequest.pwd);
        editor.putString(KEY_USER_NAME, userLoginRequest.name);
        editor.apply();


        SharedPreferences sharedPreferences2 = context.getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor2.putString(KEY_USER_ID, userLoginRequest.userName);
        editor2.putString(KEY_PASSWORD, userLoginRequest.pwd);
        editor2.putString(KEY_USER_NAME, userLoginRequest.name);
        editor2.apply();


        return true;
    }


    public boolean saveData(){

        SharedPreferences sharedPreferences3 = context.getSharedPreferences(PREFS_NAMESTDINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPreferences3.edit();



        return  true;
    }


    /*public static String getStr(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(KEY_USER_ID,"");
    }*/

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_ID, null) != null)
            return true;
        return false;
    }
    public String getUserName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);
    }
    public String getUserNamePwd() {
        SharedPreferences sharedPreferences2 = context.getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
        return sharedPreferences2.getString(KEY_USER_ID, null);
    }
    public String getUName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }

    public String getPwd() {
        SharedPreferences sharedPreferences2 = context.getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
        return sharedPreferences2.getString(KEY_PASSWORD, null);
    }

    public static void deleteShared(){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        prefs.edit().clear().apply();

    }
}