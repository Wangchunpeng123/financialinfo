package com.yongyi.financialinfo.fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.model.ShouyeViewModel;
import com.yongyi.financialinfo.util.MyLog;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouyeFragment extends Fragment {


    private ShouyeViewModel shouyeViewModel;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shouye, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        shouyeViewModel = ViewModelProviders.of(this).get(ShouyeViewModel.class);
        //让TextView观察ViewModel中数据的变化,并实时展示
        shouyeViewModel.mUserLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
               // homeShouyeTv.setText(integer);
                MyLog.e("shouyeViewModel",integer.toString());
               // homeShouyeTv.setText(integer.toString());
            }
        });

    }


}
