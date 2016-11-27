package io.liamju.tangshi.data.source;

import java.util.List;

import io.liamju.tangshi.data.Poetry;

/**
 * @author Zhu Liang
 */

public interface PoetryDataSource {
    List<Poetry> getPoetryList();

    void savePoetryList(List<Poetry> poetryList);
}
