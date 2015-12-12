package io.liamju.tangshi.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.OnClick;
import io.liamju.tangshi.AppConstants;
import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.R;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/7
 */
public class AboutFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_about);
        setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initBackground() {

    }

    @OnClick(R.id.relative_about_developer)
    public void showDeveloper() {
        IntentStarter.showAuthorIntroduce(getActivity(), "求关注", AppConstants.URL_DEVELOPER_HOMEPAGE);
    }

    @OnClick(R.id.relative_about_designer)
    public void showDesigner() {
        IntentStarter.showAuthorIntroduce(getActivity(), "站酷个人首页", AppConstants.URL_DESIGNER_HOMEPAGE);
    }
}
