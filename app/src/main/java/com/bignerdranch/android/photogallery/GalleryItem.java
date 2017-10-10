package com.bignerdranch.android.photogallery;

/**
 * Created by francisco on 10/10/17.
 */

public class GalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;

    public GalleryItem(String caption, String id) {
        mCaption = caption;
        mId = id;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "GalleryItem{" +
                "mCaption='" + mCaption + '\'' +
                ", mId='" + mId + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
