package io.liamju.tangshi.fragment;

import android.os.Bundle;
import android.view.View;

import java.util.List;

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
public class HistoryFragment extends BaseListFragment<FootprintAdapter> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.label_history_record);
    }

    @Override
    protected FootprintAdapter getAdapter() {
        List<Footprint> footprintList
                = PoetryUtil.getInstance(getContext()).getAllHistoryList();
        return new FootprintAdapter(footprintList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String title = mAdapter.getData().get(position).getTitle();
                IntentStarter.showPoetryDetail(getContext(),
                        PoetryUtil.getInstance(getContext()).getPoetryByTitle(title));
            }
        }, null);
    }
}
