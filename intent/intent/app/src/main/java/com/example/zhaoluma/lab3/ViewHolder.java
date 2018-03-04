package com.example.zhaoluma.lab3;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhaoluma on 2017/10/21.
 */

public  class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews; // 存储list_Item的子View
    private View mConvertView; // 存储List_Item
    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }
    public static  ViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder holder = new ViewHolder(context,itemView,parent);
        return holder;
    }
    public <T extends View> T getView(int ViewId) {
        View view = mViews.get(ViewId);
        if (view == null) {
            //创建View
            view = mConvertView.findViewById(ViewId);
            // 将View存入mViews
            mViews.put(ViewId,view);
        }
        return (T)view;
    }
}
