//package com.pixelsstudio.opensource.zhihudailyopensource.test;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * 用于定义数据库名称，数据库版本，创建表名。
// */
//
//public class BaseSQLiteOpenHelper extends SQLiteOpenHelper {
//    String create_table = "create table test(id integer primary key autoincrement,name varchar(20), waihao text, classdata text)";
//
//    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // 创建一张表
//        db.execSQL(create_table);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    }
//}
