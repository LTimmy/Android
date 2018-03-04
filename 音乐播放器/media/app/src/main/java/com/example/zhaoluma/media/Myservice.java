package com.example.zhaoluma.media;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import java.io.IOException;

/**
 * Created by zhaoluma on 2017/11/16.
 */

public class Myservice extends Service {

    public MediaPlayer player = new MediaPlayer();
    public final IBinder binder = new MyBinder();
     /*
    * 绑定服务的实现流程：
    * 1.服务 onCreate， onBind， onDestroy 方法
    * 2.onBind 方法需要返回一个 IBinder 对象
    * 3.如果 Activity 绑定，Activity 就可以取到 IBinder 对象，可以直接调用对象的方法
    */

    // 相同应用内部不同组件绑定，可以使用内部类以及Binder对象来返回。
    public class MyBinder extends Binder {//定义一个类实现IBinder接口（ Binder实现了IBinder接口）
        Myservice getService() {
            return Myservice.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) { //在onBind(Intent intent)中返回IBinder对象
        return binder;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        try{
            //player.setDataSource()
            player.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/melt.mp3");
            player.prepare();
            player.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 任意一次unbindService()方法，都会触发这个方法
     * 用于释放一些绑定时使用的资源
     * @param intent
     * @return
     */
    @Override
    public  boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
        super.onDestroy();
    }

    public void play() {

        player.start(); // 开启音乐并启动动画

    }
    public void paused() {

        player.pause(); // 暂停音乐并停止动画

    }
    public void stop() {   // 停止音乐
        if (player != null) {
            player.stop();

            try {
                player.prepare();
                player.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
