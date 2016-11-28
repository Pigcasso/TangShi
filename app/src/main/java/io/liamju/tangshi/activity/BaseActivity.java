package io.liamju.tangshi.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.liamju.tangshi.utils.MobclickAgentHelper;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/11/30
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutResId());
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgentHelper.getInstance().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgentHelper.getInstance().onPause(this);
    }

    protected abstract int getLayoutResId();
}
