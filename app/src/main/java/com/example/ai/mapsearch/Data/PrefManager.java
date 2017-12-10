package com.example.ai.mapsearch.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ai.mapsearch.Utils.Constant;

/**
 * Created by A.I on 10/12/2017.
 */

public class PrefManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "mapTracker";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean login){
        editor.putBoolean(IS_LOGGED_IN, login);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void setUser(int id, String username, String name){
        editor.putInt("id", id);
        editor.putString("username", username);
        editor.putString("name", name);
        editor.commit();
    }

    public int getUserId(){
        return sharedPreferences.getInt("id", 0);
    }

    public String getUserName(){
        return sharedPreferences.getString("name", "null");
    }

    public void setDestination(int id){
        editor.putInt("destinationId", id);
        Log.d(Constant.TAG, "set pref destination " + id);
        editor.commit();
    }

    public int getDestinationId(){
        Log.d(Constant.TAG, "get pref destination " + sharedPreferences.getInt("destinationId", 0));
        return sharedPreferences.getInt("destinationId", 0);
    }

    public void deleteUser(){
        editor.remove("id").apply();
        editor.remove("username").apply();
        editor.remove("name").apply();
        editor.remove("destinationId").apply();
    }


}
