package com.skysport.inerfaces.bean.basic;

/**
 * 说明:
 * Created by zhangjh on 2016/2/1.
 */
public class InitialPreviewConfig {
    private String caption;
    private String width;
    private String url;
    private String key;
    private InitialPreviewExtra extra;
    private String frameClass;
    private String frameAttr;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public InitialPreviewExtra getExtra() {
        return extra;
    }

    public void setExtra(InitialPreviewExtra extra) {
        this.extra = extra;
    }

    public String getFrameClass() {
        return frameClass;
    }

    public void setFrameClass(String frameClass) {
        this.frameClass = frameClass;
    }

    public String getFrameAttr() {
        return frameAttr;
    }

    public void setFrameAttr(String frameAttr) {
        this.frameAttr = frameAttr;
    }
}
