package com.pvp.deviceapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PrefHandler {

    private static final String TAG = PrefHandler.class.getSimpleName();

    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    private static final String ROLE_ID = "r_id";

    Context _context;
    // Shared pref mode
    public static int PRIVATE_MODE = 0;
    // Shared preferences file name
    public static final String PREF_NAME = "Dairy";


    public PrefHandler(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


  public void setROLE_ID(int uid) {
        editor.putInt(ROLE_ID, uid);
        editor.commit();
    }

    public int getROLE_ID() {
        return pref.getInt(ROLE_ID, 0);
    }


    public static boolean removeUserInSharedPref(Context mContext) {
        if (mContext != null) {
            SharedPreferences mSharedPreferences = mContext.getSharedPreferences("selectedUser", 0);
            if (mSharedPreferences != null)
                Log.e(TAG, "removeUserInSharedPref: notnull");
            SharedPreferences.Editor blockEdit = mSharedPreferences.edit();
            blockEdit.clear();
            blockEdit.apply();
            return true;
            //return mSharedPreferences.edit().remove("selectedUser").apply();
        }
        return false;
    }


}
