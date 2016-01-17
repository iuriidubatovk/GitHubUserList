package com.iurii.retrofitexample.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.iurii.retrofitexample.R;
import com.squareup.picasso.Picasso;


public class AvatarFragment extends Fragment {

    public static final String ARGS_URL_IMAGE = "args_url_image";
    public static final String ARGS_NAME_USER = "args_name_user";
    public static final String ARGS_HTML_URL_USER = "args_html_url_user";

    private String htmlUrl;
    private ImageView imageView;
    private String avatarUrl;
    private String userName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        htmlUrl = getArguments().getString(ARGS_HTML_URL_USER);
        avatarUrl = getArguments().getString(ARGS_URL_IMAGE);
        userName = getArguments().getString(ARGS_NAME_USER);
        setUpActionBar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, null);
        ((TextView) v.findViewById(R.id.textView)).setText(String.valueOf(userName));

        imageView = (ImageView) v.findViewById(R.id.avatar_fragment);
        Animation changeTransparency = AnimationUtils.loadAnimation(getActivity(), R.anim.myalpha);
        imageView.startAnimation(changeTransparency);

        downloadImageView();

        return v;
    }

    private void downloadImageView() {
        Picasso.with(getActivity()) //передаем контекст приложения
                .load(avatarUrl)
                .into(imageView);
    }

    private void setUpActionBar() {
        setHasOptionsMenu(true);

        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(htmlUrl);
    }
}
