package com.example.dbdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Shinelon on 2017/3/17.
 */
public class BookDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    public static final String CREAT_BOOK = "create table Book ("
            +"id integer primary key autoincrement,"
            + "author text,"
            + "price real,"
            + "pages integer,"
            + "name text)";
    public static final String CREAT_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement,"
            + "category_name text,"
            + "category_code integer)";

    /**
     * @param context 上下文
     * @param name    数据库名
     * @param factory 油标cursor 一般为null
     * @param version 版本
     */
    public BookDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_BOOK);
        db.execSQL(CREAT_CATEGORY);
        Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //升级数据库
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
