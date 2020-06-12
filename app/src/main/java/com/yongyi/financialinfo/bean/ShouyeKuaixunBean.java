package com.yongyi.financialinfo.bean;

import java.util.List;

public class ShouyeKuaixunBean {
    private List<KuaixunDate>  list;

    public List<KuaixunDate> getList() {
        return list;
    }

    public void setList(List<KuaixunDate> list) {
        this.list = list;
    }

    public static class KuaixunDate {
        private String date;
        private List<livesList> lives;


        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<livesList> getLives() {
            return lives;
        }

        public void setLives(List<livesList> lives) {
            this.lives = lives;
        }

        public static class livesList {
            private String id;
            private int zhuangTai=1;//该条数据的id
            private String content;
            private String tiltle;
            private long created_at;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getZhuangTai() {
                return zhuangTai;
            }

            public void setZhuangTai(int zhuangTai) {
                this.zhuangTai = zhuangTai;
            }

            public String getTiltle() {
                return tiltle;
            }

            public void setTiltle(String tiltle) {
                this.tiltle = tiltle;
            }


            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }
        }
    }
}
