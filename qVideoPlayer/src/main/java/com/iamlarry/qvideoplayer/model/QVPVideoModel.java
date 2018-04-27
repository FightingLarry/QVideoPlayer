package com.iamlarry.qvideoplayer.model;

/**
 * @author larryycliu on 2018/4/24.
 */

public class QVPVideoModel {

    private String mUrl;
    private String mTitle;

    public QVPVideoModel(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
}
