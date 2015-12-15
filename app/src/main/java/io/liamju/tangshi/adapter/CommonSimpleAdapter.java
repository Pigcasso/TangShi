package io.liamju.tangshi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.InjectView;
import io.liamju.tangshi.R;
import io.liamju.tangshi.fragment.BaseListFragment.OnItemClickListener;

/**
 * 一个简单的适配器，仅仅用来展示String类型的数据。
 *
 * @author LiamJu
 * @version 1.0
 * @since 15/12/4
 */
public class CommonSimpleAdapter extends BaseAdapter<String, CommonSimpleAdapter.ViewHolder> {
    private OnItemClickListener mListener;

    public CommonSimpleAdapter(OnItemClickListener listener, String[] values) {
        super(values);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_base_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(mValues.get(position));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position);
                }
            }
        });
    }

    public static class ViewHolder extends BaseAdapter.ViewHolder {
        @InjectView(R.id.text_item_title)
        TextView title;
        private View root;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
        }
    }
}
