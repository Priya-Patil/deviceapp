package com.pvp.deviceapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PrefManager {

    private static final String TAG = PrefManager.class.getSimpleName();


    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    public static int PRIVATE_MODE = 0;
    // Shared preferences file name
    public static final String PREF_NAME = "Dairy";
    // All Shared Preferences Keys
    private static final String ROLE_ID = "r_id";
    private static final String CLASS_ID = "c_id";
    private static final String USER_ID = "u_id";
    private static final String Selected_class_ID = "s_id";
    private static final String Selected_Teacher_ID = "t_id";
    private static final String Q_NO = "no";
    private static final String TIME = "time";
    private static final String TOTAL_FEES = "tot_fees";
    private static final String EXCEED_FEES = "exc_fees";
    private static final String DISCOUNT_FEES = "dis_fees";
    private static final String bl = "bl";
    private static final String action = "action";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //
    public void setROLE_ID(int uid) {
        editor.putInt(ROLE_ID, uid);
        editor.commit();
    }

    public void setUSER_ID(int uid) {
        editor.putInt(USER_ID, uid);
        editor.commit();
    }

    public int getUSER_ID() {
        return pref.getInt(USER_ID, 0);
    }

    public void setTOTAL_FEES(int fid) {
        editor.putInt(TOTAL_FEES, fid);
        editor.commit();
    }

    public int getTOTAL_FEES() {
        return pref.getInt(TOTAL_FEES, 0);
    }

    public void setEXCEED_FEES(int fid) {
        editor.putInt(EXCEED_FEES, fid);
        editor.commit();
    }

    public int getEXCEED_FEES() {
        return pref.getInt(EXCEED_FEES, 0);
    }


    public int getROLE_ID() {
        return pref.getInt(ROLE_ID, 0);
    }

    public void setCLASS_ID(int uid) {
        editor.putInt(CLASS_ID, uid);
        editor.commit();
    }

    public int getCLASS_ID() {
        return pref.getInt(CLASS_ID, 0);
    }

    public void setDISCOUNT_FEES(int uid) {
        editor.putInt(DISCOUNT_FEES, uid);
        editor.commit();
    }

    public int getDISCOUNT_FEES (){
        return pref.getInt(DISCOUNT_FEES, 0);
    }


    public void setSelected_class_ID(int uid) {
        editor.putInt(Selected_class_ID, uid);
        editor.commit();
    }

    public int getSelected_class_ID() {
        return pref.getInt(Selected_class_ID, 0);
    }

    public void setSelected_Teacher_ID(int tid) {
        editor.putInt(Selected_Teacher_ID, tid);
        editor.commit();
    }

    public int getSelected_Teacher_ID() {
        return pref.getInt(Selected_Teacher_ID, 0);
    }

    public void setQ_NO(String qno) {
        editor.putString(Q_NO, qno);
        editor.commit();
    }

    public String getQ_NO() {
        return pref.getString(Q_NO, null);
    }

    public void setTIME(String t) {
        editor.putString(TIME, t);
        editor.commit();
    }

    public String getTIME() {
        return pref.getString(TIME, null);
    }





    public void setAction(String qno) {
        editor.putString(Q_NO, qno);
        editor.commit();
    }

    public String getAction() {
        return pref.getString(Q_NO, null);
    }

}