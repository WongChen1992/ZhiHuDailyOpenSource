package com.pixelsstudio.opensource.zhihudailyopensource.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WongChen on 2017/3/18.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diary_db";
    private static final int VERSION = 1;
    public static final String TABLE_STARRED = "starred";
    public static final String TABLE_COLLECTION = "collection";
    public static final String TABLE_READ = "read";//已读列表
    private static SQLiteDBHelper mSQLiteDBHelper = null;
    private SQLiteDatabase db;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        db = getWritableDatabase();
    }

    public static SQLiteDBHelper getInstens(Context context) {
        if (mSQLiteDBHelper == null) {
            mSQLiteDBHelper = new SQLiteDBHelper(context);
        }
        return mSQLiteDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSQL = "create table "
                + TABLE_STARRED
                + "(newsId text primary key, newsData text)";
        String _strSQL = "create table "
                + TABLE_COLLECTION
                + "(newsId text primary key, newsData text)";
        String __strSQL = "create table "
                + TABLE_READ
                + "(newsId text primary key, newsData text)";
        db.execSQL(strSQL);
        db.execSQL(_strSQL);
        db.execSQL(__strSQL);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 插入新闻
     *
     * @param newsBean
     */
    public long insert(String tableName,int id,ListNews.StoriesEntity newsBean) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(newsBean);
            objectOutputStream.flush();

            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();

            ContentValues values = new ContentValues();
            values.put("newsData", data);
            values.put("newsId", String.valueOf(id));

            return db.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 根据ID查询新闻
     *
     * @param id
     * @param columnName
     */
    public ListNews.StoriesEntity query(String tableName,String[] id, String[] columnName) {
        Cursor cursor = db.query(
                tableName,
                columnName,
                "newsId=?",
                id,
                null,
                null,
                null
        );
        ListNews.StoriesEntity newsBean;
        while (cursor.moveToNext()) {
            for (String string : columnName) {
                byte[] data = cursor.getBlob(cursor.getColumnIndex(string));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    newsBean = (ListNews.StoriesEntity) inputStream.readObject();
                    inputStream.close();
                    arrayInputStream.close();
                    return newsBean;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        cursor.close();
        return null;
    }

    public boolean query(String tableName, String[] columnName) {
        Cursor cursor = getReadableDatabase().query(tableName, null, "newsId=?", columnName, null, null, null);
        return cursor.getCount() != 0 ? true : false;
    }

    public List<ListNews.StoriesEntity> queryAll(String tableName){
        List<ListNews.StoriesEntity> dataList = new ArrayList<>();
        Cursor cursor = db.query(tableName,  null, null, null, null, null, null);
        if (cursor != null){
            while (cursor.moveToNext()){
                ListNews.StoriesEntity testBean = null;
                byte[] data = cursor.getBlob(cursor.getColumnIndex("newsData"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    testBean = (ListNews.StoriesEntity) inputStream.readObject();
                    inputStream.close();
                    arrayInputStream.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dataList.add(testBean);
            }
        }
        return dataList;
    }


    /**
     * 删除
     *
     * @param id
     */
    public int delete(String tableName,String[] id) {
       return db.delete(tableName, "newsId=?", id);
    }
}
