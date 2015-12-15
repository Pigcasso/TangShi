package io.liamju.tangshi.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.InjectView;
import io.liamju.tangshi.IntentStarter;
import io.liamju.tangshi.R;
import io.liamju.tangshi.adapter.BaseAdapter;
import io.liamju.tangshi.adapter.SearchableAdapter;
import io.liamju.tangshi.db.PoetryUtil;
import io.liamju.tangshi.entity.Poetry;
import io.liamju.tangshi.utils.L;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/10
 */
public class SearchableActivity extends BaseListActivity<SearchableAdapter> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected SearchableAdapter getAdapter() {
        return new SearchableAdapter(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Poetry poetry = mAdapter.getData().get(position);
                IntentStarter.showPoetryDetail(SearchableActivity.this, poetry);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_poetry, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.menu_search_poetry));

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                List<Poetry> result = PoetryUtil.getInstance(SearchableActivity.this)
//                        .searchPoetry(query);
//                mAdapter.setData(result);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Poetry> result = PoetryUtil.getInstance(SearchableActivity.this)
                        .searchPoetry(newText);
                mAdapter.setData(result);
                return true;

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
