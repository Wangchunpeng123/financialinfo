package com.yongyi.financialinfo.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yongyi.financialinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouyeFragment extends Fragment {

    public ShouyeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shouye, container, false);
    }
}
