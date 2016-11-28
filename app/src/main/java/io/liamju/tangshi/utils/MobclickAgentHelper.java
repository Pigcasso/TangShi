package io.liamju.tangshi.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import io.liamju.tangshi.entity.Poetry;

/**
 * @author Zhu Liang
 */
public class MobclickAgentHelper {

    private static final String EVENT_ID_POETRY_LIST = "event_poetry_list";
    private static final String EVENT_ID_MAIN_TAB = "event_main_tab";
    private static final String EVENT_ID_SEARCH = "event_id_search";

    private static final String KEY_POETRY_TITLE = "key_poetry";
    private static final String KEY_MAIN_TAB_ID = "key_main_tab_id";

    private static MobclickAgentHelper sInstance;

    public static MobclickAgentHelper getInstance() {
        if (sInstance == null) {
            synchronized (MobclickAgentHelper.class) {
                if (sInstance == null) {
                    sInstance = new MobclickAgentHelper();
                }
            }
        }
        return sInstance;
    }

    private MobclickAgentHelper() {
    }

    public void init(Context context) {
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    public void onResume(Context context) {
        MobclickAgent.onResume(context);
    }

    public void onPause(Context context) {
        MobclickAgent.onPause(context);
    }

    public void onClickPoetryListItem(final Context context, final Poetry poetry) {
        HashMap<String, String> map = new HashMap<>();
        map.put(KEY_POETRY_TITLE, poetry.getTitle());
        onEvent(context, EVENT_ID_POETRY_LIST, map);
    }

    public void onClickMainTabHost(Context context, String tabId) {
        HashMap<String, String> map = new HashMap<>();
        map.put(KEY_MAIN_TAB_ID, tabId);
        onEvent(context, EVENT_ID_MAIN_TAB, map);
    }

    public void onClickSearch(Context context) {
        onEvent(context, EVENT_ID_SEARCH, null);
    }

    private void onEvent(Context context, String eventId, HashMap<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        MobclickAgent.onEvent(context, eventId, map);
    }
}
