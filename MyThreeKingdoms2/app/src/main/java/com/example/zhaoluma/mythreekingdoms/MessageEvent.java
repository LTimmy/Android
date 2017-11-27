package com.example.zhaoluma.mythreekingdoms;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/11/21.
 */

// EventBus传递的事件类
public class MessageEvent {
    // 事件类型：  0--删除  1--修改  2--新增人物
    private int types;
    // 三个文字信息
    private Person person;
    //  头像
    private Bitmap bitmap;
    MessageEvent(int t, Person p, Bitmap b) {
        types = t;
        person = p;
        bitmap = b;
    }
    public int getTypes() {return types;}
    public Person getPerson() {return person;}
    public Bitmap getBitmap() {return bitmap;}
}
