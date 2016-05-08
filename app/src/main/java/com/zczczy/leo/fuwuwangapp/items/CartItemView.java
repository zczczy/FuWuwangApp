package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CartActivity;
import com.zczczy.leo.fuwuwangapp.adapters.CartAdapter;
import com.zczczy.leo.fuwuwangapp.model.CartModel;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/27.
 */
@EViewGroup(R.layout.activity_cart_item)
public class CartItemView extends ItemView<CartModel> {

    @ViewById
    CheckBox cb_store_check;

    @ViewById
    TextView txt_store;

    Context context;

    CartActivity cartActivity;

    CartAdapter cartAdapter;

    public CartItemView(Context context) {
        super(context);
        this.context = context;
        cartActivity = (CartActivity) context;
    }

    @Click
    void cb_store_check() {
        _data.isChecked = cb_store_check.isChecked();
        _data.isStoreChecked=cb_store_check.isChecked();
        for (CartModel cm : cartAdapter.getItems()) {
            if (_data.StoreInfoId.equals(cm.StoreInfoId)) {
                cm.isChecked = _data.isChecked;
            }
        }
        cartAdapter.notifyDataSetChanged();
        cartActivity.setTotalMoney();
    }


    @Override
    protected void init(Object... objects) {
        cartAdapter = (CartAdapter) objects[0];
        txt_store.setText(_data.StoreName);
        cb_store_check.setChecked(_data.isChecked);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
