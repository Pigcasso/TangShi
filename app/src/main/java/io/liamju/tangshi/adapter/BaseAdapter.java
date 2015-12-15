package io.liamju.tangshi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/1
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mValues;

    public BaseAdapter() {
        mValues = new ArrayList<>();
    }

    public BaseAdapter(List<T> mValues) {
        this.mValues = mValues;
    }

    public BaseAdapter(T[] values) {
        this.mValues = Arrays.asList(values);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setData(List<T> data) {
        mValues = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mValues;
    }

    public abstract static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
