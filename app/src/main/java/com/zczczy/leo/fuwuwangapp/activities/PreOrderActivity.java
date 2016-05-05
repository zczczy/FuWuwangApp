package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.items.PreOrderItemView;
import com.zczczy.leo.fuwuwangapp.items.PreOrderItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;
import com.zczczy.leo.fuwuwangapp.model.ConfirmOrderModel;
import com.zczczy.leo.fuwuwangapp.model.MReceiptAddressModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/5/4.
 */
@EActivity(R.layout.activity_pre_order)
public class PreOrderActivity extends BaseActivity {

    @ViewById
    LinearLayout ll_shipping, ll_store, ll_pre_order_item;

    @ViewById
    RelativeLayout rl_dian, ll_lb, ll_pay;

    @ViewById
    TextView tv_shipping, txt_phone, tv_shipping_address, txt_store, txt_express_charges,
            txt_dian_balance, txt_coupon, txt_sub_express_charges, txt_pay_total_rmb, txt_total_lb,
            txt_dian_quantity, txt_rmb, txt_plus, txt_home_lb, txt_count;

    @StringRes
    String txt_shipping_address, dian_balance, home_rmb, home_lb, txt_shipping, text_count;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Pref
    MyPrefs_ pre;

    @ViewById
    CheckBox use_dian;

    @Extra
    String goodsInfoId;

    @Extra
    int orderCount;

    //电子币余额
    double balance = 0;

    //输入的电子币数
    double useDianZiBi = 0.0;

    String storeId;

    int sellerType = 0;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        createTempOrderInfo();
    }

    @Background
    void createTempOrderInfo() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        afterCreateTempOrderInfo(myRestClient.createTempGoodsOrderInfo(goodsInfoId, orderCount));
    }

    @UiThread
    void afterCreateTempOrderInfo(BaseModelJson<ConfirmOrderModel> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            setShipping(bmj.Data.MReceiptAddress);
            txt_store.setText(bmj.Data.StoreName);
            storeId = bmj.Data.StoreInfoId;
            txt_express_charges.setText(String.format(home_rmb, bmj.Data.Postage));
            txt_dian_balance.setText(String.format(dian_balance, bmj.Data.MaxDzb));
//            txt_coupon.setText(bmj.Data.RetTicketNum);
            txt_sub_express_charges.setText(String.format(home_rmb, bmj.Data.Postage));
            txt_pay_total_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney));
            txt_total_lb.setText(String.format(home_lb, bmj.Data.MOrderLbCount));
            for (BuyCartInfoList buyCartInfoList : bmj.Data.BuyCartInfoList) {
                PreOrderItemView preOrderItemView = PreOrderItemView_.build(this);
                preOrderItemView.init(buyCartInfoList);
                ll_pre_order_item.addView(preOrderItemView);
            }
            sellerType = bmj.Data.SellerType;

            if (bmj.Data.MOrderMoney > 0 && bmj.Data.MOrderLbCount > 0) {
                ll_lb.setVisibility(View.VISIBLE);
                ll_pay.setVisibility(View.VISIBLE);
                txt_rmb.setVisibility(View.VISIBLE);
                txt_plus.setVisibility(View.VISIBLE);
                txt_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney));
                txt_home_lb.setText(String.format(home_lb, bmj.Data.MOrderLbCount));
            } else if (bmj.Data.MOrderMoney > 0) {
                ll_lb.setVisibility(View.GONE);
                ll_pay.setVisibility(View.VISIBLE);
                txt_rmb.setVisibility(View.VISIBLE);
                txt_plus.setVisibility(View.GONE);
                txt_home_lb.setVisibility(View.GONE);
                txt_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney));
            } else if (bmj.Data.MOrderLbCount > 0) {
                ll_pay.setVisibility(View.GONE);
                ll_lb.setVisibility(View.VISIBLE);
                txt_rmb.setVisibility(View.GONE);
                txt_plus.setVisibility(View.GONE);
                txt_home_lb.setVisibility(View.VISIBLE);
                txt_home_lb.setText(String.format(home_lb, bmj.Data.MOrderLbCount));
            }

            txt_count.setText(String.format(text_count, bmj.Data.GoodsAllCount));

        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    @CheckedChange
    void use_dian(Boolean checked) {
        if (sellerType != 1) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("提示").setMessage("该商家不支持电子币支付").setNegativeButton("取消", null).setIcon(R.mipmap.logo).create().show();
        } else {
            txt_dian_quantity.setClickable(checked);
            txt_dian_quantity.setText(checked ? useDianZiBi + "" : "0.0");
        }
    }


    //设置 收货地址
    void setShipping(MReceiptAddressModel model) {
        if (model != null) {
            tv_shipping.setText(String.format(txt_shipping, model.ReceiptName));
            txt_phone.setText(model.Mobile);
            tv_shipping_address.setText(String.format(txt_shipping_address, model.ProvinceName + model.CityName + model.AreaName + model.DetailAddress));
        } else {
            tv_shipping.setText("!填写收货地址");
        }
    }

    @Click
    void ll_shipping() {
        ShippingAddressActivity_.intent(this).startForResult(1000);
    }

    @OnActivityResult(1000)
    void onSelectShippingAddress(int reault, @OnActivityResult.Extra MReceiptAddressModel model) {
        setShipping(model);
    }

    @Click
    void ll_store() {

    }

}
