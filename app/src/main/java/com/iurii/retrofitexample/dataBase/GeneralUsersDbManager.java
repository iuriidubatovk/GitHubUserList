package com.iurii.retrofitexample.dataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iurii.retrofitexample.dataBase.DbHelper;
import com.iurii.retrofitexample.dataBase.DbHelperSingleton;
import com.iurii.retrofitexample.net.model.User;

import java.util.LinkedList;
import java.util.List;

import static com.iurii.retrofitexample.dataBase.DbScheme.*;



public class GeneralUsersDbManager {
    private Context context;

    public GeneralUsersDbManager(Context context) {
        this.context = context;
    }

    public void saveUsers(List<User> users) {
        clearUserTable();

        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        ContentValues cv = new ContentValues();

        for (User u : users) {
            cv.put(USER_LOGIN, u.login);
            cv.put(USER_ID, u.id);
            cv.put(USER_AVATAR_URL, u.avatarUrl);
            cv.put(USER_HTML_URL, u.htmlUrl);

            db.insert(USER_TABLE, null, cv);
            cv.clear();
        }

        DbHelperSingleton.closeDb();
    }

    public void clearUserTable() {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL(DbHelper.CREATE_USER_TABLE);
    }
    public List<User> getUsers() {
        List<User> users = new LinkedList<>();
        Cursor c = DbHelperSingleton.getDb(context)
                .query(USER_TABLE, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                users.add(
                        new User(
                                c.getString(c.getColumnIndex(USER_LOGIN)),
                                c.getInt(c.getColumnIndex(USER_ID)),
                                c.getString(c.getColumnIndex(USER_AVATAR_URL)),
                                c.getString(c.getColumnIndex(USER_HTML_URL))
                        )
                );
            } while (c.moveToNext());
        }
        c.close();
        DbHelperSingleton.closeDb();
        return users;
    }

}
