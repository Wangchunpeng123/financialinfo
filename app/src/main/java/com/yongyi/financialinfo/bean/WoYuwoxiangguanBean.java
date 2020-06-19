package com.yongyi.financialinfo.bean;

import java.util.List;

public class WoYuwoxiangguanBean {
    private ShequRemenSsBean.Mydata data;
    private String success;

    public ShequRemenSsBean.Mydata getData() {
        return data;
    }

    public void setData(ShequRemenSsBean.Mydata data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public class Mydata {
        private boolean hasMore;
        private int currentPage;
        private List<ShequRemenSsBean.Mydata.dateMsg> list;
        private int pageSize;
        private int totalPage;
        private int totalSize;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<ShequRemenSsBean.Mydata.dateMsg> getList() {
            return list;
        }

        public void setList(List<ShequRemenSsBean.Mydata.dateMsg> list) {
            this.list = list;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }
    }
}
