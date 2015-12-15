package io.liamju.tangshi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.InjectView;
import io.liamju.tangshi.R;
import io.liamju.tangshi.activity.BaseListActivity.OnItemClickListener;
import io.liamju.tangshi.entity.Poetry;


/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/15
 */
public class SearchableAdapter extends BaseAdapter<Poetry, SearchableAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;

    public SearchableAdapter(OnItemClickListener listener) {
        super();
        onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchable, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Poetry poetry = mValues.get(position);
        holder.title.setText(poetry.getTitle());
        holder.author.setText("-" + poetry.getAuth());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    public static class ViewHolder extends BaseAdapter.ViewHolder {
        View root;
        @InjectView(R.id.text_searchable_title)
        TextView title;
        @InjectView(R.id.text_searchable_author)
        TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
        }
    }
}
