package com.google.sites.lostandfoundapp.log;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Isaac on 3/7/2018.
 */

public class ToastLog {
    private static ToastLog INSTANCE = null;

    private ToastLog() {}

    public void toastMessage(final Context context, final String message, final int duration) {
        final Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public static ToastLog getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ToastLog();
        }
        return INSTANCE;
    }
}
