package io.liamju.tangshi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.liamju.tangshi.R;
import io.liamju.tangshi.activity.BaseActivity;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/11/30
 */
public abstract class BaseFragment extends Fragment {

    @InjectView(R.id.layout_container)
    View container;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBackground();
    }

    protected void initBackground() {
        container.setBackgroundResource(R.color.white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract int getLayoutResId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    protected void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        if (getActivity() instanceof BaseActivity) {
            setHasOptionsMenu(true);
            ((BaseActivity) getActivity())
                    .getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    protected void setTitle(int titleId) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle(titleId);
        }
    }

    protected void setTitle(String title) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
    }
}
