package com.bignerdranch.android.photogallery;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import static com.bignerdranch.android.photogallery.PollService.NOTIFICATION;
import static com.bignerdranch.android.photogallery.PollService.REQUEST_CODE;

/**
 * Created by francisco on 14/10/17.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Updating...", Toast.LENGTH_LONG);
        if (getResultCode() != Activity.RESULT_OK) {
            return;
        }
        int requestCode = intent.getIntExtra(REQUEST_CODE, 0);
        Notification notification = (Notification)intent.getParcelableExtra(NOTIFICATION);
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(requestCode, notification);
    }
}
