package com.example.dbdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Shinelon on 2017/3/17.
 */
public class DbBookDao {
    private BookDatabaseHelper databaseHelper;

    public DbBookDao(Context context) {
        databaseHelper = new BookDatabaseHelper(context, "BookStore.db", null, 1);
    }

    /**
     * 插入数据
     */
    public boolean insertData() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("name", "english");
        values.put("author", "老舍");
        values.put("price", 16.95);
        values.put("pages", 454);
        db.insert("Book", null, values);//插入第一条数据
        values.clear();
        //开始插入第二条数据
        values.put("name", "chinese");
        values.put("author", "巴金");
        values.put("price", 13.14);
        values.put("pages", 300);
        long insert = db.insert("Book", null, values);
        db.close();
        return insert != -1;
    }

    /**
     * 更改数据
     */
    public void updata() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", 10000);
        db.update("Book", values, "name=?", new String[]{"chinese"});
    }

    /**
     * 删除数据
     */
    public void delete() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        db.delete("Book", "name=?", new String[]{"english"});
    }

    /**
     * 查询数据
     */
    public void query() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        //查询Book标中所有数据
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //得到相应name位置的索引
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String auther = cursor.getString(cursor.getColumnIndex("author"));
                String pages = cursor.getString(cursor.getColumnIndex("pages"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                Log.e("db","name :"+name+" @ auther:"+auther+" @ pages："+pages+" @ price:"+price);

            }
        }else{
            Log.e("db","curser为空");

        }

        cursor.close();
    }
}
