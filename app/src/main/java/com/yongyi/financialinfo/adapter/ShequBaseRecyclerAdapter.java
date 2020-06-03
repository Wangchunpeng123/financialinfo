package com.yongyi.financialinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yongyi.financialinfo.util.MyLog;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2017/6/5.
 */
public abstract class ShequBaseRecyclerAdapter<T> extends RecyclerView.Adapter<ShequBaseRecyclerViewHolder>{
    private Context context;
    private List<T> ts;
    private int layoutId;
    private View view;

    public static int type=0;
    public View mMiddleView2;
    private int item;

    public ShequBaseRecyclerAdapter(Context context, List<T> ts, int layoutId) {
        this.context = context;
        this.ts = ts;
        this.layoutId = layoutId;
    }

    @Override
    public ShequBaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMiddleView2 != null&& viewType == type )
            view= mMiddleView2;
        else
             view= LayoutInflater.from(context).inflate(layoutId,parent,false);
        ShequBaseRecyclerViewHolder holder=new ShequBaseRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == item) {
            return type;
        }  else {
            return 7;
        }
    }
    @Override
    public void onBindViewHolder(ShequBaseRecyclerViewHolder holder, int position) {
        if(ts.size()<item)
            item=ts.size();
        if(position<item){
            MyLog.e("ShequBaseRecyclerAdapter",position+"正常界面");
            T t = ts.get(position);
            bindData(holder, t, position);
        }else if (position==item){
            MyLog.e("ShequBaseRecyclerAdapter",position+"插入界面");
        }else if (position>item){
            MyLog.e("ShequBaseRecyclerAdapter",position+"正常界面");
            T t = ts.get(position-1);
            bindData(holder, t, position-1);
        }

    }

    @Override
    public int getItemCount() {
        MyLog.e("ShequBaseRecyclerAdapter",ts.size()+1+"");
        return ts.size()+1 ;
    }

     public abstract void bindData(ShequBaseRecyclerViewHolder holder,T t,int position);

     public void clickEvent(int viewId,T t,int position){

    }

    public void setMiddleView2(int item,View middleView2) {
        mMiddleView2 = middleView2;
        type=1;
        this.item=item;
    }

}
