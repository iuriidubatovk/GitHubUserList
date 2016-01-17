package com.iurii.retrofitexample.dataBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.iurii.retrofitexample.dataBase.DbScheme.*;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "githubUserslist.db";
    private static final int DATABASE_VERSION = 1;
//Если у меня всего одна таблица в базе данных все равно нужно указывать имя таблицы USER_TABLE???
    public static final String CREATE_USER_TABLE = "create table " + USER_TABLE + " ("
            + "_id integer primary key autoincrement,"
            + USER_LOGIN + " text,"
            + USER_ID + " integer,"
            + USER_AVATAR_URL + " text,"
            + USER_HTML_URL + " text"
            + ");";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }
}
