package com.example.zhaoluma.lab3;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

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
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals("DYNAMICATION")) {
            Bundle bundle=intent.getExtras();
            shop_name = bundle.getString("shop_name");
            shop_image = bundle.getInt("shop_image");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setTextViewText(R.id.appwidget_text, shop_name+"已添加到购物车");
            views.setImageViewResource(R.id.appwidget_imageview,shop_image);
            ComponentName me = new ComponentName(context,Widget.class);
            appWidgetManager.updateAppWidget(me,views);
            Intent clickintent = new Intent(context,shopList.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickintent, 0);
            views.setOnClickPendingIntent(R.id.widget_item,pendingIntent);
            appWidgetManager.updateAppWidget(me,views);
            //Toast.makeText(context,"You hit me!"+shop_name,Toast.LENGTH_LONG).show();
            /*Intent clickintent = new Intent(context,shopList.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickintent, 0);
            Notifi cationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("马上下单").
                    setLargeIcon(BitmapFactory.decodeResource(context.getResources(),shop_image)).
                    setSmallIcon(shop_image).setContentText(shop_name+"已添加到购物车").
                    setContentIntent(pendingIntent).setAutoCancel(true);
            Notification notify = builder.build();
            notifyManager.notify(0,notify);*/
        }

    }
}
