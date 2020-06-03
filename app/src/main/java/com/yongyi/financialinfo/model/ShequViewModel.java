package com.yongyi.financialinfo.model;

import com.yongyi.financialinfo.bean.ShequRemenBean;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShequViewModel extends ViewModel {

    public final MutableLiveData<List<ShequRemenBean>> mUserLiveData = new MutableLiveData<>();
    private List<ShequRemenBean> Remenlist=new ArrayList<>();

    public final MutableLiveData<List<ShequRemenBean>> guanzhuData = new MutableLiveData<>();
    private List<ShequRemenBean> guanzhulist=new ArrayList<>();

    public ShequViewModel(){
    }

    public void setGuanzhuMsg() {
        List<String> list=new ArrayList<>();
        list.add("https://pics2.baidu.com/feed/e7cd7b899e510fb3de879c88a4527293d0430cff.jpeg?token=80099e45096c2ab1ad3ed388ae0c3401");
        list.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        list.add("https://pics2.baidu.com/feed/e7cd7b899e510fb3de879c88a4527293d0430cff.jpeg?token=80099e45096c2ab1ad3ed388ae0c3401");
        list.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        list.add("https://pics2.baidu.com/feed/e7cd7b899e510fb3de879c88a4527293d0430cff.jpeg?token=80099e45096c2ab1ad3ed388ae0c3401");
        list.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        ShequRemenBean remen=new ShequRemenBean();
        remen.setImages(list);
        remen.setName("区块链姐姐111");
        remen.setTime("五分钟");
        remen.setIsGuanzhu(false);
        remen.setContent("OPPO Ace2支持超清视频防抖、超广角视频等，让视频拍摄有更大的发挥空间。");
        remen.setHeadIv("https://pics1.baidu.com/feed/50da81cb39dbb6fd027b96a4836d101e962b37db.png?token=b6b5114b7220cd08987be156f6927f96");
        remen.setJinhao("#比特圈圈");
        remen.setHuati("1522人参与了该话题");
        remen.setDate("2020/1/23");
        remen.setIsDianzan(false);
        remen.setGetIsGuanzhu_ing(false);
        guanzhulist.add(remen);

        remen=new ShequRemenBean();
        List<String> list1=new ArrayList<>();
        list1.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        remen.setImages(list1);
        remen.setName("区块链小姐姐2222");
        remen.setTime("刚刚");
        remen.setIsGuanzhu(true);
        remen.setContent("OPPO Ace2支持超清视频防抖、超广角视频等，让视频拍摄有更大的发挥空间。拉啊啊啊啊啊啊啊");
        remen.setHeadIv("https://pics1.baidu.com/feed/50da81cb39dbb6fd027b96a4836d101e962b37db.png?token=b6b5114b7220cd08987be156f6927f96");
        remen.setJinhao("#比特圈真乱");
        remen.setHuati("1.5万人参与了该话题");
        remen.setDate("2020/11/23");
        remen.setIsDianzan(true);
        remen.setGetIsGuanzhu_ing(false);
        guanzhulist.add(remen);

        remen=new ShequRemenBean();
        List<String> list2=new ArrayList<>();
        list2.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        remen.setImages(list2);
        remen.setName("区块链小姐姐3333");
        remen.setTime("刚刚");
        remen.setIsGuanzhu(true);
        remen.setContent("OPPO Ace2支持超清视频防抖、超广角视频等，让视频拍摄有更大的发挥空间。拉啊啊啊啊啊啊啊");
        remen.setHeadIv("https://pics1.baidu.com/feed/50da81cb39dbb6fd027b96a4836d101e962b37db.png?token=b6b5114b7220cd08987be156f6927f96");
        remen.setJinhao("#比特圈真乱");
        remen.setHuati("1.5万人参与了该话题");
        remen.setDate("2020/11/23");
        remen.setIsDianzan(true);
        remen.setGetIsGuanzhu_ing(false);
        guanzhulist.add(remen);

        guanzhuData.setValue(guanzhulist);
    }


    //模拟 进行一些数据骚操作
    public void setMsg() {
        List<String> list=new ArrayList<>();
        list.add("https://pics2.baidu.com/feed/e7cd7b899e510fb3de879c88a4527293d0430cff.jpeg?token=80099e45096c2ab1ad3ed388ae0c3401");
        list.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        list.add("https://pics2.baidu.com/feed/e7cd7b899e510fb3de879c88a4527293d0430cff.jpeg?token=80099e45096c2ab1ad3ed388ae0c3401");
        list.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        list.add("https://pics2.baidu.com/feed/e7cd7b899e510fb3de879c88a4527293d0430cff.jpeg?token=80099e45096c2ab1ad3ed388ae0c3401");
        list.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        ShequRemenBean remen=new ShequRemenBean();
        remen.setImages(list);
        remen.setName("区块链姐姐111");
        remen.setTime("五分钟");
        remen.setIsGuanzhu(false);
        remen.setContent("OPPO Ace2支持超清视频防抖、超广角视频等，让视频拍摄有更大的发挥空间。");
        remen.setHeadIv("https://pics1.baidu.com/feed/50da81cb39dbb6fd027b96a4836d101e962b37db.png?token=b6b5114b7220cd08987be156f6927f96");
        remen.setJinhao("#比特圈圈");
        remen.setHuati("1522人参与了该话题");
        remen.setDate("2020/1/23");
        remen.setIsDianzan(false);
        remen.setGetIsGuanzhu_ing(false);
        Remenlist.add(remen);

        remen=new ShequRemenBean();
        List<String> list1=new ArrayList<>();
        list1.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        remen.setImages(list1);
        remen.setName("区块链小姐姐2222");
        remen.setTime("刚刚");
        remen.setIsGuanzhu(true);
        remen.setContent("OPPO Ace2支持超清视频防抖、超广角视频等，让视频拍摄有更大的发挥空间。拉啊啊啊啊啊啊啊");
        remen.setHeadIv("https://pics1.baidu.com/feed/50da81cb39dbb6fd027b96a4836d101e962b37db.png?token=b6b5114b7220cd08987be156f6927f96");
        remen.setJinhao("#比特圈真乱");
        remen.setHuati("1.5万人参与了该话题");
        remen.setDate("2020/11/23");
        remen.setIsDianzan(true);
        remen.setGetIsGuanzhu_ing(false);
        Remenlist.add(remen);

        remen=new ShequRemenBean();
        List<String> list2=new ArrayList<>();
        list2.add("https://pics1.baidu.com/feed/7dd98d1001e939019c06fc41c4d1e8e136d19609.jpeg?token=3ac5134052d64579fe8db69830f532b1");
        remen.setImages(list2);
        remen.setName("区块链小姐姐3333");
        remen.setTime("刚刚");
        remen.setIsGuanzhu(true);
        remen.setContent("OPPO Ace2支持超清视频防抖、超广角视频等，让视频拍摄有更大的发挥空间。拉啊啊啊啊啊啊啊");
        remen.setHeadIv("https://pics1.baidu.com/feed/50da81cb39dbb6fd027b96a4836d101e962b37db.png?token=b6b5114b7220cd08987be156f6927f96");
        remen.setJinhao("#比特圈真乱");
        remen.setHuati("1.5万人参与了该话题");
        remen.setDate("2020/11/23");
        remen.setIsDianzan(true);
        remen.setGetIsGuanzhu_ing(false);
        Remenlist.add(remen);

        mUserLiveData.setValue(Remenlist);
    }


}
