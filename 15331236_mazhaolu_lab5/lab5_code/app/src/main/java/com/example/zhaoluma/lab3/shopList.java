package com.example.zhaoluma.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoluma on 2017/10/21.
 */

public class shopList extends AppCompatActivity {
    public  static List<Map<String, Object>> mDatas_;
    private int pp;
    public  static SimpleAdapter adapter;
    //public static String s = "123";
    public ListView shoppList_;
    public static shopList shop = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoplist);
        shop = this;
        this.shoppList_= (ListView)findViewById(R.id.list);
        mDatas_=detials.list;
        this.adapter = new SimpleAdapter(this, mDatas_,R.layout.shoplist_item,
                new String[]{"initial_1","name_1", "price"},
                new int[]{R.id.initial_1, R.id.name_1,R.id.price});
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        shoppList_.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> s = mDatas_.get(position);
                pp = position;
                String temp = s.get("name_1").toString();
                alertDialog.setTitle("移除商品").
                        setMessage("从购物车移除"+temp+"?").setNegativeButton("取消",null).
                        setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatas_.remove(pp);
                                adapter.notifyDataSetChanged();
                            }
                        }).create();
                alertDialog.show();
                return true;
            }
        });
        shoppList_.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> s = mDatas_.get(position);
                String temp = s.get("name_1").toString();
               Intent intent=new Intent(shopList.this,detials.class);
                if (temp.equals("Enchated Forest")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Enchated Forest");
                    bundle.putString("price2","￥ 5.00");
                    bundle.putString("initial_1","E");
                    bundle.putString("birth","作者 Johanna Basford");
                    bundle.putInt("image",R.drawable.enchatedforest);
                    intent.putExtras(bundle);
               }  else if (temp.equals("Arla Milk")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Arla Milk");
                    bundle.putString("price2","￥ 59.00");
                    bundle.putString("initial_1","A");
                    bundle.putString("birth","产地 德国");
                    bundle.putInt("image",R.drawable.arla);
                    intent.putExtras(bundle);
                } else if (temp.equals("Devondale Milk")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Devondale Milk");
                    bundle.putString("price2","￥ 79.00");
                    bundle.putString("initial_1","D");
                    bundle.putString("birth","产地 澳大利亚");
                    bundle.putInt("image",R.drawable.devondale);
                    intent.putExtras(bundle);
                } else if (temp.equals("Kindle Oasis")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Kindle Oasis");
                    bundle.putString("price2","￥ 2399.00");
                    bundle.putString("initial_1","K");
                    bundle.putString("birth","版本 8GB");
                    bundle.putInt("image",R.drawable.kindle);
                    intent.putExtras(bundle);
                } else if (temp.equals("waitrose 早餐麦片")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","waitrose 早餐麦片");
                    bundle.putString("price2","￥ 179.00");
                    bundle.putString("initial_1","W");
                    bundle.putString("birth","重量 2Kg");
                    bundle.putInt("image",R.drawable.waitrose);
                    intent.putExtras(bundle);
                } else if (temp.equals("Mcvities's 饼干")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Mcvities's 饼干");
                    bundle.putString("price2","￥ 14.90");
                    bundle.putString("birth","产地 英国");
                    bundle.putString("initial_1","M");
                    bundle.putInt("image",R.drawable.mcvitie);
                    intent.putExtras(bundle);
                } else if (temp.equals("Ferrero Rocher")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Ferrero Rocher");
                    bundle.putString("price2","￥ 132.59");
                    bundle.putString("initial_1","F");
                    bundle.putString("birth","重量 300g");
                    bundle.putInt("image",R.drawable.ferrero);
                    intent.putExtras(bundle);
                } else if (temp.equals("Maltesers")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Maltesers");
                    bundle.putString("price2","￥ 141.43");
                    bundle.putString("birth","重量 118g");
                    bundle.putString("initial_1","M");
                    bundle.putInt("image",R.drawable.maltesers);
                    intent.putExtras(bundle);
                } else if (temp.equals("Lindt")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name2","Lindt");
                    bundle.putString("price2","￥ 139.43");
                    bundle.putString("birth","重量 249g");
                    bundle.putString("initial_1","L");
                    bundle.putInt("image",R.drawable.lindt);
                    intent.putExtras(bundle);
                } else if (temp.equals("Borggreve")) {
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

        this.shoppList_.setAdapter(adapter);
        FloatingActionButton shoplistbutton = (FloatingActionButton)findViewById(R.id.FloatingActionButton_1);
        shoplistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(shopList.this,lab3.class);
                startActivity(intent);
            }
        });

    }
}
