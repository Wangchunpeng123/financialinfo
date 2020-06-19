package com.yongyi.financialinfo.bean;

public class UserTalkBean {
    private String _pageNumber;
    private String _pageSize;
    private String userId;
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
