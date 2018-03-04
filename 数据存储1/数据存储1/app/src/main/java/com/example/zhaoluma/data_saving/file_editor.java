package com.example.zhaoluma.data_saving;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhaoluma on 2017/11/30.
 */

public class file_editor extends AppCompatActivity {
    private EditText filename;
    private EditText filecontent;
    private Button save;
    private Button load;
    private Button clear;
    private Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_editor);

        filename = (EditText)findViewById(R.id.filename);
        filecontent = (EditText)findViewById(R.id.filecotent);
        save = (Button)findViewById(R.id.save);
        load = (Button)findViewById(R.id.load);
        clear = (Button)findViewById(R.id.clear);
        delete = (Button)findViewById(R.id.delete);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filename.getText().toString().isEmpty()) {
                    Toast.makeText(file_editor.this, "Filename cannot be empty ", Toast.LENGTH_SHORT).show();
                } else {
                    FileOutputStream fileOutputStream = null;
                    try  {
                        fileOutputStream = openFileOutput(filename.getText().toString(), MODE_PRIVATE);
                        String str = filecontent.getText().toString();
                        fileOutputStream.write(str.getBytes());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        Log.i("Tag", "Successfully saved file");
                        Toast.makeText(file_editor.this, "Save successfully", Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        Log.e("TAG", "Fail to save file");
                    }
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filename.getText().toString().isEmpty()) {
                    Toast.makeText(file_editor.this, "File name cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    filecontent.setText("");
                    FileInputStream fileInputStream = null;
                    try {
                        fileInputStream = openFileInput(filename.getText().toString());
                        byte[] contents = new byte[fileInputStream.available()];
                        fileInputStream.read(contents);
                        String str = new String(contents);
                        filecontent.setText(str);
                        Toast.makeText(file_editor.this, "Load successfully", Toast.LENGTH_SHORT).show();

                    } catch (IOException ex) {
                        Toast.makeText(file_editor.this, "Fail to load file", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "Fail to read file.");
                    }
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filename.setText("");
                filecontent.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filename.getText().toString().isEmpty()) {
                    Toast.makeText(file_editor.this, "File name cannot be empty", Toast.LENGTH_SHORT).show();
                }else {
                   deleteFile(filename.getText().toString());
                    Toast.makeText(file_editor.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
