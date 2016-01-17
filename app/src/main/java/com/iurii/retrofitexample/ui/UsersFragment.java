package com.iurii.retrofitexample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.iurii.retrofitexample.R;
import com.iurii.retrofitexample.adapter.UserAdapter;
import com.iurii.retrofitexample.dataBase.GeneralUsersDbManager;
import com.iurii.retrofitexample.net.model.User;
import com.iurii.retrofitexample.net.retrofit.GitHubService;
import com.iurii.retrofitexample.uiUtils.UiUtils;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UsersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;
    private GeneralUsersDbManager usersDbManager;
    private RestAdapter restAdapter;
    private UserAdapter adapter;
    private static final String LOG_TAG = UsersFragment.class.getSimpleName();
    private static final String API_URL = "https://api.github.com/";
    private OnUsersFragmentItemClick onUsersFragmentItemClick;

    interface OnUsersFragmentItemClick {
        void onUsersFragmentItemClick(String userName, String htmlUrl, String avatarUrl);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(LOG_TAG, "onAttach");

        onUsersFragmentItemClick = (OnUsersFragmentItemClick) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");

        initializationAdapters();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");

        View view = createView(inflater);
        createSwipeLayout(view);
        createListView(view);
        return loadByRetrofit(view);
    }

    @Override
    public void onRefresh() {
        loadByRetrofit(true);
        //  swipeLayout.setRefreshing(true);
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 3000);
    }

    private View loadByRetrofit(View view) {
        List<User> users = usersDbManager.getUsers();
        if (users != null && !users.isEmpty()) {
            adapter.clear();
            adapter.addAll(users);
        } else {

            loadByRetrofit(true);
        }

        return view;
    }

    private void createListView(View view) {
        final ListView lv = (ListView) view.findViewById(R.id.lv_items);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String avatarUrl = adapter.getItem(position).avatarUrl;
                String htmlUrl = adapter.getItem(position).htmlUrl;
                String userName = adapter.getItem(position).login;

                onUsersFragmentItemClick.onUsersFragmentItemClick(userName, htmlUrl, avatarUrl);
            }
        });

        lv.setAdapter(adapter);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

                int topRowVerticalPosition = lv.getChildCount() == 0
                        ? 0
                        : lv.getChildAt(0).getTop();
                  swipeLayout.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    private void initializationAdapters() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();
        usersDbManager = new GeneralUsersDbManager(getActivity().getApplicationContext());
        adapter = new UserAdapter(getActivity());
    }

    private View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment1, null);

    }

    private void createSwipeLayout(View view) {
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(this);
        UiUtils.setSwipeRefreshColorScheme(swipeLayout);
        swipeLayout.setEnabled(false);
    }

    private void loadByRetrofit(final boolean isClear) {

        restAdapter.create(GitHubService.class).users(
                new Callback<List<User>>() {
                    @Override
                    public void success(List<User> users, Response response) {
                        Log.d(LOG_TAG, "success");

                        handleUsers(users, isClear);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure");

                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void handleUsers(List<User> users, boolean isClear) {
        if (isClear) {
            adapter.clear();
        }
        usersDbManager.saveUsers(users);
        adapter.addAll(users);
        adapter.notifyDataSetChanged();
    }
}
