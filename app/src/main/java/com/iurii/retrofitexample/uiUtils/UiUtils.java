package com.iurii.retrofitexample.uiUtils;


import android.support.v4.widget.SwipeRefreshLayout;

public class UiUtils {
    /** Avoid creating an instance of this class */
    private UiUtils() { throw new AssertionError(); }

    public static void setSwipeRefreshColorScheme(SwipeRefreshLayout swipeLayout) {
        int[] colorScheme = new int[]{android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light};
        swipeLayout.setColorScheme(colorScheme[0], colorScheme[1], colorScheme[2], colorScheme[3]);
    }
}
