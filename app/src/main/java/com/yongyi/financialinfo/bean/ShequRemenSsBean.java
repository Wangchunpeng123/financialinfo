package com.yongyi.financialinfo.bean;

import java.util.List;

public class ShequRemenSsBean {
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
            private long id;
            private String content;
            private String picture;
            private long publishTime;
            private User user;
            private long zanCount;
            private long commentCount;
            private long forwardCount;
            private long videoId;
            private Boolean hasZan;
            private Boolean isGuanzhu;
            private Boolean IsGuanzhu_ing;

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public Boolean getGuanzhu_ing() {
                return IsGuanzhu_ing;
            }

            public void setGuanzhu_ing(Boolean guanzhu_ing) {
                IsGuanzhu_ing = guanzhu_ing;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getVideoId() {
                return videoId;
            }

            public void setVideoId(long videoId) {
                this.videoId = videoId;
            }

            public class User{
                private String head;
                private String nickName;
                private long userId;
                private long id;


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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }




            public long getZanCount() {
                return zanCount;
            }

            public void setZanCount(long zanCount) {
                this.zanCount = zanCount;
            }

            public long getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(long commentCount) {
                this.commentCount = commentCount;
            }

            public long getForwardCount() {
                return forwardCount;
            }

            public void setForwardCount(long forwardCount) {
                this.forwardCount = forwardCount;
            }

            public Boolean getGuanzhu() {
                return isGuanzhu;
            }

            public void setGuanzhu(Boolean guanzhu) {
                isGuanzhu = guanzhu;
            }


            public Boolean getHasZan() {
                return hasZan;
            }

            public void setHasZan(Boolean hasZan) {
                this.hasZan = hasZan;
            }

        }
    }
}
