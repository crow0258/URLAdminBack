package com.zero.beg.urladmin.model;

import java.util.List;

public class URLCategroyPO {
    private String title;
    private List<URLPO> urls;

    public URLCategroyPO() {
    }

    public URLCategroyPO(String title, List<URLPO> urls) {
        this.title = title;
        this.urls = urls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<URLPO> getUrls() {
        return urls;
    }

    public void setUrls(List<URLPO> urls) {
        this.urls = urls;
    }
}
