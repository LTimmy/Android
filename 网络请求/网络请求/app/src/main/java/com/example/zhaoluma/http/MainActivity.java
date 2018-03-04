package com.example.zhaoluma.http;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText msearch;
    private Button clear;
    private Button fetch;
    private ProgressBar progressBar;
    public static final String baseurl = "https://api.github.com";
    private GithubService service;
    private RecyclerView recyclerView;
    private List<Github> data = new ArrayList<>();
    private CardViewadpter cardViewadpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = createRetrofit(baseurl);
        service = retrofit.create(GithubService.class);

        recyclerView = (RecyclerView)findViewById(R.id.github_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardViewadpter = new CardViewadpter(data);

        msearch = (EditText)findViewById(R.id.search);
        clear = (Button)findViewById(R.id.clear);
        fetch = (Button)findViewById(R.id.fetch);
        progressBar = (ProgressBar)findViewById(R.id.pro_bar);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                service.getUser(msearch.getText().toString())
                        .subscribeOn(Schedulers.newThread())   // 新建线程进行网络访问
                        .observeOn(AndroidSchedulers.mainThread())  // 在主线程处理请求结果
                        .subscribe(new Subscriber<Github>() {
                            @Override
                            public void onCompleted() {
                                progressBar.setVisibility(View.INVISIBLE);
                                System.out.println("完成传输");
                            }
                            @Override
                            public void onError(Throwable e) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, e.hashCode()
                                + "请确认你搜索的用户存在", Toast.LENGTH_SHORT).show();
                                Log.e("Github", "test"+e.getMessage());
                            }
                            @Override
                            public void onNext(Github github) {
                                progressBar.setVisibility(View.INVISIBLE);
                                cardViewadpter.addData(github);
                            }
                        });
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewadpter.deleteall();
            }
        });
        cardViewadpter.setOnItemLongClickListener(new CardViewadpter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                cardViewadpter.removeData(position);
            }
        });
        cardViewadpter.setOnItemClickListener(new CardViewadpter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, RepoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", data.get(position).getLogin());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(cardViewadpter);
    }

    private static Retrofit createRetrofit(String u) {
        return new Retrofit.Builder().baseUrl(u)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttp())
                .build();
    }
    private static OkHttpClient createOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) // 连接超时
                .readTimeout(30, TimeUnit.SECONDS)  // 读超时
                .writeTimeout(10, TimeUnit.SECONDS) // 写超时
                .build();
        return okHttpClient;
    }
}
