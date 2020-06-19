package com.yongyi.financialinfo.bean;

import java.util.List;

public class SignListBean {
    private List<Data> data;
    private boolean success;
    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }




    public static class Data{

        private int continueTimes;
        private long time;
        private long  userId;
        private long id;

        public int getContinueTimes() {
            return continueTimes;
        }

        public void setContinueTimes(int continueTimes) {
            this.continueTimes = continueTimes;
        }



        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }


    }
}
