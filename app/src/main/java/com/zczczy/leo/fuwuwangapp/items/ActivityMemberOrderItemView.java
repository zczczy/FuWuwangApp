package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.StoreInformationActivity_;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;
import com.zczczy.leo.fuwuwangapp.model.MAppOrder;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

/**
 * Created by leo on 2016/5/6.
 */
@EViewGroup(R.layout.activity_member_order_item)
public class ActivityMemberOrderItemView extends ItemView<MAppOrder> {


    @ViewById
    LinearLayout ll_pre_order_item;

    @ViewById
    TextView txt_store, txt_count, txt_rmb, txt_plus, txt_home_lb;

    @StringRes
    String home_rmb, home_lb, text_count;

    @ViewById
    Button btn_cancel_order, btn_logistics, btn_pay, btn_finish;

    Context context;

    public ActivityMemberOrderItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        txt_store.setText(_data.StoreName);
        //设置商品总数
//        txt_count.setText(String.format(text_count, _data.));
        //设置费用
        if (_data.MOrderMoney > 0 && _data.MOrderLbCount > 0) {
            txt_rmb.setVisibility(View.VISIBLE);
            txt_plus.setVisibility(View.VISIBLE);
            //设置商品总价
            txt_rmb.setText(String.format(home_rmb, _data.MOrderMoney));
            //设置龙币数
            txt_home_lb.setText(String.format(home_lb, _data.MOrderLbCount));
        } else if (_data.MOrderMoney > 0) {
            txt_rmb.setVisibility(View.VISIBLE);
            txt_plus.setVisibility(View.GONE);
            txt_home_lb.setVisibility(View.GONE);
            txt_rmb.setText(String.format(home_rmb, _data.MOrderMoney));
        } else if (_data.MOrderLbCount > 0) {
            txt_rmb.setVisibility(View.GONE);
            txt_plus.setVisibility(View.GONE);
            txt_home_lb.setVisibility(View.VISIBLE);
            txt_home_lb.setText(String.format(home_lb, _data.MOrderLbCount));
        }
        for (OrderDetailModel orderDetailModel : _data.MOrderDetailList) {
            BuyCartInfoList buyCartInfoList = new BuyCartInfoList();
            buyCartInfoList.GoodsImgSl = orderDetailModel.GoodsImgSl;
            buyCartInfoList.GodosName = orderDetailModel.ProductName;
            buyCartInfoList.GoodsPrice = Double.valueOf(orderDetailModel.ProductPrice);
            //buyCartInfoList.GoodsLBPrice=orderDetailModel.
            buyCartInfoList.ProductCount = Integer.valueOf(orderDetailModel.ProductNum);
            PreOrderItemView preOrderItemView = PreOrderItemView_.build(context);
            preOrderItemView.init(buyCartInfoList);
            ll_pre_order_item.addView(preOrderItemView);
        }
    }

    @Click
    void ll_store() {
        StoreInformationActivity_.intent(context).storeId(_data.StoreInfoId).start();
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
