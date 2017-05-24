
package com.example.fkz2.news_fkz.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {



    public final static int TYPE_BANNER = 1;
    public final static int TYPE_CONTENT = 0;
    private int type = 0;
    private String docid;
    private String title;
    private int replyCount;
    private String lmodify;
    private String ltitle;
    private String imgsrc;
    private String digest;
    private String url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getLtitle() {
        return ltitle;
    }

    public void setLtitle(String ltitle) {
        this.ltitle = ltitle;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.docid);
        dest.writeString(this.title);
        dest.writeInt(this.replyCount);
        dest.writeString(this.lmodify);
        dest.writeString(this.ltitle);
        dest.writeString(this.imgsrc);
        dest.writeString(this.digest);
        dest.writeString(this.url);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.type = in.readInt();
        this.docid = in.readString();
        this.title = in.readString();
        this.replyCount = in.readInt();
        this.lmodify = in.readString();
        this.ltitle = in.readString();
        this.imgsrc = in.readString();
        this.digest = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
