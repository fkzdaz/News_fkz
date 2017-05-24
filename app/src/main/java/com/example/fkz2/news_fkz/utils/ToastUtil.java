
package com.example.fkz2.news_fkz.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtil {

    /**
     * 显示短时间的Toast
     *
     * @param context context
     * @param msg     提示内容
     */
    public static void showSortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 显示长时间的Toast
     *
     * @param context context
     * @param msg     提示内容
     */
    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
