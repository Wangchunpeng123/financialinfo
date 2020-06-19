package com.yongyi.financialinfo.bean;

import java.util.List;

public class WoHudongBean {
    private DateMsg data;

    public DateMsg getData() {
        return data;
    }

    public void setData(DateMsg data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;

    public static class DateMsg {
        private List<Msg> usertalk;

        public List<Msg> getUsertalk() {
            return usertalk;
        }

        public void setUsertalk(List<Msg> usertalk) {
            this.usertalk = usertalk;
        }


        public static class Msg {
            private  String content;
            private  long id;
            private Talk talk;
            private UserBean.User user;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public Talk getTalk() {
                return talk;
            }

            public void setTalk(Talk talk) {
                this.talk = talk;
            }


            public UserBean.User getUser() {
                return user;
            }

            public void setUser(UserBean.User user) {
                this.user = user;
            }

            public static class Talk {
                private  String content;

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }
        }
    }
}
