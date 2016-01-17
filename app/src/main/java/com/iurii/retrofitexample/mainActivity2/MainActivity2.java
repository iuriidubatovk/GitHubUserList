package com.iurii.retrofitexample.mainActivity2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.iurii.retrofitexample.R;
import com.squareup.picasso.Picasso;

public class MainActivity2 extends ActionBarActivity {

    public static final String ARGS_USER_LOGIN = "args_user_name";
    public static final String ARGS_URL_IMAGE = "args_url_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.imageView33);
        Intent intent = getIntent();
        String userLogin = intent.getStringExtra(ARGS_USER_LOGIN);
        String userAvatarUrl = intent.getStringExtra(ARGS_URL_IMAGE);

        getSupportActionBar().setTitle(userLogin);

        Picasso.with(this) //передаем контекст приложения
                .load(userAvatarUrl)
                .placeholder(R.drawable.android_128x128_32)
                .into(imageView); //ссылка на ImageView
    }
}