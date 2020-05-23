package com.yongyi.financialinfo.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ShouyeViewModel extends ViewModel {

    public final MutableLiveData<Integer> mUserLiveData = new MutableLiveData<>();
    public ShouyeViewModel(){
        mUserLiveData.postValue(1);
    }
    //模拟 进行一些数据骚操作
    public void doSomething() {
        int i = mUserLiveData.getValue();
        i++;
        mUserLiveData.setValue(i);
    }


}
