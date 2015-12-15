package io.liamju.tangshi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.InjectView;
import io.liamju.tangshi.R;
import io.liamju.tangshi.adapter.BaseAdapter;
import io.liamju.tangshi.view.DividerItemDecoration;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/15
 */
public abstract class BaseListActivity<A extends BaseAdapter> extends BaseActivity {

    protected A mAdapter;
    @InjectView(R.id.recycler_list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter = getAdapter());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_base_list;
    }

    protected abstract A getAdapter();

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
}
