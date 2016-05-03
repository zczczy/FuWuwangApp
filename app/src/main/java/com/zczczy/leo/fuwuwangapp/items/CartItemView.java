package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.CustomLinearLayoutManager;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CartDetailItemAdapter;
import com.zczczy.leo.fuwuwangapp.model.CartInfo;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/27.
 */
@EViewGroup(R.layout.activity_cart_item)
public class CartItemView extends ItemView<CartInfo> {

    @ViewById
    CheckBox cb_store_check;

    @ViewById
    TextView txt_store;

    Context context;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(CartDetailItemAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    LinearLayoutManager linearLayoutManager;

    public CartItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        ultimateRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new CustomLinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(myAdapter);
        txt_store.setText(_data.getStoreName());
        myAdapter.getMoreData(0, 0, false, _data.getBuyCartInfoList());
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
