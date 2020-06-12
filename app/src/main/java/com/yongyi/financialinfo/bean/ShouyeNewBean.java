package com.yongyi.financialinfo.bean;

import java.util.Date;
import java.util.List;

public class ShouyeNewBean {

    private  Mydata data;
    private String success;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Mydata getData() {
        return data;
    }

    public void setData(Mydata data) {
        this.data = data;
    }

    public class Mydata {

        private boolean hasMore;
        private int currentPage;
        private List<dateMsg> list;
        private int pageSize;
        private int totalPage;
        private int totalSize;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
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



        public List<dateMsg> getList() {
            return list;
        }

        public void setList(List<dateMsg> list) {
            this.list = list;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public  class dateMsg {
            private String author;
            private String content;
            private String hot;
            private long id;
            private String link;
            private String picture;
            private String project;
            private String projectKey;
            private long publishTime;
            private String source;
            private String title;
            private String video;

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHot() {
                return hot;
            }

            public void setHot(String hot) {
                this.hot = hot;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getProject() {
                return project;
            }

            public void setProject(String project) {
                this.project = project;
            }

            public String getProjectKey() {
                return projectKey;
            }

            public void setProjectKey(String projectKey) {
                this.projectKey = projectKey;
            }



            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
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
