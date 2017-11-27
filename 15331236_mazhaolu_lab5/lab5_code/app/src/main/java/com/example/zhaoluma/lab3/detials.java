package com.example.zhaoluma.lab3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
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
    public static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detialsxml);
        ImageButton back=(ImageButton)findViewById(R.id.back);
        Bundle bundle=this.getIntent().getExtras();
        tv1=(TextView)findViewById(R.id.name2) ;
        tv1.setText(bundle.getString("name2"));
        tv2=(TextView)findViewById(R.id.price2) ;
        tv2.setText(bundle.getString("price2"));
        tv3=(TextView)findViewById(R.id.birth) ;
        tv3.setText(bundle.getString("birth"));
        img=(ImageView)findViewById(R.id.image_);
        img.setImageResource(bundle.getInt("image"));
        ImageButton imgb=(ImageButton)findViewById(R.id.shop2);
        s1= tv1.getText().toString();
        s2=tv2.getText().toString();
        s3=bundle.getString("initial_1");

        imgb.setOnClickListener(new View.OnClickListener(){

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
           @Override
           public void onClick(View v) {
               Map<String, Object> map = new HashMap<String, Object>();
               map.put("initial_1",s3);
               map.put("name_1",s1);
               map.put("price",s2);
               list.add(map);
               shopList.mDatas_ = list;
                Toast.makeText(detials.this,"商品已加入购物车",Toast.LENGTH_SHORT).show();
               //Toast.makeText(detials.this,shopList.shop.s(),Toast.LENGTH_SHORT).show();
            }
       });
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
}
