package com.yongyi.financialinfo.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yongyi.financialinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShequFragmentGuanzhu extends Fragment {

    private View view;
    private static String Tag="ShequFragmentGuanzhu";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shequ_guanzhu, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据
        initMsg();
        //初始化界面
        initView();
    }

    private void initMsg() {
    }

    private void initView() {
    }


}
