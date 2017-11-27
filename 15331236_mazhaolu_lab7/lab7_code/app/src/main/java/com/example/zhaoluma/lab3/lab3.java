package com.example.zhaoluma.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class lab3 extends AppCompatActivity {
    private String initial_[] = {"E","A","D","K","W","M","F","M","L","B"};
    private String name_[] = {"Enchated Forest","Arla Milk","Devondale Milk","Kindle Oasis",
    "waitrose 早餐麦片","Mcvitiess 饼干","Ferrero Rocher","Maltesers","Lindt","Borggreve"};
    protected RecyclerView mRecyclerView;
    private List<Map<String, Object>> mDatas;
    private MyRecyclerAdapter mAdapter;
    private String random_name;
    private int random_image;
    private String random_price;
    private String random_birth;
    private String random_initial;
    int mAppWigetId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);
        mRecyclerView = (RecyclerView)findViewById(R.id.shoppinglist);
        initData();
        //ListView shoppingList = (ListView)findViewById(R.id.shoppinglist);
        /*SimpleAdapter adapter = new SimpleAdapter(this, getData(),R.layout.item_type,
                new String[]{"initial","name"},
                new int[]{R.id.initial, R.id.name});*/
        Random random=new Random();
        int random_int = random.nextInt(10);
        random_name = name_[random_int];
        if (random_name == "Enchated Forest") {
            random_image = R.mipmap.enchatedforest;
            random_price="￥ 5.00";
            random_birth="作者 Johanna Basford";
            random_initial="E";
        } else if (random_name == "Arla Milk") {
            random_image=R.mipmap.arla;
            random_price="￥ 59.00";
            random_birth="产地 德国";
            random_initial="A";
        } else if (random_name == "Devondale Milk") {
            random_image=R.mipmap.devondale;
            random_price="￥ 79.00";
            random_birth="产地 澳大利亚";
            random_initial="D";
        } else if (random_name == "Kindle Oasis") {
            random_image=R.mipmap.kindle;
            random_price="￥ 2399.00";
            random_birth="版本 8GB";
            random_initial="K";
        } else if (random_name == "waitrose 早餐麦片") {
            random_price="￥ 179.00";
            random_image=R.mipmap.waitrose;
            random_birth="重量 2Kg";
            random_initial="W";
        } else if (random_name.equals("Mcvitiess 饼干")) {
            random_image=R.mipmap.mcvitie;
            random_price="￥ 14.90";
            random_birth="产地 英国";
            random_initial="M";
        } else if (random_name == "Ferrero Rocher") {
            random_image=R.mipmap.ferrero;
            random_price="￥ 132.59";
            random_birth="重量 300g";
            random_initial="F";
        } else if (random_name == "Maltesers") {
            random_image=R.mipmap.maltesers;
            random_price="￥ 141.43";
            random_birth="重量 118g";
            random_initial="M";
        } else if (random_name == "Lindt") {
            random_image=R.mipmap.lindt;
            random_price="￥ 139.43";
            random_birth="重量 249g";
            random_initial="L";
        } else if (random_name == "Borggreve") {
            random_image=R.mipmap.borggreve;
            random_price="￥ 28.90";
            random_birth="重量 640g";
            random_initial="B";
        }
        Intent intentBroadcast = new Intent();
        intentBroadcast.setAction("MystaticFliter");
        Bundle bundlebroadcast = new Bundle();
        bundlebroadcast.putString("random_name",random_name);
        bundlebroadcast.putInt("random_image",random_image);
        bundlebroadcast.putString("random_price",random_price);
        bundlebroadcast.putString("random_birth",random_birth);
        bundlebroadcast.putString("random_initial",random_initial);
        intentBroadcast.putExtras(bundlebroadcast);
        sendBroadcast(intentBroadcast);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecyclerAdapter(lab3.this,R.layout.item_type,mDatas);
        mAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Map<String, Object> s = mDatas.get(position);
                Intent intent=new Intent(lab3.this,detials.class);
               if (s.get("name").equals("Enchated Forest")) {
                    //img.setImageResource(R.drawable.enchatedforest);
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Enchated Forest");
                    bundle.putString("price2","￥ 5.00");
                    bundle.putString("initial_1","E");
                    bundle.putString("birth","作者 Johanna Basford");
                    bundle.putInt("image",R.drawable.enchatedforest);
                    intent.putExtras(bundle);

                } else if (s.get("name") == "Arla Milk") {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Arla Milk");
                    bundle.putString("price2","￥ 59.00");
                   bundle.putString("initial_1","A");
                    bundle.putString("birth","产地 德国");
                    bundle.putInt("image",R.drawable.arla);
                    intent.putExtras(bundle);
                } else if (s.get("name") == "Devondale Milk") {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Devondale Milk");
                    bundle.putString("price2","￥ 79.00");
                   bundle.putString("initial_1","D");
                    bundle.putString("birth","产地 澳大利亚");
                    bundle.putInt("image",R.drawable.devondale);
                    intent.putExtras(bundle);
                } else if (s.get("name") == "Kindle Oasis") {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Kindle Oasis");
                    bundle.putString("price2","￥ 2399.00");
                   bundle.putString("initial_1","K");
                    bundle.putString("birth","版本 8GB");
                    bundle.putInt("image",R.drawable.kindle);
                    intent.putExtras(bundle);
                } else if (s.get("name") == "waitrose 早餐麦片") {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","waitrose 早餐麦片");
                    bundle.putString("price2","￥ 179.00");
                   bundle.putString("initial_1","W");
                    bundle.putString("birth","重量 2Kg");
                    bundle.putInt("image",R.drawable.waitrose);
                    intent.putExtras(bundle);
                } else if (s.get("name").equals("Mcvitiess 饼干")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Mcvitiess 饼干");
                    bundle.putString("price2","￥ 14.90");
                    bundle.putString("birth","产地 英国");
                   bundle.putString("initial_1","M");
                    bundle.putInt("image",R.drawable.mcvitie);
                    intent.putExtras(bundle);
                } else if (s.get("name") == "Ferrero Rocher") {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Ferrero Rocher");
                    bundle.putString("price2","￥ 132.59");
                   bundle.putString("initial_1","F");
                    bundle.putString("birth","重量 300g");
                    bundle.putInt("image",R.drawable.ferrero);
                    intent.putExtras(bundle);
                } else if (s.get("name") == "Maltesers") {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Maltesers");
                    bundle.putString("price2","￥ 141.43");
                    bundle.putString("birth","重量 118g");
                   bundle.putString("initial_1","M");
                    bundle.putInt("image",R.drawable.maltesers);
                    intent.putExtras(bundle);
                } else if (s.get("name") == "Lindt") {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Lindt");
                    bundle.putString("price2","￥ 139.43");
                    bundle.putString("birth","重量 249g");
                   bundle.putString("initial_1","L");
                    bundle.putInt("image",R.drawable.lindt);
                    intent.putExtras(bundle);
                } else if (s.get("name") == "Borggreve") {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Borggreve");
                    bundle.putString("price2","￥ 28.90");
                   bundle.putString("initial_1","B");
                    bundle.putString("birth","重量 640g");
                    bundle.putInt("image",R.drawable.borggreve);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });
        mAdapter.setOnItemLongClickListener(new MyRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                mDatas.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(lab3.this,"移除第"+position+"个商品",Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);


        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(mAdapter);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
        FloatingActionButton shoplistbutton = (FloatingActionButton)findViewById(R.id.FloatingActionButton);
        shoplistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(lab3.this,shopList.class);
                startActivity(intent);
            }
        });
    }
    private void initData() {
        mDatas = getData();
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("initial",initial_[i]);
            map.put("name",name_[i]);
            list.add(map);
        }
        return list;
    }

}
