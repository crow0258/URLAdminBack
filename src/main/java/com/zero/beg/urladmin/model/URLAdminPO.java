package com.zero.beg.urladmin.model;

import java.util.List;

public class URLAdminPO {
    private String url;
    private String img;
    private List<URLCategroyPO> categroies;

    public URLAdminPO() {
    }

    public  URLAdminPO(String url, List<URLCategroyPO> categroies) {
        this.url = url;
        this.categroies = categroies;
    }

    @Override
    public String toString() {
        StringBuffer info = new StringBuffer("-URLAdim-");
        info.append(" -name:").append(url);
        info.append(" -categroies:").append(categroies.size());
        return info.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<URLCategroyPO> getCategroies() {
        return categroies;
    }

    public void setCategroies(List<URLCategroyPO> categroies) {
        this.categroies = categroies;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
