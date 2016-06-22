package com.zczczy.leo.fuwuwangapp.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.items.PreOrderItemView;
import com.zczczy.leo.fuwuwangapp.items.PreOrderItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;
import com.zczczy.leo.fuwuwangapp.model.MAppOrder;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;
import com.zczczy.leo.fuwuwangapp.model.PayResult;
import com.zczczy.leo.fuwuwangapp.model.UnionPay;
import com.zczczy.leo.fuwuwangapp.rest.MyBackgroundTask;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

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
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leo on 2016/5/6.
 */
@EActivity(R.layout.activity_order_detail)
public class OrderDetailActivity extends BaseActivity {

    @ViewById
    TextView tv_shipping, txt_phone, tv_shipping_address, txt_store,
            txt_order_no, txt_coupon, txt_sub_express_charges, txt_total_lb, txt_pay_total_rmb,
            txt_should_pay_l_rmb, txt_paid_rmb;

    @ViewById
    LinearLayout ll_take, ll_logistics, ll_store, ll_pre_order_item, ll_shipping, ll_next;

    @ViewById
    RelativeLayout ll_lb, ll_pay, ll_should_pay, ll_paid, rl_express_charges;

    @ViewById
    Button btn_canceled, btn_cancel_order, btn_logistics, btn_pay, btn_finish, btn_finished;

    @StringRes
    String text_order_no, txt_shipping_address, dian_balance, home_rmb, home_lb, txt_shipping, text_count;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Bean
    MyBackgroundTask myBackgroundTask;

    @Bean
    OttoBus bus;

    @Extra
    String orderId;

