package com.hafiz.android_01.dbUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hafiz.android_01.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductUti {
    private final sqlDataBase dbHelper;

    public ProductUti(Context context){
        dbHelper = new sqlDataBase(context);
    }

//    public long insert(Product product){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(sqlDataBase.COL_NAME, product.getName());
//        values.put(sqlDataBase.COL_EMAIL, product.getEmail());
//        values.put(sqlDataBase.COL_PRICE,product.getPrice());
//        values.put(sqlDataBase.COL_QUANTITY,product.getQuantity());
//
//        long id = db.insert(sqlDataBase.TBL_NAME,null,values);
//        db.close();
//        return id;
//    }
//
//    public List<Product> getAllProducts(){
//        List<Product> list = new ArrayList<>();
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("Select * from "+ sqlDataBase.TBL_NAME, null);
//        if(cursor.moveToFirst()){
//            do {
//                Product p = new Product();
//                p.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
//                p.setName(cursor.getString(cursor.getColumnIndexOrThrow(sqlDataBase.COL_NAME)));
//                p.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(sqlDataBase.COL_EMAIL)));
//                p.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(sqlDataBase.COL_PRICE)));
//                p.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(sqlDataBase.COL_QUANTITY)));
//
//                list.add(p);
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }
//
//    public int update(Product product){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(sqlDataBase.COL_NAME, product.getName());
//        values.put(sqlDataBase.COL_EMAIL, product.getEmail());
//        values.put(sqlDataBase.COL_PRICE,product.getPrice());
//        values.put(sqlDataBase.COL_QUANTITY,product.getQuantity());
//
//        int rows = db.update(sqlDataBase.TBL_NAME,values,"ID = ? ", new String[]{String.valueOf(product.getId())});
//        db.close();
//        return rows;
//    }
//
//
//    public int delete(int id){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        int rows = db.delete(sqlDataBase.TBL_NAME, "ID = ?", new String[]{String.valueOf(id)});
//        db.close();
//        return rows;
//    }

    public long insert(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(sqlDataBase.COL_NAME, product.getName());
        values.put(sqlDataBase.COL_EMAIL, product.getEmail());
        values.put(sqlDataBase.COL_PRICE, product.getPrice());
        values.put(sqlDataBase.COL_QUANTITY, product.getQuantity());
        values.put("IMAGE", product.getImage()); // new field

        long id = db.insert(sqlDataBase.TBL_NAME, null, values);
        db.close();
        return id;
    }

    // READ ALL
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + sqlDataBase.TBL_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Product p = new Product(
                        cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow(sqlDataBase.COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(sqlDataBase.COL_EMAIL)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(sqlDataBase.COL_PRICE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(sqlDataBase.COL_QUANTITY)),
                        cursor.getString(cursor.getColumnIndexOrThrow("IMAGE"))
                );

                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // PRODUCT BY ID (for edit)
    public Product getProductById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + sqlDataBase.TBL_NAME + " WHERE ID = ?", new String[]{String.valueOf(id)});
        Product p = null;
        if (cursor.moveToFirst()) {
            p = new Product(
                    cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                    cursor.getString(cursor.getColumnIndexOrThrow(sqlDataBase.COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(sqlDataBase.COL_EMAIL)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(sqlDataBase.COL_PRICE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(sqlDataBase.COL_QUANTITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow("IMAGE"))
            );
        }
        cursor.close();
        db.close();
        return p;
    }

    // UPDATE
    public int update(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(sqlDataBase.COL_NAME, product.getName());
        values.put(sqlDataBase.COL_EMAIL, product.getEmail());
        values.put(sqlDataBase.COL_PRICE, product.getPrice());
        values.put(sqlDataBase.COL_QUANTITY, product.getQuantity());
        values.put("IMAGE", product.getImage());

        int rows = db.update(sqlDataBase.TBL_NAME, values, "ID = ? ", new String[]{String.valueOf(product.getId())});
        db.close();
        return rows;
    }

    // DELETE
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(sqlDataBase.TBL_NAME, "ID = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
}
