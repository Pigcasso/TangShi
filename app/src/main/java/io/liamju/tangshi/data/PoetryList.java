package io.liamju.tangshi.data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * @author Zhu Liang
 */
@Root(name = "root")
public class PoetryList {

    @ElementList(inline = true)
    private List<Poetry> list;

    public List<Poetry> getList() {
        return list;
    }

    public void setList(List<Poetry> list) {
        this.list = list;
    }
}
