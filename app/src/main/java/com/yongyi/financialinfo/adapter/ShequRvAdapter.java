package com.yongyi.financialinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yongyi.financialinfo.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public  class ShequRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_MIDDLE_TWO = 0;
    private static final int TYPE_7 = 7;


    private View mMiddleView2;
    private List<Integer> listBeans;
    ShequRvBindViewHolder shequRvBindViewHolder =new ShequRvBindViewHolder();
    private Context mContext;


    public void setMiddleView2(View middleView2) {
        mMiddleView2 = middleView2;
    }

    public ShequRvAdapter(List<Integer> list, Context context) {
        this.listBeans = list;
        this.mContext = context;

    }


    @Override
    public int getItemCount() {
        if (listBeans != null && listBeans.size() != 0) {
            return listBeans.size() + 1;
        }
        return 0;
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 3) {
            return TYPE_MIDDLE_TWO;
        }  else {
                return TYPE_7;
            }
    }

    public int getPosition(int i) {
        if (i < 3) {
            return i;
        }
        else {
            return i - 1;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mMiddleView2 != null && viewType == TYPE_MIDDLE_TWO) {
            return new ShequRvViewHolder(mMiddleView2);
        }
        return new ShequRvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type0, null, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ShequRvViewHolder) {
                shequRvBindViewHolder.onBindViewHolder2(holder,listBeans);
            }
            return;
    }


}
