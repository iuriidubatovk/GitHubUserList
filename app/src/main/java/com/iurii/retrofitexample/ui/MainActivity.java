package com.iurii.retrofitexample.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import com.iurii.retrofitexample.R;

public class MainActivity extends ActionBarActivity implements UsersFragment.OnUsersFragmentItemClick {

    private FragmentManager fragmentManager;
    AvatarFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmCont, new UsersFragment())
                .commit();
    }

    @Override
    public void onUsersFragmentItemClick(String userName, String htmlUrl, String avatarUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(AvatarFragment.ARGS_HTML_URL_USER, htmlUrl);
        bundle.putString(AvatarFragment.ARGS_URL_IMAGE, avatarUrl);
        bundle.putString(AvatarFragment.ARGS_NAME_USER, userName);
        fragment = new AvatarFragment();
        fragment.setArguments(bundle);

        changeFragment(fragment);
    }

    private void changeFragment(Fragment fragment) {
        String fragmentTag = fragment.getClass().getSimpleName();

        fragmentManager.executePendingTransactions();
        if (fragmentManager.findFragmentByTag(fragmentTag) != null) {
            return;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragmCont, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(R.string.app_name);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(R.string.app_name);
    }
}



