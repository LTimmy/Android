package com.example.zhaoluma.mythreekingdoms;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/21.
 */

//  将每个角色的所有信息抽象为一个类，实现Serializable接口
//  以便用Intent在Activity间传输
//  将图片传递方式由id改为Bitmap后，Bitmap便无法放置在Person中，只能额外传递

public class Person implements Serializable {
    private String name;
    private String detail;
    private String power;
    private Bitmap avator;
    Person(Bitmap a,String n, String d, String p) {
        name = n;
        detail = d;
        power = p;
        avator = a;
    }
    Person(String n, String d, String p) {
        name = n;
        detail = d;
        power = p;
    }
    public void setName(String ss) {
        name = ss;
    }
    public String getName() {
        return name;
    }

    public void setDetail(String ss) {
        detail = ss;
    }
    public String getDetail() {
        return detail;
    }

    public void setPower(String ss) {
        power = ss;
    }
    public String getPower() {
        return power;
    }


    public void setAvator(Bitmap n) { avator = n; }
    public Bitmap getAvator() {
        return avator;
    }

}
