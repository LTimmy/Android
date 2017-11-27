package com.example.zhaoluma.media;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Media extends AppCompatActivity {
    // Button
    private Button play, stop, quit;
    // 服务端
    /*在服务中定义一个IBinder的实例, 在Activity中定义一个ServiceConnection类的实例，
    在其onServiceConnected(ComponentName name, IBinder service)方法中获取Service中
    返回IBinder对象，利用该对象即可调用Service中的方法进行相互通信。
     */
    private Myservice myservice;

    // 进度条
    private SeekBar seekbar;
    // 状态和时间
    private TextView state, time;
    // 图片
    private ImageView image;


    public ObjectAnimator objectAnimator = new ObjectAnimator(); // 动画


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        verifyStoragePermissions(this);
        play = (Button)findViewById(R.id.button1);
        stop = (Button)findViewById(R.id.button2);
        quit = (Button)findViewById(R.id.button3);
        state = (TextView)findViewById(R.id.state);
        time = (TextView) findViewById(R.id.time);
        seekbar = (SeekBar)findViewById(R.id.bar);
        image = (ImageView)findViewById(R.id.image);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Media.this,"test",Toast.LENGTH_SHORT).show();
                if (play.getText().toString().equals("PLAY")) {
                    myservice.play();
                    play.setText("PAUSED");
                    state.setText("Playing");
                    if (!objectAnimator.isRunning()) {
                        objectAnimator.start();
                    } else {
                        objectAnimator.resume();
                    }
                } else if (play.getText().toString().equals("PAUSED")) {
                    myservice.paused();
                    play.setText("PLAY");
                    state.setText("Paused");
                    if (objectAnimator.isRunning()) {
                        objectAnimator.pause();
                    }
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setText("PLAY");
                state.setText("Stopped");
                myservice.stop();
                objectAnimator.end();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                unbindService(connection);
                try {
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

       myservice = new Myservice();

        // 连接绑定服务端
        Intent intent = new Intent(this, Myservice.class);
        startService(intent);
        /*第二参数是一个ServiceConnection对象，该对象用于监听访问者与service之间的连接情况，
        当访问者与Service连接成功时会回调该类的onServiceConnected(ComponentName name, IBinder service)方法，
        然后将服务中创建的Ibinder对象（此时在Service的onBinder方法中需要返回该Ibinder对象）传递给第一个参数intent，
        通过该Ibinder对象就能与Service进行通信。第三个参数flags指定绑定时是否自动创建Service，
        一般我们指定为BIND_AUTO_CREATE（自动创建，如果传入0表示不自动创建）*/
        bindService(intent, connection, BIND_AUTO_CREATE );

    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //Toast.makeText(Media.this,"test",Toast.LENGTH_SHORT).show();
            myservice = ((Myservice.MyBinder)service).getService();
            rotate();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            myservice = null;
        }
    };


   public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(myservice.player.isPlaying()) {
                state.setText("Playing");
                play.setText("PAUSED");
            } else {

                //state.setText("Paused");
                play.setText("PLAY");
            }
            time.setText(getPlayingTime(myservice.player.getCurrentPosition()));
            seekbar.setProgress(myservice.player.getCurrentPosition());
            seekbar.setMax(myservice.player.getDuration());
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    time.setText(getPlayingTime(progress));
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // 寻找指定的时间位置
                    myservice.player.seekTo(seekBar.getProgress());
                }
            });
            handler.postDelayed(runnable, 100);
        }
    };

   @Override
    protected void onResume() {
       //Toast.makeText(Media.this,"test",Toast.LENGTH_SHORT).show();
        if (myservice.player.isPlaying()) {
            state.setText("Playing");
            if (!objectAnimator.isRunning()) {
                objectAnimator.start();
            }
        }
        seekbar.setProgress(myservice.player.getCurrentPosition());
        seekbar.setMax(myservice.player.getDuration());
        handler.post(runnable);
        super.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop(){
        super.onStop();
    }
    @Override
    protected  void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }

    //播放时间变化
    private String getPlayingTime(int progress) {
        int second = progress/1000;
        int min = second / 60;
        second %= 60;
        if (second < 10)
            return "0"+min+" : 0"+ second;
        return"0"+min+" : "+ second;
    }

    public void  rotate() {
        objectAnimator = ObjectAnimator.ofFloat(image, "rotation", 0f, 360f);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(-1);
    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            };


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permissionREAD_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
