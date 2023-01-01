package cn.danliren.apps.tangshi.data;

import java.io.Serializable;

/**
 * @author Zhu Liang
 */
public class Poetry implements Serializable {

    private int id;
    private String title;
    private String auth;
    private String type;
    private String content;
    private String desc;
    /**
     * 标题首字母
     */
    private String sort;

    private boolean isFavorite;

    public Poetry() {
    }

    public Poetry(String title, String auth, String type, String content, String desc) {
        this.title = title;
        this.auth = auth;
        this.type = type;
        this.content = content;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuth() {
        return auth;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getDesc() {
        return desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
