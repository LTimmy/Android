package com.example.zhaoluma.birthday_remind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zhaoluma on 2017/12/7.
 */

public class Add_item extends AppCompatActivity {
    private Button additem;
    private EditText addname;
    private EditText addbirthday;
    private EditText addgift;
    private myDB mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mydb = new myDB(getApplicationContext());
        additem = (Button)findViewById(R.id.additem);
        addname = (EditText)findViewById(R.id.addname);
        addbirthday = (EditText)findViewById(R.id.addbirthday);
        addgift = (EditText)findViewById(R.id.addgift);

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addname.getText().toString();
                String birthday = addbirthday.getText().toString();
                String gift = addgift.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "名字为空，请完善", Toast.LENGTH_SHORT).show();
                } else {
                    if (mydb.Query(name)) {
                        Toast.makeText(getApplicationContext(),
                                "名字重复啦，请检查", Toast.LENGTH_SHORT).show();
                    } else {
                        member m = new member(name, birthday, gift);
                        mydb.insert(m);
                        Intent intent = new Intent(Add_item.this, Birthday_remind.class);
                        startActivity(intent);
                    }
                }

            }
        });

    }
}
