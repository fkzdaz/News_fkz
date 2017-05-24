package com.example.fkz2.news_fkz.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity启动util
 */
public class StartActivityUtil {

    public static void start(Activity activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        ActivityTransitionUtil.startActivityTransition(activity);
    }

    public static void start(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        ActivityTransitionUtil.startActivityTransition(activity);
    }
}
