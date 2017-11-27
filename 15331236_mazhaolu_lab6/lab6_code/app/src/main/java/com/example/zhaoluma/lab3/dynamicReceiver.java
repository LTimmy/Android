package com.example.zhaoluma.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * Created by zhaoluma on 2017/10/26.
 */

public class dynamicReceiver extends BroadcastReceiver {
    private String shop_name;
    private int shop_image;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle=intent.getExtras();
        shop_name = bundle.getString("shop_name");
        shop_image = bundle.getInt("shop_image");
        Intent clickintent = new Intent(context,shopList.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickintent, 0);
        NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("马上下单").
                setLargeIcon(BitmapFactory.decodeResource(context.getResources(),shop_image)).
                setSmallIcon(shop_image).setContentText(shop_name+"已添加到购物车").
                setContentIntent(pendingIntent).setAutoCancel(true);
        Notification notify = builder.build();
        notifyManager.notify(0,notify);
    }
}
