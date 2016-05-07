package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
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
public class MemberOrderItemView extends ItemView<MAppOrder> {


    @ViewById
    LinearLayout ll_pre_order_item;

    @ViewById
    TextView txt_store, txt_count, txt_rmb, txt_plus, txt_home_lb, txt_do_message;

    @StringRes
    String home_rmb, home_lb, text_count;

    @ViewById
    Button btn_canceled, btn_cancel_order, btn_logistics, btn_pay, btn_finish, btn_finished;

    Context context;

    public MemberOrderItemView(Context context) {
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
        if (_data.MorderStatus == MyApplication.DUEPAYMENT) {
            txt_do_message.setVisibility(VISIBLE);
            txt_do_message.setText("等待买家付款");
            btn_cancel_order.setVisibility(VISIBLE);
            btn_pay.setVisibility(VISIBLE);
            btn_logistics.setVisibility(GONE);
            btn_finish.setVisibility(GONE);
            btn_canceled.setVisibility(GONE);
        } else if (_data.MorderStatus == MyApplication.PAID) {
            txt_do_message.setVisibility(VISIBLE);
            txt_do_message.setText("商家处理量中");
            btn_logistics.setVisibility(VISIBLE);
            btn_finish.setVisibility(VISIBLE);
            btn_cancel_order.setVisibility(GONE);
            btn_pay.setVisibility(GONE);
            btn_canceled.setVisibility(GONE);
        } else if (_data.MorderStatus == MyApplication.CANCEL) {
            txt_do_message.setVisibility(GONE);
            btn_canceled.setVisibility(VISIBLE);
            btn_logistics.setVisibility(GONE);
            btn_finish.setVisibility(GONE);
            btn_cancel_order.setVisibility(GONE);
            btn_pay.setVisibility(GONE);
        } else if (_data.MorderStatus == MyApplication.SEND) {
            txt_do_message.setVisibility(VISIBLE);
            txt_do_message.setText("卖家已发货");
            btn_logistics.setVisibility(VISIBLE);
            btn_finish.setVisibility(VISIBLE);
            btn_cancel_order.setVisibility(GONE);
            btn_pay.setVisibility(GONE);
            btn_canceled.setVisibility(GONE);
        } else if (_data.MorderStatus == MyApplication.CONFIRM) {

        } else if (_data.MorderStatus == MyApplication.FINISH) {

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
