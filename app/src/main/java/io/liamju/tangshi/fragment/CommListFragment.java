package io.liamju.tangshi.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import io.liamju.tangshi.AppConstants;
import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.adapter.PoetryAdapter;
import io.liamju.tangshi.db.PoetryUtil;
import io.liamju.tangshi.entity.Poetry;

/**
 * 以列表的方式展示数据，界面需要展示的数据是：
 * <li>获取某位作者的所有诗词</li>
 * <li>获取某种类型的所有诗词</li>
 * <li>获取用户收藏的所有诗词</li>
 *
 * @author LiamJu
 * @version 1.0
 * @since 15/12/5
 */
public class CommListFragment extends BaseListFragment<PoetryAdapter> {

    private int type;
    private String value;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            type = args.getInt(AppConstants.KEY_COMM_TYPE);
            value = args.getString(AppConstants.KEY_COMM_VALUE);

            setTitle(value);
            setDisplayHomeAsUpEnabled(true);
        } else {
            throw new RuntimeException("必须有参数");
        }
    }

    @Override
    protected PoetryAdapter getAdapter() {
        List<Poetry> poetryList = null;
        if (type == AppConstants.VALUE_COMM_TYPE_AUTH) {
            poetryList = PoetryUtil.getInstance(getContext()).getPoetryListOfAuth(value);
        } else if (type == AppConstants.VALUE_COMM_TYPE_CATEGORY) {
            poetryList = PoetryUtil.getInstance(getContext()).getPoetryListByType(value);
        }
        return new PoetryAdapter(poetryList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Poetry poetry = mAdapter.getData().get(position);
                IntentStarter.showPoetryDetail(getContext(), poetry);
            }
        });
    }
}
