package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperViewHolder;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;

/**
 * Created by Leo on 2016/4/27.
 */
public abstract class ItemView<T> extends LinearLayout implements ItemTouchHelperViewHolder {

    protected T _data;

    protected BaseUltimateRecyclerViewAdapter<T> baseUltimateRecyclerViewAdapter;

    protected RecyclerView.ViewHolder viewHolder;

    protected Context context;


    public ItemView(Context context) {
        super(context);
        this.context = context;
    }

    public void init(T t, Object... objects) {
        this._data = t;
        init(objects);
    }

    public void init(T t, BaseUltimateRecyclerViewAdapter<T> baseUltimateRecyclerViewAdapter, RecyclerView.ViewHolder viewHolder, Object... objects) {
        this._data = t;
        this.baseUltimateRecyclerViewAdapter = baseUltimateRecyclerViewAdapter;
        this.viewHolder = viewHolder;
        init(objects);
    }


    protected abstract void init(Object... objects);

}

