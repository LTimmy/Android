package com.example.zhaoluma.mythreekingdoms;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class ThreeKingdoms extends AppCompatActivity {

    private Button button;
    private MediaPlayer player = new MediaPlayer();
    private Button button2;
    private EditText editText;
    private EditText editText2;
    private String name = "Name";
    private String password = "key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_kingdoms);

        //背景音乐播放
        verifyStoragePermissions(this);
        try{
            //player.setDataSource()
            player.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Music/melt.mp3");
            player.prepare();
            player.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        editText = (EditText)findViewById(R.id.editText);
        editText2 = (EditText)findViewById(R.id.editText2);
        // 登录按钮，初始用户名为"android"，密码为"ThreeKingdoms"
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() == 0 || editText2.getText().length()==0) {
                    Toast.makeText(ThreeKingdoms.this,"用户名或密码为空",Toast.LENGTH_SHORT).show();
                } else if (!editText.getText().toString().equals(name) ||
                        !editText2.getText().toString().equals(password)) {
                    Toast.makeText(ThreeKingdoms.this,"用户名或密码不正确",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ThreeKingdoms.this, PersonageList.class);
                    startActivity(intent);
                }
            }
        });
        // 更改用户名按钮
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() == 0 || editText2.getText().length()==0) {
                    Toast.makeText(ThreeKingdoms.this,"请输入用户名或密码",Toast.LENGTH_SHORT).show();
                } else {
                    name = editText.getText().toString();
                    password = editText2.getText().toString();
                    Toast.makeText(ThreeKingdoms.this,"您已成功注册，请重新登录",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    public void onDestroy() {
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
        super.onDestroy();
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
