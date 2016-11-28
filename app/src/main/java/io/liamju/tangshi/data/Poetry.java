package io.liamju.tangshi.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Zhu Liang
 */
@Entity
@Root(name = "node")
public class Poetry {

    @Id(autoincrement = true)
    private Long id;
    @Element
    private String title;
    @Element
    private String auth;
    @Element
    private String type;
    @Element
    private String content;
    @Element
    private String desc;

    public Poetry() {
    }

    public Poetry(String title, String auth, String type, String content, String desc) {
        this.title = title;
        this.auth = auth;
        this.type = type;
        this.content = content;
        this.desc = desc;
    }

    @Generated(hash = 1899166872)
    public Poetry(Long id, String title, String auth, String type, String content,
                  String desc) {
        this.id = id;
        this.title = title;
        this.auth = auth;
        this.type = type;
        this.content = content;
        this.desc = desc;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
