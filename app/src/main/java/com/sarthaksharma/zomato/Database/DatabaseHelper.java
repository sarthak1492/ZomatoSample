package com.sarthaksharma.zomato.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sarthak.Sharma on 28-12-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    byte[] input;

    byte[] keyBytes;
    byte[] ivBytes;

    //DATABASE VERSION
    public static final int DATABSE_VERSION = 1;

    //DATABASE NAME
    public static final String DATABASE_NAME = "ZomatoDB.db";

    //TABLE NAME
    private static final String TABLE_NAME = "tblUsers";

    //COLUMNS
    public static final String Name = "Name";
    public static final String EmailID = "EmailID";
    public static final String Password = "Password";
    public static final String PhoneNumber = "Phone";
    public static final String Location = "Location";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
        db = this.getWritableDatabase();
    }

    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + Name + " TEXT, " + EmailID + " TEXT PRIMARY KEY, " + Password + " TEXT, "
            + PhoneNumber + " INTEGER, " + Location + " TEXT" + ")";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean fncInsertDataToTable(String strName, String strEmailID, String strPassword, String strPhone, String strLocation) {
//            SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
//            IvParameterSpec ivspec = new IvParameterSpec(ivBytes);
//            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//
//            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
//            byte[] encrypted = new byte[cipher.getOutputSize(input.length)];
//            int enc_len = cipher.update(input, 0, input.length, encrypted, 0);
//            enc_len += cipher.doFinal(encrypted, enc_len);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, strName);
        values.put(EmailID, strEmailID);
        values.put(Password, strPassword);
        values.put(PhoneNumber, strPhone);
        values.put(Location, strLocation);

        // INSERTING ROW
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public String fncUpdateTable(String strEmail, String strPhone, String strLocation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneNumber, strPhone);
        values.put(Location, "'" + strLocation +"'");
        try {
            db.update(TABLE_NAME, values , "EmailID = " + "'" + strEmail + "'", null);
        }catch (Exception e){
            e.printStackTrace();
        }

        return strPhone;
    }

    public String fncGetUserValue(String strField){
        SQLiteDatabase db = this.getWritableDatabase();
        String strRetrieveValue = "";
        Cursor cursor = db.rawQuery("SELECT " + strField + " FROM " + TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()){
            strRetrieveValue = cursor.getString(0);
        }
        cursor.close();
        return strRetrieveValue;
    }
}
