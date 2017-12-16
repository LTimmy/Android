package com.example.zhaoluma.http;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoluma on 2017/12/14.
 */

public class CardViewadpter extends RecyclerView.Adapter<CardViewadpter.ViewHolder> {

    private List<Github> data = new ArrayList<Github>();
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public CardViewadpter( List<Github> data) {
        this.data = data;
    }
    public void addData(Github github) {
        data.add(github);
        notifyDataSetChanged();
    }
    public void removeData(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
    public void deleteall() {
        if (!data.isEmpty()) {
            data.clear();
            notifyDataSetChanged();
        }

    }
    public  interface OnItemClickListener{
        void onClick(View view, int position);
    }
    public interface OnItemLongClickListener{
        void onLongClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener=onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener=onItemLongClickListener;
    }
    @Override
    public CardViewadpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 绑定布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.github_item, null);
        // 创建ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.loginview.setText(data.get(position).getLogin());
        holder.idview.setText("id: "+ Integer.toString(data.get(position).getId()));
        holder.blogview.setText("blog: "+data.get(position).getBlog());
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onClick(holder.itemView,position); // 2
                }
            });
        }
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView loginview;
        public TextView idview;
        public TextView blogview;
        public ViewHolder(View itemView) {
            super(itemView);
            loginview = (TextView)itemView.findViewById(R.id.login);
            idview = (TextView)itemView.findViewById(R.id.idv);
            blogview = (TextView)itemView.findViewById(R.id.blog);
        }
    }
}
