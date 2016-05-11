package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.LogisticsInfoActivity_;
import com.zczczy.leo.fuwuwangapp.activities.OrderDetailActivity_;
import com.zczczy.leo.fuwuwangapp.activities.StoreInformationActivity_;
import com.zczczy.leo.fuwuwangapp.activities.UmspayActivity_;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;
import com.zczczy.leo.fuwuwangapp.model.MAppOrder;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;
import com.zczczy.leo.fuwuwangapp.model.UnionPay;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.HashMap;
import java.util.Map;

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

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Pref
    MyPrefs_ pre;

    @StringRes
    String no_net;


    Context context;

    public MemberOrderItemView(Context context) {
        super(context);
        this.context = context;
    }


    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }


    @Click
    void btn_canceled() {
        OrderDetailActivity_.intent(context).orderId(_data.MOrderId).start();
    }

    @Click
    void btn_finish() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle("提示").setMessage("确认收货？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AndroidTool.showLoadDialog(context);
                confirmReceipt();
            }
        }).setNegativeButton("取消", null).setIcon(R.mipmap.logo).create().show();
    }

    @Background
    void confirmReceipt() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        Map<String, String> map = new HashMap<>(1);
        map.put("MOrderId", _data.MOrderId);
        afterConfirm(myRestClient.confirmReceipt(map));
    }

    @UiThread
    void afterConfirm(BaseModel bm) {
        AndroidTool.dismissLoadDialog();
        if (bm == null) {
            AndroidTool.showToast(context, no_net);
        } else if (!bm.Successful) {
            AndroidTool.showToast(context, bm.Error);
        } else {
            btn_finish.setVisibility(GONE);
            btn_finished.setVisibility(VISIBLE);
        }
    }


    @Click
    void btn_cancel_order() {

    }

    @Click
    void btn_logistics() {
        LogisticsInfoActivity_.intent(context).MOrderId(_data.MOrderId).start();
    }

    @Click
    void btn_pay() {
        UnionPay order = new UnionPay();
        order.ChrCode = _data.chrCode;
        order.MerSign = _data.merSign;
        order.TransId = _data.transId;
        UmspayActivity_.intent(context).MOrderId(_data.MOrderId).order(order).start();
    }

    @Click
    void btn_finished() {

    }


    @Override
    protected void init(Object... objects) {
        txt_store.setText(_data.StoreName);
        //设置商品总数
        txt_count.setText(String.format(text_count, _data.GoodsAllCount));
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
        //先清空所以布局
        ll_pre_order_item.removeAllViews();
        for (OrderDetailModel orderDetailModel : _data.MOrderDetailList) {
            BuyCartInfoList buyCartInfoList = new BuyCartInfoList();
            buyCartInfoList.GoodsImgSl = orderDetailModel.GoodsImgSl;
            buyCartInfoList.GodosName = orderDetailModel.ProductName;
            buyCartInfoList.GoodsPrice = Double.valueOf(orderDetailModel.MOrderDetailPrice);
            buyCartInfoList.GoodsLBPrice = Integer.valueOf(orderDetailModel.MOrderDetailLbCount == null ? "0" : orderDetailModel.MOrderDetailLbCount);
            buyCartInfoList.ProductCount = Integer.valueOf(orderDetailModel.ProductNum);
            PreOrderItemView preOrderItemView = PreOrderItemView_.build(context);
            preOrderItemView.init(buyCartInfoList);
            ll_pre_order_item.addView(preOrderItemView);
        }
        if (_data.MorderStatus == MyApplication.DUEPAYMENT) {
            txt_do_message.setVisibility(VISIBLE);
            txt_do_message.setText("等待买家付款");
            btn_cancel_order.setVisibility(GONE);
            btn_pay.setVisibility(VISIBLE);
            btn_logistics.setVisibility(GONE);
            btn_finish.setVisibility(GONE);
            btn_canceled.setVisibility(GONE);
        } else if (_data.MorderStatus == MyApplication.PAID) {
            txt_do_message.setVisibility(VISIBLE);
            txt_do_message.setText("商家处理量中");
            btn_logistics.setVisibility(VISIBLE);
            btn_finish.setVisibility(GONE);
            btn_cancel_order.setVisibility(GONE);
            btn_pay.setVisibility(GONE);
            btn_canceled.setVisibility(GONE);
        } else if (_data.MorderStatus == MyApplication.CANCEL) {
            txt_do_message.setVisibility(VISIBLE);
            txt_do_message.setText("订单已取消");
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
            txt_do_message.setVisibility(VISIBLE);
            txt_do_message.setText("等待买家确认");
            btn_logistics.setVisibility(View.VISIBLE);
            btn_finish.setVisibility(View.GONE);
            btn_cancel_order.setVisibility(View.GONE);
            btn_pay.setVisibility(View.GONE);
            btn_canceled.setVisibility(View.GONE);
        } else if (_data.MorderStatus == MyApplication.FINISH) {
            txt_do_message.setVisibility(VISIBLE);
            txt_do_message.setText("已完成");
            btn_logistics.setVisibility(View.VISIBLE);
            btn_finished.setVisibility(View.VISIBLE);
            btn_finish.setVisibility(View.GONE);
            btn_cancel_order.setVisibility(View.GONE);
            btn_pay.setVisibility(View.GONE);
            btn_canceled.setVisibility(View.GONE);
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
