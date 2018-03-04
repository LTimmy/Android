package com.example.zhaoluma.birthday_remind;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Birthday_remind extends AppCompatActivity {

    private String name[] = {"姓名","李白","杜甫","白居易","王羲之","杨贵妃"};
    private String birthday[] = {"生日","01/01","02/02","03/03","04/04","05/05"};
    private String gift[] = {"礼物","✿","✿✿","✿✿✿","✿✿✿✿","✿✿✿✿✿"};

    private Button addbutton;
    private ListView birthdaylist;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> mDatas = new ArrayList<>();  // 用于存储信息更新listview
    private myDB mydb;
    private int p; // 当前点击的item的位置

    private View dialogview = null;
    LayoutInflater layoutInflater;
    TextView dialogname;
    EditText dialogbirthday;
    EditText dialoggift;
    TextView phonetext;
    TextView dialogphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_remind);

        verifyContactsPermission(this);

        birthdaylist = (ListView)findViewById(R.id.birthdaylist);
        mydb = new myDB(getApplicationContext());
        //putindb();

        adapter = new SimpleAdapter(this, mDatas, R.layout.item,
                new String[] {"name", "birthday", "gift"},
                new int[] {R.id.name, R.id.birthday, R.id.gift}
                );
        getdata();

        // 长按删除
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        birthdaylist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                p = position;
                if (!mDatas.get(p).get("name").toString().equals("姓名")) {
                    alertDialog.setTitle("是否删除？").setNegativeButton("否",null).
                            setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String dm = mDatas.get(p).get("name").toString();
                                    mydb.delete(dm);
                                    mDatas.remove(p);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(Birthday_remind.this, "成功删除", Toast.LENGTH_SHORT).show();
                                }
                            }).create();
                    alertDialog.show();
                }
                return true;
            }
        });

        // 点击
        /*layoutInflater = LayoutInflater.from(this);
        dialogview = layoutInflater.inflate(R.layout.dialogview,null);
        dialogname = (TextView)dialogview.findViewById(R.id.dialogname);
        dialogbirthday = (EditText)dialogview.findViewById(R.id.dialogbirthday);
        dialoggift = (EditText)dialogview.findViewById(R.id.dialoggift);*/

        birthdaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                p = position;    // 锁定点击对象
                if (!mDatas.get(p).get("name").toString().equals("姓名")) {
                    layoutInflater = LayoutInflater.from(Birthday_remind.this);
                    dialogview = layoutInflater.inflate(R.layout.dialogview,null);
                    dialogname = (TextView)dialogview.findViewById(R.id.dialogname);
                    dialogbirthday = (EditText)dialogview.findViewById(R.id.dialogbirthday);
                    dialoggift = (EditText)dialogview.findViewById(R.id.dialoggift);
                    phonetext = (TextView)dialogview.findViewById(R.id.phonetext);
                    dialogphone = (TextView)dialogview.findViewById(R.id.dialogphone);
                    dialogname.setText(mDatas.get(p).get("name").toString());
                    dialogbirthday.setText(mDatas.get(p).get("birthday").toString());
                    dialoggift.setText(mDatas.get(p).get("gift").toString());
                    if (number(mDatas.get(p).get("name").toString()) != "") {
                        dialogphone.setText(number(mDatas.get(p).get("name").toString()));
                    } else {
                        dialogphone.setText("无");
                    }

                    AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(Birthday_remind.this);
                    alertDialog1.setTitle("(๑•̀ㅂ•́)و✧ 恭喜发财").setView(dialogview)
                            .setNegativeButton("放弃修改",null)
                            .setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mydb.update(mDatas.get(p).get("name").toString(),
                                            dialogbirthday.getText().toString(),
                                            dialoggift.getText().toString());
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("name", mDatas.get(p).get("name").toString());
                                    map.put("birthday", dialogbirthday.getText().toString());
                                    map.put("gift", dialoggift.getText().toString());
                                    mDatas.set(p,map);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(Birthday_remind.this, "成功修改", Toast.LENGTH_SHORT).show();
                                }
                            }).create();
                    alertDialog1.show();
                }
            }
        });

        birthdaylist.setAdapter(adapter);

        addbutton = (Button)findViewById(R.id.add);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Birthday_remind.this, Add_item.class);
                startActivity(intent);
            }
        });
    }
    // 每次oncreate时根据数据库的变化更新listview
    private void getdata() {
        ArrayList<member> al = mydb.getAllmember();
        for (int i = 0; i < al.size(); i++) {
            member m = al.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("name", m.getName());
            map.put("birthday", m.getBirthday());
            map.put("gift", m.getGift());
            mDatas.add(map);
            adapter.notifyDataSetChanged();
        }

    }

    // 初始化数据库，因为实现了重复不能insert，所以每次oncreate时并不会重复添加
    private void putindb() {
        for (int i = 0; i < 6; i++) {
            member m = new member(name[i], birthday[i], gift[i]);
            mydb.insert(m);
        }
    }

    // 根据姓名查找通讯录内电话号码
    public String number(String n) {
        // 使用ContentResolver查找联系人数据
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        // 遍历查询结果，找到所需号码
        while (cursor.moveToNext()) {
            // 获取联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            // 获取联系人的名字
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            if (n.equals(contactName)) {
                 //使用ContentResolver查找联系人的电话号码
                Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds
                .Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                + contactId, null, null);
                if (phone.moveToNext()) {
                    String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract
                    .CommonDataKinds.Phone.NUMBER));
                    return phoneNumber;
                }
            }
        }
        return "";
    }
    public static void verifyContactsPermission(Activity activity) {
        try {
            int hascontactPermission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_CONTACTS");
            if (hascontactPermission != PackageManager.PERMISSION_GRANTED) {
                // 没有权限，去权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, new String[] {"android.permission.READ_CONTACTS"},
                        1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
