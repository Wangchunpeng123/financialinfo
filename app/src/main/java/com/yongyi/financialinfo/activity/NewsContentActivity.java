package com.yongyi.financialinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.GlideImageLoader;
import com.yongyi.financialinfo.util.MyLog;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class NewsContentActivity<list_path> extends Activity {
    @BindView(R.id.news_tv)
    TextView title;
    @BindView(R.id.newscontent_main)
    TextView mainContent;
    @BindView(R.id.news_srouce)
    TextView newsSrouce;
    @BindView(R.id.news_time)
    TextView newsTime;
    @BindView(R.id.news_picture)
    Banner picture;
    @BindView(R.id.news_read)
    TextView newsRead;
    @BindView(R.id.news_writer)
    TextView newAuthor;
    @BindView(R.id.news_jiantou)
    ImageView backImg;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newscontent);
        ButterKnife.bind(this);
        mainContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        Intent intent = this.getIntent();
        String Title = intent.getStringExtra("title");
        String Content = intent.getStringExtra("Content");
        String Author = intent.getStringExtra("Author");
        String Hot = intent.getStringExtra("Hot");
        String Source = intent.getStringExtra("Source");
        String Time = intent.getStringExtra("Time");
        String Picture = intent.getStringExtra("Picture");


        if (Title == null){
            MyLog.e(TAG,"什么");
        }else {
            title.setText(Title);
            newAuthor.setText(Author);
            newsRead.setText(Hot);
            newsSrouce.setText(Source);
            mainContent.setText(Content);
            newsTime.setText(Time);
        }
        list = new ArrayList<>();
        list.add(Picture);
        picture.setImageLoader(new GlideImageLoader());
        picture.setImages(list);
        picture.start();

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
