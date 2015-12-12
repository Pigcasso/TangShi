package io.liamju.tangshi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.InjectView;
import io.liamju.tangshi.R;
import io.liamju.tangshi.entity.Footprint;
import io.liamju.tangshi.fragment.BaseListFragment.OnItemClickListener;
import io.liamju.tangshi.fragment.BaseListFragment.OnItemLongClickListener;
import io.liamju.tangshi.utils.L;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/6
 */
public class FootprintAdapter extends BaseAdapter<Footprint, FootprintAdapter.ViewHolder> {

    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    public FootprintAdapter(List<Footprint> mValues, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        super(mValues);
        this.onItemLongClickListener = onItemLongClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footprint, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Footprint footprint = mValues.get(position);
        holder.title.setText(footprint.getTitle());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    L.d("点击");
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
        holder.root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (onItemLongClickListener != null) {
                    L.d("长按");
                    return onItemLongClickListener.onItemLongClick(v, position);
                }
                return false;
            }
        });
    }

    public static class ViewHolder extends BaseAdapter.ViewHolder {
        @InjectView(R.id.linear_item_footprint_root)
        View root;
        @InjectView(R.id.text_item_footprint_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
