package com.yongyi.financialinfo.bean;

public class UserBean {
    private String msg;
   private User data;
    private String success;

    public UserBean() {
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class User {
        private String uuid;
        private long id;
        private String phone;
        private String password;
        private String head;
        private String album;
        private String nickName;
        private String signature;
        private String type;
        private String projectKey;
        private String talkCount;
        private String followCount;
        private String fansCount;
        private String project;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getProjectKey() {
            return projectKey;
        }

        public void setProjectKey(String projectKey) {
            this.projectKey = projectKey;
        }

        public String getTalkCount() {
            return talkCount;
        }

        public void setTalkCount(String talkCount) {
            this.talkCount = talkCount;
        }

        public String getFollowCount() {
            return followCount;
        }

        public void setFollowCount(String followCount) {
            this.followCount = followCount;
        }

        public String getFansCount() {
            return fansCount;
        }

        public void setFansCount(String fansCount) {
            this.fansCount = fansCount;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }


        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
