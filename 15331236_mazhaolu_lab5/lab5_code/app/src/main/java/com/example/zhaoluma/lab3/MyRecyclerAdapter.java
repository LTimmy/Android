package com.example.zhaoluma.lab3;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoluma on 2017/10/21.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Map<String, Object>> mDatas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private int ID;
    public MyRecyclerAdapter(Context context, int layoutId, List<Map<String, Object>> datas) {
        this.mContext = context;
        this.ID = layoutId;
        this.mDatas = datas;
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
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext,parent,ID);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Map<String, Object> s = mDatas.get(position);
        TextView name = holder.getView(R.id.name);
        name.setText(s.get("name").toString());
        TextView initial = holder.getView(R.id.initial);
        initial.setText(s.get("initial").toString());
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
    public  int getItemCount() {
        return mDatas.size();
    }
}
