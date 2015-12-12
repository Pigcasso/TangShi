package io.liamju.tangshi.fragment;

import android.os.Bundle;
import android.view.View;

import io.liamju.tangshi.AppConstants;
import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.R;
import io.liamju.tangshi.adapter.CommonSimpleAdapter;
import io.liamju.tangshi.db.PoetryUtil;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/11/30
 */
public class AuthListFragment extends BaseListFragment<CommonSimpleAdapter> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected CommonSimpleAdapter getAdapter() {
        return new CommonSimpleAdapter(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String auth = mAdapter.getData().get(position);
                IntentStarter.showCommList(getActivity(), AppConstants.VALUE_COMM_TYPE_AUTH, auth);
            }
        }, PoetryUtil.getInstance(getContext()).getAuthors());
    }
}
