package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.PreOrderActivity;
import com.zczczy.leo.fuwuwangapp.activities.PreOrderActivity_;
import com.zczczy.leo.fuwuwangapp.model.CheckOutModel;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

/**
 * Created by Leo on 2016/5/7.
 */
@EViewGroup(R.layout.activity_cart_buy_pop_item)
public class CartBuyPopItemView extends ItemView<CheckOutModel> {

    @ViewById
    TextView txt_store_name, txt_count, txt_rmb, txt_plus, txt_home_lb;

    @StringRes
    String text_count, home_rmb, home_lb;

    Context context;

    public CartBuyPopItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Click
    void btn_buy() {
        PreOrderActivity_.intent(context).isCart(true).BuyCartInfoIds(_data.BuyCartInfoIds).StoreInfoId(_data.StoreInfoId).start();
    }


    @Override
    protected void init(Object... objects) {
        txt_store_name.setText(_data.StoreName);
        txt_count.setText(String.format(text_count, _data.ProductCount));
        if (_data.lbTotal > 0 && _data.rmbTotal > 0) {
            txt_rmb.setVisibility(View.VISIBLE);
            txt_plus.setVisibility(View.VISIBLE);
            //设置商品总价
            txt_rmb.setText(String.format(home_rmb, _data.rmbTotal));
            //设置龙币数
            txt_home_lb.setText(String.format(home_lb, _data.lbTotal));
        } else if (_data.rmbTotal > 0) {
            txt_rmb.setVisibility(View.VISIBLE);
            txt_plus.setVisibility(View.GONE);
            txt_home_lb.setVisibility(View.GONE);
            txt_rmb.setText(String.format(home_rmb, _data.rmbTotal));
        } else if (_data.lbTotal > 0) {
            txt_rmb.setVisibility(View.GONE);
            txt_plus.setVisibility(View.GONE);
            txt_home_lb.setVisibility(View.VISIBLE);
            txt_home_lb.setText(String.format(home_lb, _data.lbTotal));
        }

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
