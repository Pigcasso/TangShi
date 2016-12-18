package io.liamju.tangshi.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import io.liamju.tangshi.AppConstants;
import io.liamju.tangshi.R;
import io.liamju.tangshi.db.PoetryUtil;
import io.liamju.tangshi.entity.Poetry;


public class PoetryDetailsFragment extends BaseFragment {
    private static final String TAG = PoetryDetailsFragment.class.getSimpleName();

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;
    private int mCurrentIndex;
    private ArrayList<Poetry> mPoetryList;

    private CheckBox mCheckableStar;

    public PoetryDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(AppConstants.KEY_POETRY_CURRENT_INDEX);
            mPoetryList = savedInstanceState.getParcelableArrayList(AppConstants.KEY_POETRY_DETAIL_LIST);
        } else {
            if (getArguments() != null) {
                mCurrentIndex = getArguments().getInt(AppConstants.KEY_POETRY_CURRENT_INDEX);
                mPoetryList = getArguments().getParcelableArrayList(AppConstants.KEY_POETRY_DETAIL_LIST);
            } else {
                throw new IllegalStateException("必须有参数");
            }
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager.setAdapter(new Adapter(getFragmentManager(), mPoetryList));
        mViewPager.setCurrentItem(mCurrentIndex, false);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mCurrentIndex = position;
                Poetry poetry = mPoetryList.get(position);
                boolean isChecked = PoetryUtil.getInstance(getActivity())
                        .isCollected(poetry.getTitle());
                Log.d(TAG, "onPageSelected: " + poetry.getTitle() + "," + isChecked);
                mCheckableStar.setChecked(isChecked);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_poetry_detail, menu);
        MenuItem item = menu.findItem(R.id.menu_item_star);
        mCheckableStar = (CheckBox) MenuItemCompat.getActionView(item)
                .findViewById(R.id.check_poetry_detail_star);
        mCheckableStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Poetry poetry = mPoetryList.get(mCurrentIndex);
                PoetryUtil.getInstance(getContext()).collectPoetry(poetry.getTitle(), mCurrentIndex);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_poetry_details;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(AppConstants.KEY_POETRY_CURRENT_INDEX, mCurrentIndex);
        outState.putParcelableArrayList(AppConstants.KEY_POETRY_DETAIL_LIST, mPoetryList);
    }

    private static class Adapter extends FragmentPagerAdapter {

        private List<Poetry> mPoetryList;

        private Adapter(FragmentManager fm, List<Poetry> poetryList) {
            super(fm);
            mPoetryList = poetryList;
        }

        @Override
        public Fragment getItem(int position) {
            return PoetryDetailFragment.newInstance(mPoetryList.get(position));
        }

        @Override
        public int getCount() {
            return mPoetryList.size();
        }
    }
}
