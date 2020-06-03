package com.yongyi.financialinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2017/6/5.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder>{
    private Context context;
    private List<T> ts;
    private int layoutId;
    private View view;

    public BaseRecyclerAdapter(Context context, List<T> ts, int layoutId) {
        this.context = context;
        this.ts = ts;
        this.layoutId = layoutId;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view= LayoutInflater.from(context).inflate(layoutId,parent,false);
        BaseRecyclerViewHolder holder=new BaseRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
            T t=ts.get(position);
            bindData(holder,t,position);

    }

    @Override
    public int getItemCount() {
                return ts.size() ;
    }

     public abstract void bindData(BaseRecyclerViewHolder holder,T t,int position);

     public void clickEvent(int viewId,T t,int position){

    }


}
