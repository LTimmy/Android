package com.example.zhaoluma.data_saving;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class data_saving extends AppCompatActivity {
    private Button ok;
    private Button clear;
    private EditText password;
    private EditText con_password;
    public static int MODE = MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "data_saving";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_saving);

        password = (EditText)findViewById(R.id.password);
        con_password = (EditText)findViewById(R.id.con_password);
        ok = (Button)findViewById(R.id.OK);
        clear = (Button)findViewById(R.id.clear);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        Boolean user_first = sharedPreferences.getBoolean("FIRST",true);

        if (user_first) {
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences1 = getSharedPreferences(PREFERENCE_NAME, MODE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    if (password.getText().toString().isEmpty() || con_password.getText().toString().isEmpty()) {
                        Toast.makeText(data_saving.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (!password.getText().toString().equals(con_password.getText().toString())) {
                        Toast.makeText(data_saving.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    } else if (!password.getText().toString().isEmpty()
                            && password.getText().toString().equals(con_password.getText().toString())){
                        editor.putString("password", con_password.getText().toString());
                        editor.putBoolean("FIRST", false);
                        editor.commit();
                        Intent intent = new Intent(data_saving.this, file_editor.class);
                        startActivity(intent);
                        //Toast.makeText(data_saving.this, "数据成功写入", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    password.setText("");
                    con_password.setText("");
                }
            });
        } else {
            password.setVisibility(View.INVISIBLE);
            con_password.setHint("Password");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences1 = getSharedPreferences(PREFERENCE_NAME, MODE);
                    String p = sharedPreferences1.getString("password", "Default");
                    if (con_password.getText().toString().isEmpty()) {
                        Toast.makeText(data_saving.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (!con_password.getText().toString().equals(p)) {
                        Toast.makeText(data_saving.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    } else if (!con_password.getText().toString().isEmpty() && con_password.getText().toString().equals(p)) {
                        Intent intent = new Intent(data_saving.this, file_editor.class);
                        startActivity(intent);
                        //Toast.makeText(data_saving.this, "Succeed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    con_password.setText("");
                }
            });
        }

    }
}
