//package com.pixelsstudio.opensource.zhihudailyopensource.test;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.util.Log;
//
//import com.pixelsstudio.opensource.zhihudailyopensource.bean.TestBean;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
///**
// * 数据库操作辅助类
// */
//
//public class DatabaseHlper {
//    private static BaseSQLiteOpenHelper baseSQLiteOpenHelper = null;
//    private static DatabaseHlper databaseHlper = null;
//
//    private DatabaseHlper(Context context, String dbname, int version) {
//        baseSQLiteOpenHelper = new BaseSQLiteOpenHelper(context, dbname, null, version);
//    }
//
//    public static DatabaseHlper newInstance(Context context, String dbname, int version) {
//        if (databaseHlper != null) {
//            return databaseHlper;
//        }
//        databaseHlper = new DatabaseHlper(context, dbname, version);
//        return databaseHlper;
//    }
//
//    /**
//     * 插入 String
//     * @param tableName
//     * @param data
//     */
//    public void insert(String tableName, HashMap<String, String> data) {
//        ContentValues values = new ContentValues();
//
//        Set set = data.entrySet();
//        Iterator iterator = set.iterator();
//        while(iterator.hasNext()){
//            Map.Entry entry = (Map.Entry) iterator.next();
//            values.put((String)entry.getKey(), (String)entry.getValue());
//        }
//        baseSQLiteOpenHelper.getWritableDatabase().insert(tableName, null, values);
//    }
//
//    /**
//     * 查询对象
//     * @param tableName
//     * @param testBean
//     * @param key
//     */
//    public void insert(String tableName, TestBean testBean, String key){
//        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//        try {
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
//            objectOutputStream.writeObject(testBean);
//            objectOutputStream.flush();
//
//            byte data[] = arrayOutputStream.toByteArray();
//            objectOutputStream.close();
//            arrayOutputStream.close();
//
//            ContentValues values = new ContentValues();
//            values.put(key, data);
//
//            baseSQLiteOpenHelper.getWritableDatabase().insert(tableName, null, values);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 查询
//     * @param tableName
//     * @param id
//     * @param columnName
//     */
//    public void query(String tableName, String[] id, String... columnName) {
//        Cursor cursor = baseSQLiteOpenHelper.getReadableDatabase().query(
//                tableName,
//                columnName,
//                "id=?",
//                id,
//                null,
//                null,
//                null
//        );
//
//        while (cursor.moveToNext()) {
//            for (String string : columnName){
//                String value = cursor.getString(cursor.getColumnIndex(string));
//                Log.i("test", string + ":" + value + " ");
//            }
//        }
//
//        cursor.close();
//    }
//
//    /**
//     * 查询一个对象
//     * @param tableName
//     * @param id
//     * @param columnName
//     */
//    public void queryClass(String tableName, String[] id, String[] columnName){
//        Cursor cursor = baseSQLiteOpenHelper.getReadableDatabase().query(
//                tableName,
//                columnName,
//                "id=?",
//                id,
//                null,
//                null,
//                null
//        );
//
//        TestBean testBean = null;
//        while (cursor.moveToNext()) {
//            for (String string : columnName){
//                byte[] data = cursor.getBlob(cursor.getColumnIndex(string));
//                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
//                try {
//                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
//                    testBean = (TestBean) inputStream.readObject();
//                    inputStream.close();
//                    arrayInputStream.close();
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                Log.i("test", string + ":" + testBean.getName() + " " + testBean.getAge());
//            }
//        }
//
//        cursor.close();
//    }
//
//    /**
//     *  更新
//     * @param stableName
//     * @param data
//     *      要修改的列名和所对应的值的集合
//     * @param id
//     *      要更新的where子句的条件（这里的id数组不代表一次可以更新多个id的值，
//     *      一次只能更新一个id的值，所以，这里的id如果这样传值new Stirng[]{"1", "2"}就会出错）
//     */
//    public void update(String stableName,  HashMap<String, String> data, String[] id) {
//        ContentValues values = new ContentValues();
//        Set set = data.entrySet();
//        Iterator iterator = set.iterator();
//        while(iterator.hasNext()){
//            Map.Entry entry = (Map.Entry) iterator.next();
//            values.put((String)entry.getKey(), (String)entry.getValue());
//        }
//
//        baseSQLiteOpenHelper.getWritableDatabase().update(
//                stableName,
//                values,
//                "id=?",
//                id
//        );
//    }
//
//    /**
//     * 删除
//     * @param stableName
//     * @param id
//     */
//    public void delete(String stableName, String[] id) {
//        baseSQLiteOpenHelper.getWritableDatabase().delete(
//                stableName,
//                "id=?",
//                id
//        );
//    }
//}
