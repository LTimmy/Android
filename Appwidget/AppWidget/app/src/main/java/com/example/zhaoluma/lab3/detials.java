package com.example.zhaoluma.lab3;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoluma on 2017/10/21.
 */

public class detials extends AppCompatActivity {
   private int tag;
    private TextView tv1;
    private TextView tv2;
    private  TextView tv3;
   private ImageView img;
    private   String s1;
    private   String s2;
    private  String s3;
    private int shop_image;
    private dynamicReceiver  dreceiver;
    public static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detialsxml);

        ImageButton back=(ImageButton)findViewById(R.id.back);

        ImageButton imgb=(ImageButton)findViewById(R.id.shop2);

        imgb.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               Intent intent = new Intent();
               intent.setAction("DYNAMICATION");
               Bundle bundle_ = new Bundle();
               bundle_.putString("shop_name",s1);
               bundle_.putInt("shop_image",shop_image);
               intent.putExtras(bundle_);
               sendBroadcast(intent);
               EventBus.getDefault().postSticky(new MessageEvent(s3,s1,s2));
            }
       });
        Bundle bundle=this.getIntent().getExtras();
        tv1=(TextView)findViewById(R.id.name2) ;
        tv1.setText(bundle.getString("name2"));
        tv2=(TextView)findViewById(R.id.price2) ;
        tv2.setText(bundle.getString("price2"));
        tv3=(TextView)findViewById(R.id.birth) ;
        tv3.setText(bundle.getString("birth"));
        img=(ImageView)findViewById(R.id.image_);
        img.setImageResource(bundle.getInt("image"));
        s1= tv1.getText().toString();
        s2=tv2.getText().toString();
        s3=bundle.getString("initial_1");
        shop_image = bundle.getInt("image");
        final ImageView star=(ImageView)findViewById(R.id.star);
       star.setTag(0);
        tag = (int)star.getTag();
        star.setOnClickListener(new View.OnClickListener(){

            @Override
           public void onClick(View v) {
                tag = (int)star.getTag();
                switch (tag) {
                    case 0:
                        star.setTag(1);
                        star.setImageResource(R.drawable.full_star);
                        break;
                    case 1:
                        star.setTag(0);
                        star.setImageResource(R.drawable.empty_star);
                        break;
                }
            }
        });
       back.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // 实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction("DYNAMICATION");
        dreceiver = new dynamicReceiver();
        // 注册广播接收
        registerReceiver(dreceiver,filter);
    }
    @Override
    protected void onStop() {

        unregisterReceiver(dreceiver);
        super.onStop();
    }
    /*@Override
    public void onStop() {
        unregisterReceiver(dreceiver);
        super.onStop();
    }*/
}
