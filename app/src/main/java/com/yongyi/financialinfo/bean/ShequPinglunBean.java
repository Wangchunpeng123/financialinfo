package com.yongyi.financialinfo.bean;

public class ShequPinglunBean {
    private String userId;
    private String talkId;
    private String videoId;
    private String matchId;
    private String _orderBy;
    private String _pageNumber;
    private String _pageSize;

    public String getTalkId() {
        return talkId;
    }

    public void setTalkId(String talkId) {
        this.talkId = talkId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String get_orderBy() {
        return _orderBy;
    }

    public void set_orderBy(String _orderBy) {
        this._orderBy = _orderBy;
    }

    public String get_pageNumber() {
        return _pageNumber;
    }

    public void set_pageNumber(String _pageNumber) {
        this._pageNumber = _pageNumber;
    }

    public String get_pageSize() {
        return _pageSize;
    }

    public void set_pageSize(String _pageSize) {
        this._pageSize = _pageSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
