package io.liamju.tangshi.activity;

import io.liamju.tangshi.R;
import io.liamju.tangshi.fragment.AuthListFragment;
import io.liamju.tangshi.fragment.CategoryFragment;
import io.liamju.tangshi.fragment.PoetryListFragment;
import io.liamju.tangshi.fragment.SettingFragment;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/11/30
 */
public enum MainTab {

    POETRY(0, R.string.main_tab_poetry, R.drawable.tab_icon_poetry, PoetryListFragment.class),
    AUTH(1, R.string.main_tab_auth, R.drawable.tab_icon_auth, AuthListFragment.class),
    CATEGORY(2, R.string.main_tab_category, R.drawable.tab_icon_category, CategoryFragment.class),
    SETTING(3, R.string.main_tab_setting, R.drawable.tab_icon_setting, SettingFragment.class);

    private int index;
    private int tabNameRes;
    private int tabIconRes;
    private Class<?> clz;

    MainTab(int index, int tabNameRes, int tabIconRes, Class<?> clz) {
        this.index = index;
        this.tabNameRes = tabNameRes;
        this.tabIconRes = tabIconRes;
        this.clz = clz;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTabNameRes() {
        return tabNameRes;
    }

    public void setTabNameRes(int tabNameRes) {
        this.tabNameRes = tabNameRes;
    }

    public int getTabIconRes() {
        return tabIconRes;
    }

    public void setTabIconRes(int tabIconRes) {
        this.tabIconRes = tabIconRes;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}