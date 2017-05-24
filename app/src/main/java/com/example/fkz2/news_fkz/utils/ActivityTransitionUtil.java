package com.example.fkz2.news_fkz.utils;

import android.app.Activity;

import com.example.fkz2.news_fkz.R;


public class ActivityTransitionUtil {
    /**
     * 从右侧滑入
     *
     * @param activity activity
     */
    public static void startActivityTransition(Activity activity) {
        activity.overridePendingTransition(R.anim.swipeback_stack_right_in,
                R.anim.swipeback_stack_to_back);

    }

    /**
     * 从左侧滑出
     *
     * @param activity activity
     */
    public static void finishActivityTransition(Activity activity) {
        activity.overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }
}
