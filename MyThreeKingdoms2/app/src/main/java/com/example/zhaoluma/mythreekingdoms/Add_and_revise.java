package com.example.zhaoluma.mythreekingdoms;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/11/21.
 */

// 编辑页事件处理类 包括添加和修改
public class Add_and_revise extends AppCompatActivity {
    private String name;
    private String detail;
    private String power;
    private Bitmap avator;
    private boolean types;    //  当前页面类型标记 T--新增页 F--修改页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_revise);
        setInfo();
        setCancelClicked();     //设置取消事件
        setSaveClicked();       //设置保存事件
        setChooseImageClicked();    //选择图片
    }

    // 上传图片btn设置监听器
    private void setChooseImageClicked() {
        Button btn = (Button)findViewById(R.id.btn_image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 权限申请
                PremissionHandle();

                // 创建图片存储位置
                File imageFile = new File(Environment
                        .getExternalStorageDirectory(), "outputImage.jpg");
                if (imageFile.exists()) {
                    imageFile.delete();
                }
                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
                //转换成Uri
                Uri imageUri = Uri.fromFile(imageFile);
                //开启选择界面
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                //设置可以缩放
                intent.putExtra("scale", true);
                //设置可以裁剪
                intent.putExtra("crop", true);
                intent.setType("image/*");
                //设置输出位置
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                //开始选择  进入相册
                startActivityForResult(intent, 2);
            }
        });
    }

    // 动态权限申请
    private void PremissionHandle() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    //  选择相片的回调函数处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 2:
                if(resultCode==RESULT_OK){
                   handleImage(data);
                }
                break;
            default:
                break;
        }
    }

    // 处理从相册选择的图片
    private void handleImage(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri
                    .getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri
                    .getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果不是document类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        }
        displayImage(imagePath); // 根据图片路径显示图片
        System.err.println(imagePath);
    }

    //  获取图片真实路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    // 将Bitmap显示在ImageView上
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ImageView mImage = (ImageView)findViewById(R.id.choose_image);
            mImage.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    // 保存键事件处理
    private void setSaveClicked() {
        TextView text = (TextView)findViewById(R.id.text_save);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 规范性检查
                EditText inputName = (EditText)findViewById(R.id.input_name);
                name = inputName.getText().toString();
                EditText inputInfo = (EditText)findViewById(R.id.input_info);
                detail = inputInfo.getText().toString();
                EditText inputHistory = (EditText)findViewById(R.id.input_history);
                power = inputHistory.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(getApplicationContext(), "姓名栏不能为空",
                            Toast.LENGTH_SHORT).show();
                }
                /* else if (detail.equals("")) {
                    Toast.makeText(getApplicationContext(), "简介栏不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (power.equals("")) {
                    Toast.makeText(getApplicationContext(), "生平栏不能为空",
                            Toast.LENGTH_SHORT).show();
                }*/
                else {      // EventBus传回数据， 跳转
                    ImageView mImage = (ImageView)findViewById(R.id.choose_image);
                    Bitmap bm =((BitmapDrawable) mImage.getDrawable()).getBitmap();
                    //Bitmap bm = mImage.getDrawingCache();

                    // 压缩图片：  通过Intent传递Bitmap必须小于40kb，否则报错
                    //bm = compressImage(bm);

                    Person person = new Person(avator,name, detail, power);
                    //Person person = new Person(name, detail, power);
                    if (types) {    //当前页面为 新增页
                        EventBus.getDefault().post(new MessageEvent(2, person, bm));
                        Toast.makeText(getApplicationContext(), "添加成功",
                                Toast.LENGTH_SHORT).show();
                    } else {       //当前页面为 修改页
                        EventBus.getDefault().post(new MessageEvent(1, person, bm));
                        Toast.makeText(getApplicationContext(), "修改成功",
                                Toast.LENGTH_SHORT).show();
                    }
                    //跳转回列表页
                    Intent intent = new Intent(Add_and_revise.this, PersonageList.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // 取消键事件处理
    private void setCancelClicked() {
        TextView text = (TextView)findViewById(R.id.text_cancel);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消时弹出对话框提示
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Add_and_revise.this);
                alertDialog.setTitle("放弃更改")
                        .setMessage("是否要放弃此次更改？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            //  放弃更改，返回
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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

    //接收Intent信息
    private void setInfo() {
        TextView title = (TextView)findViewById(R.id.text_title);   // 页面标题
        ImageView image = (ImageView)findViewById(R.id.choose_image);   //头像
        EditText inputName = (EditText)findViewById(R.id.input_name);
        EditText inputInfo = (EditText)findViewById(R.id.input_info);
        EditText inputHistory = (EditText)findViewById(R.id.input_history);

        Intent intent = getIntent();
        String action = intent.getStringExtra("action");

        if (action.equals("add")) {     //当前页面为新增页, 页面清空
            types = true;
            title.setText("添加人物");

            name = "";
            detail = "";
            power = "";

            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.image_default);
            BitmapDrawable bd = (BitmapDrawable)drawable;
            Bitmap bm = bd.getBitmap();
            avator = bm;

        } else if (action.equals("revise")) {            // 当前页面为修改页，设置默认信息
            types = false;
            title.setText("修改信息");
            Person person = (Person) intent.getSerializableExtra("revise");
            name = person.getName();
            detail = person.getDetail();
            power = person.getPower();

            byte[] bitmapByte = intent.getByteArrayExtra("image");
            Bitmap bitmap= BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);
            avator = bitmap;
            //avator = intent.getParcelableExtra("image");
        }
        image.setImageBitmap(avator);
        inputName.setText(name);
        inputInfo.setText(detail);
        inputHistory.setText(power);
    }

    // 压缩bitmap：  bitmap大于40k，通过Intent传递会报错
    // 不过  当图片过大时 压缩时间会很长，会导致无响应错误
    // 待解决  Mark
    // 现在不传bitmap了 所以不用压缩
    /*public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 40) {  //循环判断如果压缩后图片是否大于40kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }*/

    // bitmap转byte[]
    private byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] bitmapByte =baos.toByteArray();
        return bitmapByte;
    }
}

