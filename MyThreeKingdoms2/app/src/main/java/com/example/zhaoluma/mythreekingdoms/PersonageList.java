package com.example.zhaoluma.mythreekingdoms;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.zhaoluma.mythreekingdoms.R.id.avator;

/**
 * Created by zhaoluma on 2017/11/18.
 */

public class PersonageList extends AppCompatActivity {
    private int p; // 当前点击的item的位置
    private  List<Map<String, Object>> mDatas = new ArrayList<>();

    private String personname[] = {"阿贵","安阳公主","白虎文","白居易",
            "北宫玉","白起","卑弥呼","卑衍","邴原","波才"};
    private String gender[] = {"男 史实人物 生卒(?-212)",
            "女 史实人物 生卒(?-?) 籍贯，豫州沛国樵",
            "男 史实人物 生卒(?-?) 籍贯，凉州",
            "男 史实人物 字：乐天 生卒(772-846) 籍贯，司隶河南新郑",
            "男 史实人物 生卒(?-?)",
            "男 史实人物 生卒(?-公元前257) 籍贯，司隶扶风眉",
            "女 史实人物 生卒(158-248) 籍贯，倭国",
            "男 史实人物 生卒(?-238)",
            "男 史实人物 字：根矩 生卒(?-?) 籍贯，青州北海国朱虚",
            "男 史实人物 生卒(?-?)"};
    private String power[] = {"兴国氏王",
            "曹操之女",
            "凉州胡王",
            "香山居士，现代派诗人",
            "以中国西北部为据点的藏系游牧民羌族的首领",
            "军事家，秦国名将",
            "倭国女王",
            "公孙渊大将军",
            "曹操之五言将长史",
            "朱与贼波才战，战败"};
    private int avatorofperson[] = {R.drawable.agui,
            R.drawable.anyang,
            R.drawable.baihuwen,
            R.drawable.baijuyi,
            R.drawable.beigongyu,
            R.drawable.baiqi,
            R.drawable.beimihu,
            R.drawable.beiyan,
            R.drawable.bingyuan,
            R.drawable.bocai
    };

    private SimpleAdapter adapter;
    private ListView pernonagelist;
    private ImageButton search;

