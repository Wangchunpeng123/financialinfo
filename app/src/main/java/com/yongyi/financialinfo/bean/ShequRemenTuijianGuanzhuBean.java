package com.yongyi.financialinfo.bean;

import java.util.List;

public class ShequRemenTuijianGuanzhuBean {
    private List<Mydata> data;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Mydata> getData() {
        return data;
    }

    public void setData(List<Mydata> data) {
        this.data = data;
    }


    public class Mydata{

        private long id;
        private String head;
        private String nickName;
        private boolean isGuanzhu=false;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public boolean isGuanzhu() {
            return isGuanzhu;
        }

        public void setGuanzhu(boolean guanzhu) {
            isGuanzhu = guanzhu;
        }
    }
}
