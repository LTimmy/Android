package com.example.zhaoluma.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoluma on 2017/12/15.
 */

public class RepoActivity extends AppCompatActivity {
    private  List<Map<String, Object>> mDatas = new ArrayList<>();
    public static final String baseurl = "https://api.github.com";
    private ListView repo_list;
    private SimpleAdapter adapter;
    private RepoService service;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_layout);

        repo_list = (ListView)findViewById(R.id.repo_list);
        progressBar = (ProgressBar)findViewById(R.id.pro_bar1);
        progressBar.setVisibility(View.VISIBLE);

        adapter = new SimpleAdapter(this, mDatas, R.layout.repo_item,
                new String[] {"name", "language", "description"},
                new int[] {R.id.repo_name, R.id.language, R.id.description});
        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("name");

        Retrofit retrofit = createRetrofit(baseurl);
        service = retrofit.create(RepoService.class);
        service.getUser(name)
                .subscribeOn(Schedulers.newThread())   // 新建线程进行网络访问
                .observeOn(AndroidSchedulers.mainThread())  // 在主线程处理请求结果
                .subscribe(new Subscriber<List<Repositories>>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.INVISIBLE);
                        System.out.println("完成传输");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.e("Github", "test"+e.getMessage());
                    }
                    @Override
                    public void onNext(List<Repositories> list) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (list.size() == 0) {
                            Toast.makeText(RepoActivity.this,
                                    "此用户没有Repository", Toast.LENGTH_SHORT).show();
                        }
                        for (int i = 0; i < list.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("name",list.get(i).getName());
                            map.put("language",list.get(i).getLanguage());
                            map.put("description", list.get(i).getDescription());
                            mDatas.add(map);
                            adapter.notifyDataSetChanged();
                            Log.d("github", list.get(i).getName());
                            Log.d("github", list.get(i).getLanguage());
                            Log.d("github", list.get(i).getDescription());
                        }
                    }
                });
        repo_list.setAdapter(adapter);

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
