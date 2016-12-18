package io.liamju.tangshi.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/11/29
 */
public class Poetry implements Parcelable {

    private String title;
    private char sort; // 标题首字母
    private String auth;
    private String type;
    private String content;
    private String desc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public char getSort() {
        return sort;
    }

    public void setSort(char sort) {
        this.sort = sort;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean contains(String key) {
        return title.contains(key)
                || auth.contains(key)
                || content.contains(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.sort);
        dest.writeString(this.auth);
        dest.writeString(this.type);
        dest.writeString(this.content);
        dest.writeString(this.desc);
    }

    public Poetry() {
    }

    protected Poetry(Parcel in) {
        this.title = in.readString();
        this.sort = (char) in.readInt();
        this.auth = in.readString();
        this.type = in.readString();
        this.content = in.readString();
        this.desc = in.readString();
    }

    public static final Parcelable.Creator<Poetry> CREATOR = new Parcelable.Creator<Poetry>() {
        @Override
        public Poetry createFromParcel(Parcel source) {
            return new Poetry(source);
        }

        @Override
        public Poetry[] newArray(int size) {
            return new Poetry[size];
        }
    };
}
