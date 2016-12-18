package io.liamju.tangshi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import butterknife.InjectView;
import io.liamju.tangshi.AppConstants;
import io.liamju.tangshi.R;
import io.liamju.tangshi.db.PoetryUtil;
import io.liamju.tangshi.entity.Poetry;
import io.liamju.tangshi.utils.FullDateManager;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/2
 */
public class PoetryDetailFragment extends BaseFragment {

    @InjectView(R.id.text_poetry_detail_title)
    TextView detailTitle;
    @InjectView(R.id.text_poetry_detail_auth)
    TextView detailAuth;
    @InjectView(R.id.text_poetry_detail_content)
    TextView detailContent;
    @InjectView(R.id.text_poetry_detail_desc)
    TextView detailDesc;
    private boolean isCollected;
    // 是否点击过“收藏按钮”
    private Poetry poetry;

    public static PoetryDetailFragment newInstance(Poetry poetry) {

        Bundle args = new Bundle();
        args.putParcelable(AppConstants.KEY_POETRY_DETAIL, poetry);
        PoetryDetailFragment fragment = new PoetryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            poetry = savedInstanceState.getParcelable(AppConstants.KEY_POETRY_DETAIL);
        } else {
            if (getArguments() != null) {
                poetry = getArguments().getParcelable(AppConstants.KEY_POETRY_DETAIL);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailTitle.setText(Html.fromHtml(poetry.getTitle()));
        detailAuth.setText(Html.fromHtml(poetry.getAuth()));
        detailContent.setText(Html.fromHtml(poetry.getContent()));
        detailDesc.setText(Html.fromHtml(poetry.getDesc()));

        if (savedInstanceState != null) {
            isCollected = savedInstanceState.getBoolean(AppConstants.KEY_IS_COLLECTED);
        } else {
            isCollected = PoetryUtil.getInstance(getActivity()).isCollected(poetry.getTitle());
            PoetryUtil.getInstance(getActivity()).addHistoryRecord(poetry.getTitle(), FullDateManager.getFullDateMills());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(AppConstants.KEY_POETRY_DETAIL, poetry);
        outState.putBoolean(AppConstants.KEY_IS_COLLECTED, isCollected);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_poetry_detail;
    }
}
