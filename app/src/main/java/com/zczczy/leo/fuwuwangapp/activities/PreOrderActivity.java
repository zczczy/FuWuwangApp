package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
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
    LinearLayout ll_shipping, ll_store;

    @ViewById
    RelativeLayout rl_dian;

    @ViewById
    TextView tv_shipping, txt_phone, tv_shipping_address, txt_store, txt_express_charges,
            txt_dian_balance, txt_coupon, txt_sub_express_charges, txt_total_rmb, txt_total_lb;

    @StringRes
    String txt_shipping_address, dian_balance, home_rmb, home_lb, txt_shipping;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Pref
    MyPrefs_ pre;

    @Extra
    String goodsInfoId;

    @Extra
    int orderCount;

    String storeId;

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
            txt_coupon.setText(bmj.Data.RetTicketNum);
            txt_sub_express_charges.setText(String.format(home_rmb, bmj.Data.Postage));
            txt_total_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney));
            txt_total_lb.setText(String.format(home_lb, bmj.Data.MOrderLbCount));
            for (BuyCartInfoList buyCartInfoList : bmj.Data.BuyCartInfoList) {

            }

        } else {
            AndroidTool.showToast(this, bmj.Error);
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
