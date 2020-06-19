package com.yongyi.financialinfo.bean;

import java.util.List;

public class ShequPinglunMsgBean {
    private Mydata data;
    private String success;

    public Mydata getData() {
        return data;
    }

    public void setData(Mydata data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public class Mydata{
        private boolean hasMore;
        private int currentPage;
        private List<dateMsg> list;
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

        public List<dateMsg> getList() {
            return list;
        }

        public void setList(List<dateMsg> list) {
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

        public class dateMsg{

            private String content;
            private long publishTime;
            private UserBean.User user;



            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public UserBean.User getUser() {
                return user;
            }

            public void setUser(UserBean.User user) {
                this.user = user;
            }


            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }
        }
    }
}