    MAppOrder mAppOrder;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        bus.register(this);
        AndroidTool.showLoadDialog(this);
        ll_next.setVisibility(View.GONE);
        getOrderDetailById();
    }

    @Background
    void getOrderDetailById() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
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
            mAppOrder = bmj.Data;
            txt_order_no.setText(String.format(text_order_no, "  " + bmj.Data.MOrderNo));
            tv_shipping.setText(String.format(txt_shipping, bmj.Data.ShrName));
            txt_phone.setText(bmj.Data.Lxdh);
            tv_shipping_address.setText(String.format(txt_shipping_address, bmj.Data.DetailAddress));
            txt_store.setText(bmj.Data.StoreName);
            txt_sub_express_charges.setText(String.format(home_rmb, bmj.Data.Postage));
            txt_pay_total_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney));
            txt_total_lb.setText(String.format(home_lb, bmj.Data.MOrderLbCount));
            int i = 0;
            for (OrderDetailModel orderDetailModel : bmj.Data.MOrderDetailList) {
                final BuyCartInfoList buyCartInfoList = new BuyCartInfoList();
                buyCartInfoList.GoodsLBPrice = orderDetailModel.MOrderDetailLbCount == null ? 0 : Integer.valueOf(orderDetailModel.MOrderDetailLbCount);
                buyCartInfoList.GoodsPrice = orderDetailModel.ProductPrice == null ? 0.00 : Double.valueOf(orderDetailModel.ProductPrice);
                buyCartInfoList.GoodsImgSl = orderDetailModel.GoodsImgSl;
                buyCartInfoList.GodosName = orderDetailModel.ProductName;
                buyCartInfoList.ProductCount = orderDetailModel.ProductNum == null ? 0 : Integer.valueOf(orderDetailModel.ProductNum);
                buyCartInfoList.XfNo = orderDetailModel.XfNo;
                buyCartInfoList.GoodsInfoId = orderDetailModel.GoodsInfoId;
                buyCartInfoList.XfStatusDisp = orderDetailModel.XfStatusDisp;
                PreOrderItemView preOrderItemView = PreOrderItemView_.build(this);
                preOrderItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsDetailInfoActivity_.intent(OrderDetailActivity.this).goodsId(buyCartInfoList.GoodsInfoId).start();
                    }
                });
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
                    temp = "不赠券+";
                }
            } else {
                temp = "不赠券+";
            }
            txt_coupon.setText(temp.substring(0, temp.lastIndexOf('+')));
            if (bmj.Data.MorderStatus == Constants.DUEPAYMENT) {
                btn_cancel_order.setVisibility(View.GONE);
                btn_pay.setVisibility(View.VISIBLE);
                btn_logistics.setVisibility(View.GONE);
                btn_finish.setVisibility(View.GONE);
                btn_canceled.setVisibility(View.GONE);
                //还需支付
                ll_should_pay.setVisibility(View.VISIBLE);
                ll_paid.setVisibility(View.VISIBLE);
                if (bmj.Data.MOrderDzb >= 0) {
                    txt_should_pay_l_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney - bmj.Data.MOrderDzb));
                    txt_paid_rmb.setText(String.format(home_rmb, bmj.Data.MOrderDzb));
                }
                rl_express_charges.setVisibility(View.GONE);
            } else if (bmj.Data.MorderStatus == Constants.PAID) {
                ll_take.setVisibility("1".equals(bmj.Data.GoodsType) ? View.GONE : View.VISIBLE);
                btn_logistics.setVisibility(View.VISIBLE);
                btn_finish.setVisibility(View.GONE);
                btn_cancel_order.setVisibility(View.GONE);
                btn_pay.setVisibility(View.GONE);
                btn_canceled.setVisibility(View.GONE);
                ll_should_pay.setVisibility(View.GONE);
            } else if (bmj.Data.MorderStatus == Constants.CANCEL) {
                btn_canceled.setVisibility(View.VISIBLE);
                btn_logistics.setVisibility(View.GONE);
                btn_finish.setVisibility(View.GONE);
                btn_cancel_order.setVisibility(View.GONE);
                btn_pay.setVisibility(View.GONE);
            } else if (bmj.Data.MorderStatus == Constants.SEND) {
                btn_logistics.setVisibility(View.VISIBLE);
                btn_finish.setVisibility(View.VISIBLE);
                btn_cancel_order.setVisibility(View.GONE);
                btn_pay.setVisibility(View.GONE);
                btn_canceled.setVisibility(View.GONE);
                ll_should_pay.setVisibility(View.GONE);
            } else if (bmj.Data.MorderStatus == Constants.CONFIRM) {
                btn_logistics.setVisibility(View.VISIBLE);
                btn_finish.setVisibility(View.GONE);
                btn_cancel_order.setVisibility(View.GONE);
                btn_pay.setVisibility(View.GONE);
                btn_canceled.setVisibility(View.GONE);
                ll_should_pay.setVisibility(View.GONE);
            } else if (bmj.Data.MorderStatus == Constants.FINISH) {
                btn_logistics.setVisibility(View.VISIBLE);
                btn_finished.setVisibility(View.VISIBLE);
                btn_finish.setVisibility(View.GONE);
                btn_cancel_order.setVisibility(View.GONE);
                btn_pay.setVisibility(View.GONE);
                btn_canceled.setVisibility(View.GONE);
                ll_should_pay.setVisibility(View.GONE);
            }
            ll_shipping.setVisibility("1".equals(bmj.Data.GoodsType) ? View.GONE : View.VISIBLE);
            ll_logistics.setVisibility("1".equals(bmj.Data.GoodsType) ? View.GONE : View.VISIBLE);
            rl_express_charges.setVisibility("1".equals(bmj.Data.GoodsType) ? View.GONE : View.VISIBLE);
        }
    }

    @Click
    void btn_finish() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("提示").setMessage("确认收货？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AndroidTool.showLoadDialog(OrderDetailActivity.this);
                confirmReceipt();
            }
        }).setNegativeButton("取消", null).setIcon(R.mipmap.logo).create().show();
    }

    @Background
    void confirmReceipt() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        Map<String, String> map = new HashMap<>(1);
        map.put("MOrderId", orderId);
        afterConfirm(myRestClient.confirmReceipt(map));
    }

    @UiThread
    void afterConfirm(BaseModel bm) {
        AndroidTool.dismissLoadDialog();
        if (bm == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bm.Successful) {
            AndroidTool.showToast(this, bm.Error);
        } else {
            btn_finish.setVisibility(View.GONE);
        }
    }

    @Click
    void ll_logistics() {
        LogisticsInfoActivity_.intent(this).MOrderId(mAppOrder.MOrderId).start();

    }

    @Click
    void btn_logistics() {
        LogisticsInfoActivity_.intent(this).MOrderId(mAppOrder.MOrderId).start();
    }

    @Click
    void btn_pay() {
        switch (mAppOrder.MPaymentType) {
            case Constants.ALI_PAY:
            case Constants.ALI_DZB:
            case Constants.ALI_DZB_LONGBI:
            case Constants.ALI_LONGBI:
                myBackgroundTask.aliPay(mAppOrder.AlipayInfo, this, mAppOrder.MOrderId);
                break;
            case Constants.WX_DZB:
            case Constants.WX_LONGBI:
            case Constants.WX_PAY:
            case Constants.WX_DZB_LONGBI:
                if (mAppOrder.WxPayData != null) {
                    mAppOrder.WxPayData.extData = mAppOrder.MOrderId;
                    app.iWXApi.sendReq(mAppOrder.WxPayData);
                }
                break;
            case Constants.UMSPAY:
            case Constants.DZB_UMSPAY:
            case Constants.LONGBI_UMSPAY:
            case Constants.LONGBI_UMSPAY_DZB:
                UnionPay order = new UnionPay();
                order.ChrCode = mAppOrder.chrCode;
                order.MerSign = mAppOrder.merSign;
                order.TransId = mAppOrder.transId;
                UmspayActivity_.intent(this).MOrderId(mAppOrder.MOrderId).order(order).startForResult(1000);
                finish();
            default:
                AndroidTool.showToast(this, "该订单已支付");
        }
    }

    @Subscribe
    public void NotifyUI(PayResp resp) {
        switch (resp.errCode) {
            case 0:
                AndroidTool.showToast(this, "支付成功");
                AndroidTool.showLoadDialog(this);
                getOrderDetailById();
                break;
            case -1:
                AndroidTool.showToast(this, "支付异常");
                break;
            case -2:
                AndroidTool.showToast(this, "您取消了支付");
                break;
        }
    }

    @Subscribe
    public void NotifyUI(PayResult payResult) {
        switch (payResult.getResultStatus()) {
            case "9000":
                AndroidTool.showToast(this, "支付成功");
                AndroidTool.showLoadDialog(this);
                getOrderDetailById();
                break;
            case "8000":
                AndroidTool.showToast(this, "支付结果确认中");
                break;
            case "4000":
                AndroidTool.showToast(this, "订单支付失败");
                break;
            case "6001":
                AndroidTool.showToast(this, "用户中途取消");
                break;
            case "6002":
                AndroidTool.showToast(this, "网络连接出错");
                break;
            default: {
                AndroidTool.showToast(this, "网络连接出错");
            }
        }

    }

    @OnActivityResult(1000)
    void onPay(int resultCode) {
        if (resultCode == RESULT_OK) {
            AndroidTool.showLoadDialog(this);
            getOrderDetailById();
        }
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
        bus.unregister(this);
    }
}
