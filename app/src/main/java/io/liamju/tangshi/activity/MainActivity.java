package io.liamju.tangshi.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import butterknife.InjectView;
import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.R;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.tab_host)
    FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabs();
    }

    /**
     * 1.1暂时不添加搜索功能
     *
     * @return 返回布局资源id
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            IntentStarter.searchPoetry(this);
        }
        return true;
    }

    private void initTabs() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);
        if (Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        MainTab[] tabs = MainTab.values();
        for (MainTab tab : tabs) {
            TabSpec spec = mTabHost.newTabSpec(getString(tab.getTabNameRes()));
            View indicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.text_tab_title);
            Drawable drawable = this.getResources().getDrawable(tab.getTabIconRes());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            title.setText(tab.getTabNameRes());
            spec.setIndicator(indicator);
            spec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(spec, tab.getClz(), null);
        }
        mTabHost.setCurrentTab(0);
    }
}
