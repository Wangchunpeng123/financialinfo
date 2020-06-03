package com.yongyi.financialinfo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.makeramen.roundedimageview.RoundedImageView;
import com.yongyi.financialinfo.R;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShequRvViewHolder extends RecyclerView.ViewHolder {
    public ImageView headlineImg;
    public TextView title;

    public ShequRvViewHolder(@NonNull View itemView) {
        super(itemView);
        headlineImg = itemView.findViewById(R.id.headline_img);

        title = itemView.findViewById(R.id.title);

    }
}
