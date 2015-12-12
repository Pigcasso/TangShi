package io.liamju.tangshi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import io.liamju.tangshi.AppConstants;
import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.R;
import io.liamju.tangshi.adapter.FootprintAdapter;
import io.liamju.tangshi.db.PoetryUtil;
import io.liamju.tangshi.entity.Footprint;

/**
 * 历史记录列表界面
 *
 * @author LiamJu
 * @version 1.0
 * @since 15/12/6
 */
public class CollectionFragment extends BaseListFragment<FootprintAdapter> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.label_my_collection);
    }

    @Override
    protected FootprintAdapter getAdapter() {
        List<Footprint> footprintList
                = PoetryUtil.getInstance(getContext()).getAllCollectionList();
        return new FootprintAdapter(footprintList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String title = mAdapter.getData().get(position).getTitle();
                IntentStarter.showPoetryDetailForResult(CollectionFragment.this,
                        PoetryUtil.getInstance(getContext()).getPoetryByTitle(title));
            }
        }, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == AppConstants.REQUEST_CODE_DETAIL) {
            mAdapter.setData(PoetryUtil.getInstance(getContext()).getAllCollectionList());
            return;
        }
    }
}
