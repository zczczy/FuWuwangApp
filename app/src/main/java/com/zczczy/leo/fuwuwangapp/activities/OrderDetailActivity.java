package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.items.PreOrderItemView;
import com.zczczy.leo.fuwuwangapp.items.PreOrderItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;
import com.zczczy.leo.fuwuwangapp.model.MAppOrder;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/6.
 */
@EActivity(R.layout.activity_order_detail)
public class OrderDetailActivity extends BaseActivity {

    @ViewById
    TextView tv_shipping, txt_phone, tv_shipping_address, txt_store,
            txt_order_no, txt_coupon, txt_sub_express_charges, txt_total_lb, txt_pay_total_rmb;

    @ViewById
    LinearLayout ll_logistics, ll_store, ll_pre_order_item;

    @ViewById
    RelativeLayout ll_lb, ll_pay;

    @StringRes
    String text_order_no, txt_shipping_address, dian_balance, home_rmb, home_lb, txt_shipping, text_count;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Pref
    MyPrefs_ pre;

    @Extra
    String orderId;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        getOrderDetailById();
    }

    @Background
    void getOrderDetailById() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        afterGetOrderDetailById(myRestClient.getOrderDetailById(orderId));
    }

    @UiThread
    void afterGetOrderDetailById(BaseModelJson<MAppOrder> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
        } else {
            txt_order_no.setText(bmj.Data.MOrderNo);
            tv_shipping.setText(String.format(txt_shipping, bmj.Data.ShrName));
            txt_phone.setText(bmj.Data.Lxdh);
            tv_shipping_address.setText(String.format(txt_shipping_address, bmj.Data.DetailAddress));
            txt_store.setText(bmj.Data.StoreName);
            txt_sub_express_charges.setText(String.format(home_rmb, bmj.Data.Postage));
            txt_pay_total_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney));
            txt_total_lb.setText(String.format(home_lb, bmj.Data.MOrderLbCount));
            int i = 0;
            for (OrderDetailModel orderDetailModel : bmj.Data.MOrderDetailList) {
                BuyCartInfoList buyCartInfoList = new BuyCartInfoList();
                buyCartInfoList.GoodsLBPrice = orderDetailModel.MOrderDetailLbCount == null ? 0 : Integer.valueOf(orderDetailModel.MOrderDetailLbCount);
                buyCartInfoList.GoodsPrice = orderDetailModel.MOrderDetailPrice == null ? 0.00 : Double.valueOf(orderDetailModel.MOrderDetailPrice);
                buyCartInfoList.GoodsImgSl = orderDetailModel.GoodsImgSl;
                buyCartInfoList.GodosName = orderDetailModel.ProductName;
                buyCartInfoList.ProductCount = orderDetailModel.ProductNum == null ? 0 : Integer.valueOf(orderDetailModel.ProductNum);
                PreOrderItemView preOrderItemView = PreOrderItemView_.build(this);
                preOrderItemView.init(buyCartInfoList);
                ll_pre_order_item.addView(preOrderItemView, i);
            }
            //设置返券内容
            String temp = "";
            if (bmj.Data.FanQuan != null) {
                if (bmj.Data.FanQuan.RetMianZhi7 > 0) {
                    temp += bmj.Data.FanQuan.RetMianZhi7Str + "*" + bmj.Data.FanQuan.RetMianZhi7 + "+";
                }
                if (bmj.Data.FanQuan.RetMianZhi8 > 0) {
                    temp += bmj.Data.FanQuan.RetMianZhi8Str + "*" + bmj.Data.FanQuan.RetMianZhi8 + "+";
                }
                if (bmj.Data.FanQuan.RetMianZhi10 > 0) {
                    temp += bmj.Data.FanQuan.RetMianZhi10Str + "*" + bmj.Data.FanQuan.RetMianZhi10 + "+";
                }
                if (bmj.Data.FanQuan.RetMianZhi9 > 0) {
                    temp += bmj.Data.FanQuan.RetMianZhi9Str + "*" + bmj.Data.FanQuan.RetMianZhi9 + "+";
                }
                if (StringUtils.isEmpty(temp)) {
                    temp = "不返券+";
                }
            } else {
                temp = "不返券+";
            }
            txt_coupon.setText(temp.substring(0, temp.lastIndexOf('+')));
        }
    }

}
