package com.iurii.retrofitexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.iurii.retrofitexample.R;
import com.iurii.retrofitexample.net.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//1
public class UserAdapter extends ArrayAdapter<User> {

    private static final int LAYOUT_RES_ID = R.layout.rowviev;

    private Context context;
    private LayoutInflater inflater;

    public UserAdapter(Context context) {
        super(context, 0, new ArrayList<User>());
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(LAYOUT_RES_ID, null);

        ((TextView) view.findViewById(R.id.tv_name)).setText(getItem(position).login);
        ((TextView) view.findViewById(R.id.tv_html_url)).setText(getItem(position).htmlUrl);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivImg);

        downloadImageView(position, imageView);

        return view;
    }

    private void downloadImageView(int position, ImageView imageView) {
        Picasso.with(this.getContext()) //передаем контекст приложения
                .load(getItem(position).avatarUrl)
                .placeholder(R.drawable.android_128x128_32)
                .resize(250, 250)
                .into(imageView); //ссылка на ImageView
    }
}