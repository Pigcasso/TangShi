package io.liamju.tangshi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.InjectView;
import io.liamju.tangshi.R;
import io.liamju.tangshi.entity.Poetry;
import io.liamju.tangshi.fragment.BaseListFragment.OnItemClickListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Poetry} and makes a call to the
 * specified {@link OnItemClickListener}.
 */
public class PoetryAdapter extends BaseAdapter<Poetry, PoetryAdapter.ViewHolder> {

    private OnItemClickListener mListener;
    public PoetryAdapter(List<Poetry> items, OnItemClickListener listener) {
        super(items);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poetry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClick(holder.root, position);
                }
            }
        });
    }

    public static class ViewHolder extends BaseAdapter.ViewHolder {

        public Poetry mItem;
        View root;

        @InjectView(R.id.text_item_poetry_title)
        TextView title;

        public ViewHolder(View view) {
            super(view);
            root = view;
        }
    }
}
