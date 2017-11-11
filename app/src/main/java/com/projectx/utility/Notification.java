package com.projectx.utility;

import android.content.Context;
import android.widget.Toast;



public class Notification {
    public static void NotifyToast(String message, Context context)
    {
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
