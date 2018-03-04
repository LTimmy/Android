package com.example.zhaoluma.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

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
        Bundle bundle=intent.getExtras();
        random_name = bundle.getString("random_name");
        random_price = bundle.getString("random_price");
       random_image = bundle.getInt("random_image");
        random_birth = bundle.getString("random_birth");
        random_initial = bundle.getString("random_initial");
        Intent clickintent = new Intent(context,detials.class);
        Bundle bundle_ = new Bundle();
        bundle_.putString("initial_1",random_initial);
        bundle_.putString("name2",random_name);
        bundle_.putString("birth",random_birth);
        bundle_.putString("price2",random_price);
        bundle_.putInt("image",random_image);
        clickintent.putExtras(bundle_);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickintent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("新商品热卖").
                setLargeIcon(BitmapFactory.decodeResource(context.getResources(),random_image)).
                setSmallIcon(random_image).setContentText(random_name+"仅售"+random_price+"!").
                setContentIntent(pendingIntent).setAutoCancel(true);
        Notification notify = builder.build();
        notifyManager.notify(0,notify);

        //Toast t = Toast.makeText(context,"静态广播："+random_name+"仅售"+random_price, Toast.LENGTH_SHORT);
        //t.show();
       // throw new UnsupportedOperationException("Not yet implemented");
    }




}
