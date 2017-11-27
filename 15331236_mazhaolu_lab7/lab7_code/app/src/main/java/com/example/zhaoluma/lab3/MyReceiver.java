package com.example.zhaoluma.lab3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    private String random_name;
    private String random_price;
    private int random_image;
    private String random_birth;
    private String random_initial;
    public MyReceiver(){}
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       /* Bundle bundle=intent.getExtras();
        random_name = bundle.getString("random_name");
        random_price = bundle.getString("random_price");
        random_image = bundle.getInt("random_image");
        random_birth = bundle.getString("random_birth");
        random_initial = bundle.getString("random_initial");
        ComponentName me = new ComponentName(context,Widget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals("MystaticFliter")) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setTextViewText(R.id.appwidget_text, random_name+"仅售"+random_price+"!");
            views.setImageViewResource(R.id.appwidget_imageview,random_image);
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
        }*/
    }




}
