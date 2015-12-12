package io.liamju.tangshi.entity;

import java.io.Serializable;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/11/29
 */
public class Poetry implements Serializable {

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
}
