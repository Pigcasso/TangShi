package io.liamju.tangshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
public class PoetryDetailActivity extends BaseActivity {

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
    private boolean isChanged;
    private Poetry poetry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent == null) return;
        poetry = (Poetry) intent.getSerializableExtra(AppConstants.KEY_POETRY_DETAIL);
        detailTitle.setText(Html.fromHtml(poetry.getTitle()));
        detailAuth.setText(Html.fromHtml(poetry.getAuth()));
        detailContent.setText(Html.fromHtml(poetry.getContent()));
        detailDesc.setText(Html.fromHtml(poetry.getDesc()));

        if (savedInstanceState != null) {
            isCollected = savedInstanceState.getBoolean(AppConstants.KEY_IS_COLLECTED);
        } else {
            isCollected = PoetryUtil.getInstance(this).isCollected(poetry.getTitle());
            PoetryUtil.getInstance(this).addHistoryRecord(poetry.getTitle(), FullDateManager.getFullDateMills());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstants.KEY_IS_COLLECTED, isCollected);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_poetry_detail;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_poetry_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_item_star);
        CheckBox checkBox = (CheckBox) MenuItemCompat.getActionView(item)
                .findViewById(R.id.check_poetry_detail_star);
        checkBox.setChecked(isCollected);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCollected = isChecked;
                isChanged = true;
                if (isCollected) {
                    PoetryUtil.getInstance(PoetryDetailActivity.this)
                            .collectPoetry(poetry.getTitle(), FullDateManager.getFullDateMills());
                } else {
                    PoetryUtil.getInstance(PoetryDetailActivity.this)
                            .cancelCollectPoetry(poetry.getTitle());
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getExtras().containsKey(AppConstants.KEY_FOOTPRINT_TYPE)
                && isChanged) {
            setResult(RESULT_OK);
        }
        finish();
    }
}
