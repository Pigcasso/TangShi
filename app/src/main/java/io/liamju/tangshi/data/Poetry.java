package io.liamju.tangshi.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Zhu Liang
 */
@Root(name = "node")
public class Poetry {

    private int id;

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
}