    private dbFunc mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personagelist);

        pernonagelist = (ListView)findViewById(R.id.list);
        mydb = new dbFunc(getApplicationContext());
        //getdata();  // 填充数据库

        adapter = new SimpleAdapter(this, mDatas, R.layout.personagelist_item,
                new String[] {"person_name", "person_detail", "power","avator"},
                new int[] {R.id.person_name,R.id.person_detail, R.id.power, avator});

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                                      @Override
                                      public boolean setViewValue(View view, Object data,
                                                                  String textRepresentation) {
                                          // TODO Auto-generated method stub
                                          if(view instanceof ImageView && data instanceof Bitmap){
                                              ImageView i = (ImageView)view;
                                              i.setImageBitmap((Bitmap) data);
                                              return true;
                                          }
                                          return false;
                                      }});
        putdb(); // 用数据库填充mData
        // 列表项点击 进入人物信息界面
        pernonagelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> m = mDatas.get(position);
                p = position;    // 锁定点击对象
                Person person = new Person(
                        (String)m.get("person_name"),
                        (String)m.get("person_detail"),
                        (String)m.get("power")
                );
                Intent intent = new Intent(PersonageList.this, CharacterInfo.class);
                intent.putExtra("CharacterInfo", person);

                // 将bitmap变成byte[] 再用intent传递 以避免未知bug
                byte [] bitmapByte = bitmapToByte((Bitmap)m.get("avator"));
                intent.putExtra("image", bitmapByte);
                //intent.putExtra("image", (Bitmap)m.get("avator"));      // serializable类不支持放置bitmap，故只能另外传
                startActivity(intent);
            }
        });

        // 长按删除
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        pernonagelist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> m = mDatas.get(position);
                p = position;
                String temp = m.get("person_name").toString();
                alertDialog.setTitle("移除人物").
                        setMessage("从列表移除"+temp+"?").setNegativeButton("取消",null).
                        setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String temp2 = mDatas.get(p).get("person_name").toString();
                                mydb.deletePerson(getApplicationContext(),temp2);
                                mDatas.remove(p);
                                adapter.notifyDataSetChanged();
                            }
                        }).create();
                alertDialog.show();
                return true;
            }
        });
        pernonagelist.setAdapter(adapter);

        // 搜索
        search = (ImageButton)findViewById(R.id.imageButton) ;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText = (EditText)findViewById(R.id.editText3);
                String search_name = searchText.getText().toString();
                boolean flag = false;
                for (int i = 0; i < mDatas.size(); i++) {
                    if (search_name.equals(mDatas.get(i).get("person_name"))) {
                        // 搜索到，跳转到对应详情页
                        Map<String, Object> m = mDatas.get(i);
                        Person person = new Person(
                                (String)m.get("person_name"),
                                (String)m.get("person_detail"),
                                (String)m.get("power")
                        );
                        Intent intent = new Intent(PersonageList.this, CharacterInfo.class);
                        intent.putExtra("CharacterInfo", person);
                        // 将bitmap变成byte[] 再用intent传递 以避免未知bug
                        byte [] bitmapByte = bitmapToByte((Bitmap)m.get("avator"));
                        intent.putExtra("image", bitmapByte);
                        startActivity(intent);
                        flag = true;
                        searchText.setText("");     //  清空搜索框
                        break;
                    }
                }
                if (!flag)
                    Toast.makeText(PersonageList.this,"没有此人的信息", Toast.LENGTH_SHORT).show();
            }
        });

        EventBus.getDefault().register(this);       //EventBus注册
        setAddClicked();        //注册添加按钮监听器
    }

    // bitmap转byte[]
    private byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] bitmapByte =baos.toByteArray();
        return bitmapByte;
    }

    //添加按钮点击事件
    private void setAddClicked() {
        FloatingActionButton btn = (FloatingActionButton)findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       // 点击按钮跳转到 编辑页
                Intent intent = new Intent(PersonageList.this, Add_and_revise.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });
    }


    private void getdata() {
       // List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            /*Map<String, Object> map = new HashMap<String, Object>();
            map.put("person_name",personname[i]);
            map.put("person_detail",gender[i]);
            map.put("power",power[i]);*/

            /*  Note: 这里mDatas由存储Drawable的id改为存储Bitmap
            //  因为之后从相册中选择的图片，无法获取到图片id
            //  故各个ImageView中的res全部使用Bitmap
            //  在各个Activity中传递的也使用Bitmap，而不用Drawable的id
             */
            Drawable drawable = ContextCompat.getDrawable(this, avatorofperson[i]);
            BitmapDrawable bd = (BitmapDrawable)drawable;
            Bitmap bm = bd.getBitmap();
           // map.put("avator",bm);       //  这里将bitmap存入mDatas
            //list.add(map);
            Person per = new Person(bm, personname[i],gender[i],power[i]);
            mydb.addPerson(this,per);  // 初始化数据库
        }
        //return list;
    }

    // 处理由EventBus传递回来的信息  主要是增删改操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int type = event.getTypes();
        Person person = event.getPerson();
        Bitmap bitmap = event.getBitmap();
        Map<String, Object> map = new HashMap<>();
        map.put("person_name",person.getName());
        map.put("person_detail",person.getDetail());
        map.put("power",person.getPower());
        map.put("avator",bitmap);
        boolean flag = false;
        switch (type) {
            case 0:     //  删除
                mDatas.remove(p);   // 删除的Item位置在进入Info界面时已锁定  可能会产生Bug  Mark
                adapter.notifyDataSetChanged();
                mydb.deletePerson(getApplicationContext(),person.getName().toString());
                break;
            case 1:     //  修改  修改位置依然是p   或许会有问题
                mydb.deletePerson(getApplicationContext(),person.getName().toString());
                mDatas.set(p, map);
                mydb.addPerson(getApplicationContext(),person);
                adapter.notifyDataSetChanged();
                break;
            case 2:     //  增加
                for (int i = 0; i < mDatas.size(); i++) {
                    if (person.getName().compareTo((String)mDatas.get(i).get("person_name")) < 0) {
                        //字典序
                        mDatas.add(i, map);
                        mydb.addPerson(getApplicationContext(),person);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {mDatas.add(map);
                mydb.addPerson(getApplicationContext(),person);}
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
    private void putdb() {
        ArrayList<Person> pl = mydb.getAllCharacters(getApplicationContext());
        for (int i = 0; i < pl.size(); i++) {
            Person per = pl.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("person_name",per.getName());
            map.put("person_detail",per.getDetail());
            map.put("power",per.getPower());
            map.put("avator",per.getAvator());
            mDatas.add(map);
            adapter.notifyDataSetChanged();
        }
    }

   /* private List<Map<String, Object>> getdata() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("person_name",personname[i]);
            map.put("person_detail",gender[i]);
            map.put("power",power[i]);*/

            /*  Note: 这里mDatas由存储Drawable的id改为存储Bitmap
            //  因为之后从相册中选择的图片，无法获取到图片id
            //  故各个ImageView中的res全部使用Bitmap
            //  在各个Activity中传递的也使用Bitmap，而不用Drawable的id
             */
            /*Drawable drawable = ContextCompat.getDrawable(this, avatorofperson[i]);
            BitmapDrawable bd = (BitmapDrawable)drawable;
            Bitmap bm = bd.getBitmap();
            map.put("avator",bm);       //  这里将bitmap存入mDatas
            list.add(map);
        }
        return list;
    }*/
}
