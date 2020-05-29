package com.yongyi.financialinfo.bean;

import java.util.List;

public class ShequRemenBean {
    private String headIv;
    private String name;
    private String time;
    private Boolean isGuanzhu;
    private String content;
    private String aite;
    private String jinhao;
    private String huati;
    private String date;
    private Boolean isDianzan;
    private List<String> images;
    private Boolean getIsGuanzhu_ing; //已关注点击后的状态

    public String getHeadIv() {
        return headIv;
    }

    public void setHeadIv(String headIv) {
        this.headIv = headIv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getIsGuanzhu() {
        return isGuanzhu;
    }

    public void setIsGuanzhu(Boolean isGuanzhu) {
        this.isGuanzhu = isGuanzhu;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAite() {
        return aite;
    }

    public void setAite(String aite) {
        this.aite = aite;
    }

    public String getJinhao() {
        return jinhao;
    }

    public void setJinhao(String jinhao) {
        this.jinhao = jinhao;
    }

    public String getHuati() {
        return huati;
    }

    public void setHuati(String huati) {
        this.huati = huati;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIsDianzan() {
        return isDianzan;
    }

    public void setIsDianzan(Boolean isDianzan) {
        this.isDianzan = isDianzan;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Boolean getGetIsGuanzhu_ing() {
        return getIsGuanzhu_ing;
    }

    public void setGetIsGuanzhu_ing(Boolean getIsGuanzhu_ing) {
        this.getIsGuanzhu_ing = getIsGuanzhu_ing;
    }
}
