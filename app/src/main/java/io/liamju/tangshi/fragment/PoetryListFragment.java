package io.liamju.tangshi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.brightyoyo.IndexBar;
import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.R;
import io.liamju.tangshi.adapter.PoetryAdapter;
import io.liamju.tangshi.db.PoetryUtil;
import io.liamju.tangshi.entity.Poetry;
import io.liamju.tangshi.utils.MobclickAgentHelper;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/1
 */
public class PoetryListFragment extends BaseListFragment<PoetryAdapter> {
    @InjectView(R.id.index_poetry_alphabet)
    IndexBar indexBar;
    @InjectView(R.id.text_poetry_sort)
    TextView textPoetrySort;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected PoetryAdapter getAdapter() {
        return new PoetryAdapter(PoetryUtil.getInstance(getContext()).getAllPoetry(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Poetry poetry = mAdapter.getData().get(position);
                        MobclickAgentHelper.getInstance().onClickPoetryListItem(getContext(), poetry);
                        IntentStarter.showPoetryDetail(view.getContext(), poetry);
                    }
                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        indexBar.setSections(PoetryUtil.getInstance(getContext()).getSections());
        indexBar.setIndexBarFilter(new IndexBar.IIndexBarFilter() {
            @Override
            public void filterList(float sideIndexY, int position, String previewText) {
                if (previewText == null/*手指抬起*/) {
                    textPoetrySort.setVisibility(View.GONE);
                } else {
                    textPoetrySort.setText(previewText);
                    textPoetrySort.setVisibility(View.VISIBLE);
                    int pos = PoetryUtil.getInstance(getContext()).getPosition(previewText.charAt(0));
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(pos, 0);
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_poetry_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
