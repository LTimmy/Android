package com.example.zhaoluma.mythreekingdoms;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/11/21.
 */
//  处理人物信息页面的Class

public class CharacterInfo extends AppCompatActivity {
    private String name;
    private String detail;
    private String power;
    private Bitmap avator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_detail);
        setInfo();
        setReviseClicked();
        setDeleteClicked();
        setCollectClicked();
        setBackClicked();
    }

    //  返回
    private void setBackClicked() {
        TextView text = (TextView)findViewById(R.id.text_quit);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //  收藏按钮点击事件
    private void setCollectClicked() {
        Button btn = (Button)findViewById(R.id.btn_collect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "功能尚未开发",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //  删除按钮点击事件
    private void setDeleteClicked() {
        Button btn = (Button)findViewById(R.id.btn_delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //点击删除时弹出提示框
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CharacterInfo.this);
                alertDialog.setTitle("删除人物")
                        .setMessage("是否要从词典中删除条目【" + name + "】?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            //  删除当前人物, 跳转回列表页
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Person person = new Person(avator,name, detail, power);
                                //Person person = new Person(name, detail, power);
                                //参数 "0" 表示当前是删除操作
                                EventBus.getDefault().post(new MessageEvent(0, person, avator));
                                //跳转回列表页
                                Intent intent = new Intent(CharacterInfo.this, PersonageList.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "删除成功",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    //  修改按钮点击事件
    private void setReviseClicked() {
        Button btn = (Button)findViewById(R.id.btn_revise);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person(name, detail, power);
                //Person person = new Person(name, detail, power);
                Intent intent = new Intent(CharacterInfo.this, Add_and_revise.class);
                intent.putExtra("action", "revise");
                intent.putExtra("revise", person);

                // 将bitmap变成byte[] 再用intent传递 以避免未知bug
                byte [] bitmapByte = bitmapToByte(avator);
                intent.putExtra("image", bitmapByte);
                //intent.putExtra("image", avator);
                startActivity(intent);
            }
        });
    }

    // bitmap转byte[]
    private byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] bitmapByte =baos.toByteArray();
        return bitmapByte;
    }

    // 获取Intent, 填充页面信息
    private void setInfo() {
        Intent intent = getIntent();
        Person person = (Person) intent.getSerializableExtra("CharacterInfo");
        name = person.getName();
        detail = person.getDetail();
        power = person.getPower();

        byte[] bitmapByte = intent.getByteArrayExtra("image");
        Bitmap bitmap= BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);
        avator = bitmap;

        TextView text1 = (TextView)findViewById(R.id.ch_name);
        text1.setText(name);
        TextView text2 = (TextView)findViewById(R.id.ch_gender);
        text2.setText(detail);
        TextView text3 = (TextView)findViewById(R.id.ch_power);
        text3.setText(power);
        ImageView image = (ImageView)findViewById(R.id.ch_image);
        image.setImageBitmap(avator);
    }
}
