package com.example.zhaoluma.lab1;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class lab1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);
        TextInputLayout mNumberText = (TextInputLayout) findViewById(R.id.edittext1);
        ImageView mImage = (ImageView) findViewById(R.id.image);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final String[] Items = {"拍摄","从相册中选择"};
        alertDialog.setTitle("上传头像").setItems(Items,
                new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(lab1.this,
                            "您选择了["+Items[i]+"]",Toast.LENGTH_SHORT).show();
                }
        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(lab1.this,
                        "您选择了[取消]",Toast.LENGTH_SHORT).show();
            }
        }).create();
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
        final TextInputLayout edittext1 = (TextInputLayout)findViewById(R.id.edittext1);
        final TextInputLayout edittext2 = (TextInputLayout)findViewById(R.id.edittext2);
        edittext1.setHint("请输入学号");
        edittext2.setHint("请输入密码");
        final RadioGroup mRadioGroup = (RadioGroup)findViewById(R.id.radio);
        final RadioButton radio1 = (RadioButton)findViewById(R.id.radio1);
        final RadioButton radio2 = (RadioButton)findViewById(R.id.radio2);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton selectButton = (RadioButton)findViewById(group.getCheckedRadioButtonId());
                String identity = selectButton.getText().toString();
                Snackbar.make(group, "您选择了"+identity, Snackbar.LENGTH_SHORT).
                        setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(lab1.this, "Snackbar 的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                            }
                        }).
                        setActionTextColor(getResources().getColor(R.color.colorPrimary)).
                        setDuration(5000).show();
            }
        });
        Button bnt1 = (Button)findViewById(R.id.button1);
        bnt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                hideKeyboard();
                String username = edittext1.getEditText().getText().toString();
                String password = edittext2.getEditText().getText().toString();
                if (username.length() == 0) {
                    edittext1.setError("学号不能为空");
                    edittext2.setErrorEnabled(false);
                } else if (username.length() != 0 && password.length() == 0) {
                    edittext1.setErrorEnabled(false);
                    edittext2.setError("密码不能为空");
                } else if (username.equals("123456") && password.equals("6666")) {
                    edittext1.setErrorEnabled(false);
                    edittext2.setErrorEnabled(false);
                    Snackbar.make(view, "登录成功", Snackbar.LENGTH_SHORT).
                            setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(lab1.this, "Snackbar 的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                                }
                            }).
                            setActionTextColor(getResources().getColor(R.color.colorPrimary)).
                            setDuration(5000).show();
                } else {
                    edittext1.setErrorEnabled(false);
                    edittext2.setErrorEnabled(false);
                    Snackbar.make(view, "学号或密码错误", Snackbar.LENGTH_SHORT).
                            setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(lab1.this, "Snackbar 的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                                }
                            }).
                            setActionTextColor(getResources().getColor(R.color.colorPrimary)).
                            setDuration(5000).show();
                }
            }
        });
        Button bnt2 = (Button)findViewById(R.id.button2);
        bnt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                RadioButton selectedButton = (RadioButton)findViewById(mRadioGroup.getCheckedRadioButtonId());
                String identity = selectedButton.getText().toString();
                Snackbar.make(view, identity+"注册功能尚未启用", Snackbar.LENGTH_SHORT).
                        setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(lab1.this, "Snackbar 的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                            }
                        }).
                        setActionTextColor(getResources().getColor(R.color.colorPrimary)).
                        setDuration(5000).show();
            }

        });
    }
    /*用户不再需要键盘。不幸的是，如果你不告诉它，
    安卓不会自动的隐藏虚拟键盘。在onClick方法体中调用hideKeyboard。*/
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
