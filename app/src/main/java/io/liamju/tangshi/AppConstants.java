package io.liamju.tangshi;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/2
 */
public class AppConstants {

    // 用于PoetryDetailActivity.java的常量
    public static final String KEY_POETRY_DETAIL = "poetryDetail";
    public static final String KEY_IS_COLLECTED = "isCollected";
    // 如果由CollectionFragment跳转到DetailActivity时使用
    public static final int REQUEST_CODE_DETAIL = 10;

    // 用于FragmentContainerActivity.java的常量
    public static final String KEY_ARGS = "args";
    public static final String KEY_CLASS_NAME = "className";
    public static final String FRAGMENT_TAG = "fragmentTag";
    // 用于CommListFragment.java的常量
    public static final String KEY_COMM_TYPE = "commonType";
    public static final String KEY_COMM_VALUE = "commonValue";
    public static final int VALUE_COMM_TYPE_AUTH = 100;

    public static final int VALUE_COMM_TYPE_CATEGORY = 101;
    public static final int VALUE_COMM_TYPE_STAR = 102;

    // 用于HistoryFragment.java的常量
    public static final String KEY_FOOTPRINT_TYPE = "footprintType";
    public static final int VALUE_FOOTPRINT_COLLECTION = 201;

    public static final String KEY_LOAD_URL = "loadUrl";

    public static final String URL_DESIGNER_HOMEPAGE = "http://www.zcool.com.cn/u/13619712";
    public static final String URL_DEVELOPER_HOMEPAGE = "http://weibo.com/droideep";
    public static final String MAIL_DEVELOPER = "droideep@163.com";
}
