package com.yongyi.financialinfo.bean;

import java.util.Date;
import java.util.List;

public class BannerListBean {
    private List<DataImage> data;
    private Boolean success;



    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<DataImage> getData() {
        return data;
    }

    public void setData(List<DataImage> data) {
        this.data = data;
    }

    public static class DataImage {
        private long data;
        private long id;
        private String picturePath;
        private String project;

        public long getData() {
            return data;
        }

        public long getId() {
            return id;
        }

        public String getPicturePath() {
            return picturePath;
        }

        public String getProject() {
            return project;
        }

        public String getProjectKey() {
            return projectKey;
        }

        public int getType() {
            return type;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public String getUrl() {
            return url;
        }

        private String projectKey;
        private int type;
        private long updateTime;
        private String url;



    }
}
