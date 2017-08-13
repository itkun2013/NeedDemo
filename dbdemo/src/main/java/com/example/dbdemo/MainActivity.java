package com.example.dbdemo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dbdemo.db.BookDatabaseHelper;
import com.example.dbdemo.db.DbBookDao;


public class MainActivity extends AppCompatActivity {

    private Button buttonCreat;
    private Button buttonInsert;
    private Button buttonUpdata;
    private Button buttonDelete;
    private Button buttonQuery;
    private DbBookDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonCreat = (Button) findViewById(R.id.btn_creat);
        buttonInsert = (Button) findViewById(R.id.btn_insert);
        buttonUpdata = (Button) findViewById(R.id.btn_update);
        buttonDelete = (Button) findViewById(R.id.btn_delete);
        buttonQuery = (Button) findViewById(R.id.btn_query);
        dao = new DbBookDao(this);
        buttonCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.insertData();

            }
        });
        buttonUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.updata();

            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.delete();

            }
        });
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.query();
            }
        });

    }
}
