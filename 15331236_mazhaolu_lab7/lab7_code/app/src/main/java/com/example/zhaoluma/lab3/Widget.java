package com.example.zhaoluma.lab3;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    private String random_name;
    private String random_price;
    private int random_image;
    private String random_birth;
    private String random_initial;
    private String shop_name;
    private int shop_image;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = "当前没有任何信息";
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            Intent i = new Intent(context,lab3.class);  // 首先，在OnUpdate()方法定义一个Intent，用来开启Activity 的Intent
            // 第一个参数上下文，第二个参数－要开启的Activity类

            // 顾名思义，就是组件名称，通过调用Intent中的setComponent方法，我们可以打开另外一个应用中的Activity或者服务。
            ComponentName me = new ComponentName(context,Widget.class);
            //    用该Intent实例化一个PendingIntent
            PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            // 实例化RemoteView,其对应相应的Widget布局
            RemoteViews updateView = new RemoteViews(context.getPackageName(),R.layout.widget);
            updateView.setOnClickPendingIntent(R.id.widget_item, pi);   // 设置点击事件
            appWidgetManager.updateAppWidget(me, updateView);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals("MystaticFliter")) {

            Bundle bundle=intent.getExtras();
            random_name = bundle.getString("random_name");
            random_price = bundle.getString("random_price");
            random_image = bundle.getInt("random_image");
            random_birth = bundle.getString("random_birth");
            random_initial = bundle.getString("random_initial");
            //Toast.makeText(context,"You hit me!"+random_price+random_initial,Toast.LENGTH_LONG).show();
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setTextViewText(R.id.appwidget_text, random_name+"仅售"+random_price+"!");
            views.setImageViewResource(R.id.appwidget_imageview,random_image);
            ComponentName me = new ComponentName(context,Widget.class);
            appWidgetManager.updateAppWidget(me,views);
            Intent clickintent = new Intent(context,detials.class);
            Bundle bundle_ = new Bundle();
            bundle_.putString("initial_1",random_initial);
            bundle_.putString("name2",random_name);
            bundle_.putString("birth",random_birth);
            bundle_.putString("price2",random_price);
            bundle_.putInt("image",random_image);
            clickintent.putExtras(bundle_);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickintent, PendingIntent.FLAG_CANCEL_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_item,pendingIntent);
            appWidgetManager.updateAppWidget(me,views);
        }
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

