package com.yongyi.financialinfo.bean;

        import java.util.List;
        import java.util.function.BinaryOperator;

public class ShequRemenGuanzhuBean {
    private Mydata data;
    private boolean success;

    public Mydata getData() {
        return data;
    }

    public void setData(Mydata data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
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
            private String head;
            private String nickName;
            private String signature;

            private boolean isGuanzhu;

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

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }
        }
    }
}
