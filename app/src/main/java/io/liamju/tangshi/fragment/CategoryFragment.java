package io.liamju.tangshi.fragment;

import android.view.View;

import io.liamju.tangshi.AppConstants;
import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.adapter.CommonSimpleAdapter;
import io.liamju.tangshi.db.PoetryUtil;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/11/30
 */
public class CategoryFragment extends BaseListFragment<CommonSimpleAdapter> {
    @Override
    protected CommonSimpleAdapter getAdapter() {
        return new CommonSimpleAdapter(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String category = mAdapter.getData().get(position);
                IntentStarter.showCommList(getActivity(), AppConstants.VALUE_COMM_TYPE_CATEGORY, category);
            }
        }, PoetryUtil.getInstance(getContext()).getTypes());
    }
}
