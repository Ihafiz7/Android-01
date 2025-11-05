package com.hafiz.android_01.dbUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sqlDataBase extends SQLiteOpenHelper {
    final static String Name = "INVENTORY";
    public final static String TBL_NAME = "PRODUCT";
    public final static String COL_NAME = "NAME";
    public final static String COL_EMAIL = "EMAIL";
    public final static String COL_PRICE = "PRICE";
    public final static String COL_QUANTITY= "QUANTITY";


    public sqlDataBase(@Nullable Context context) {
        super(context, Name, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TBL_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL_NAME + " TEXT,"+ COL_EMAIL+
                " TEXT, "+ COL_PRICE+ " REAL, "+ COL_QUANTITY+" INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2){
            db.execSQL("ALTER TABLE PRODUCT ADD COLUMN IMAGE TEXT");
        }
    }
}
