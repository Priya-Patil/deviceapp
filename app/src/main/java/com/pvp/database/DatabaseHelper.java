package com.pvp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "cbroz.db";

    public final static String TABLE_NEW_CUSTOMER = "tbl_setting";

    public final static String CUST_1 = "id";
    public final static String CUST_2 = "Internal_diameter";
    public final static String CUST_3 = "Number_of_terms";
    public final static String CUST_4 = "Wire_diameter";
    public final static String CUST_5 = "Start_wait_time";
    public final static String CUST_6 = "Stop_wait_time";
    public final static String CUST_7 = "Machine_Speed";
    public final static String CUST_8 = "Die_Diameter";
    public final static String CUST_9 = "isactive";
    public final static String CUST_10 = "created";
    public final static String CUST_11 = "title";


    public final static String TABLE_ORDERS = "orders";

    public final static String ORD_1 = "orderid";
    public final static String ORD_2 = "userid";
    public final static String ORD_3 = "productname";
    public final static String ORD_4 = "productquantity";
    public final static String ORD_5 = "amountpayable";
    public final static String ORD_6 = "deliverydate";
    public final static String ORD_7 = "deliverypersonId";//dboyid
    public final static String ORD_8 = "isactive";


    public final static String TABLE_DROP_ORDER = "droporder";

    public final static String DRP_ORD_1 = "droporderid";
    public final static String DRP_ORD_2 = "userid";
    public final static String DRP_ORD_3 = "dboyid";//dboyid
    public final static String DRP_ORD_4 = "startdate";
    public final static String DRP_ORD_5 = "enddate";
    public final static String DRP_ORD_6 = "isactive";


    public final static String TABLE_PAYMENT = "payment";

    public final static String PAY_1 = "paymentid";
    public final static String PAY_2 = "userid";
    public final static String PAY_3 = "amount";
    public final static String PAY_4 = "pdate";
    public final static String PAY_5 = "month";
    public final static String PAY_6 = "deliverypersonId";

    public final static String TABLE_VALIDITY = "validity";

    public final static String VALIDITY_1 = "validityid";
    public final static String VALIDITY_2 = "userid";
    public final static String VALIDITY_3 = "expirydate";


    Context context;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NEW_CUSTOMER+
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," + //1
                "Internal_diameter TEXT," +                      //2
                "Number_of_terms TEXT," +                            //3
                "Wire_diameter TEXT," +                        //4
                "Start_wait_time TEXT," +                           //5
                "Stop_wait_time TEXT," +                        //6
                "Machine_Speed TEXT," +                              //7
                "Die_Diameter TEXT," +                         //8
                "isactive TEXT," +                              //9
                "created TEXT," +                              //9
                "title TEXT)");                          //10


        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_ORDERS+
                " (orderid INTEGER PRIMARY KEY AUTOINCREMENT," + //1
                "userid INTEGER," +                      //2
                "productname TEXT," +                            //3
                "productquantity INTEGER," +                        //4
                "amountpayable REAL," +                           //5
                "deliverydate TEXT," +                        //6
                "deliverypersonId INTEGER," +                              //7
                "isactive INTEGER)");                        //8

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_DROP_ORDER+
                " (droporderid INTEGER PRIMARY KEY AUTOINCREMENT," + //1
                " userid INTEGER," +                      //2
                "dboyid INTEGER," +                      //3
                "startdate TEXT," +                    //4
                "enddate TEXT," +                      //5
                "isactive INTEGER)");
                                                 //6

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_PAYMENT +
                " (paymentid INTEGER PRIMARY KEY AUTOINCREMENT," + //1
                "userid INTEGER," +                      //2
                "amount REAL," +                      //3
                "pdate TEXT," +                          //4
                "month INTEGER," +
                 "deliverypersonId INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_VALIDITY +
                " ( validityid INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "userid INTEGER," +
                "expirydate TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DROP_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VALIDITY);
    }


    //to insert data inside service booking table
    public boolean insertData(  String Internal_diameter,String Number_of_terms,String Wire_diameter,
                               String Start_wait_time,
                              String Stop_wait_time,String Machine_Speed,String Die_Diameter,
                              String isactive,String  created, String title)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CUST_2,Internal_diameter);
        contentValues.put(CUST_3,Number_of_terms);
        contentValues.put(CUST_4,Wire_diameter);
        contentValues.put(CUST_5,Start_wait_time);
        contentValues.put(CUST_6,Stop_wait_time);
        contentValues.put(CUST_7,Machine_Speed);
        contentValues.put(CUST_8,Die_Diameter);
        contentValues.put(CUST_9,isactive);
        contentValues.put(CUST_10,created);
        contentValues.put(CUST_11,title);


        //long result = db.insert(TABLE_NEW_CUSTOMER, null, contentValues);

        long result = db.insert(TABLE_NEW_CUSTOMER, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    public boolean UpdateData(String id,String Internal_diameter,String Number_of_terms,String Wire_diameter,
                              String Start_wait_time,String Stop_wait_time,
                              String Machine_Speed, String Die_Diameter, String title) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CUST_1, id);
        contentValues.put(CUST_2, Internal_diameter);
        contentValues.put(CUST_3, Number_of_terms);
        contentValues.put(CUST_4, Wire_diameter);
        contentValues.put(CUST_5, Start_wait_time);
        contentValues.put(CUST_6, Stop_wait_time);
        contentValues.put(CUST_7, Machine_Speed);
        contentValues.put(CUST_8, Die_Diameter);
        contentValues.put(CUST_11, title);

        db.update(TABLE_NEW_CUSTOMER,contentValues,"id=?",new String[]{id});//new String[]{ id });
        return  true;
    }

    public Cursor getData(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM "+TABLE_NEW_CUSTOMER+" WHERE id='"+id+"'";

        Cursor cursor = db.rawQuery(query, null);

        return cursor;

    }

    public boolean Delete(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CUST_9, 0);

        db.update(TABLE_NEW_CUSTOMER,contentValues,"id=?",new String[]{id});//new String[]{ id });
        return  true;
    }


}


