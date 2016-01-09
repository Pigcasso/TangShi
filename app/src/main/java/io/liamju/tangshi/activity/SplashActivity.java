package io.liamju.tangshi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.R;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/10
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentStarter.showMain(SplashActivity.this);
                finish();
            }
        },2000L);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }
}
