package com.zczczy.leo.fuwuwangapp.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.items.PreOrderItemView;
import com.zczczy.leo.fuwuwangapp.items.PreOrderItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.MReceiptAddressModel;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;
import com.zczczy.leo.fuwuwangapp.model.PayResult;
import com.zczczy.leo.fuwuwangapp.model.ShopOrder;
import com.zczczy.leo.fuwuwangapp.rest.MyBackgroundTask;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

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
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/4.
 */
@EActivity(R.layout.activity_pre_order)
public class PreOrderActivity extends BaseActivity {

    @ViewById
    LinearLayout ll_shipping, ll_store, ll_pre_order_item;

    @ViewById
    RelativeLayout rl_dian, ll_lb, ll_pay, rl_express_charges, rl_postal;

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

    @ViewById
    CheckBox use_dian;

    @ViewById
    RadioButton rb_bao_pay, rb_wechat_pay, rb_umpay;

    @Extra
    String goodsInfoId, BuyCartInfoIds, StoreInfoId;

    @Extra
    int orderCount, GoodsAttributeId;

    @Bean
    MyBackgroundTask mMyBackgroundTask;

    @Extra
    boolean isCart;

    @Bean
    OttoBus bus;

    boolean isService;

    //商品总价
    double yuan;

    //电子币余额
    double balance = 10;

    //输入的电子币数 使用的电子币
    double useDianZiBi = 0.00;

    //实际付款
    double payYuan;

    //店铺id
    String storeId;

    //店铺类型
    int sellerType = 0;

    int longBi;

    //邮费
    double postal;

    AlertDialog.Builder adb;

    AlertDialog ad;

    String password;

    EditText pass;

    ShopOrder shopOrder;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        bus.register(this);
        txt_dian_quantity.setClickable(false);
        createTempOrderInfo();
    }

    @Background
    void createTempOrderInfo() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        if (isCart) {
            afterCreateTempOrderInfo(myRestClient.createTempOrderInfo(BuyCartInfoIds, StoreInfoId));
        } else {
            afterCreateTempOrderInfo(myRestClient.createTempGoodsOrderInfo(goodsInfoId, orderCount, StoreInfoId, GoodsAttributeId));
        }
    }

    @UiThread
    void afterCreateTempOrderInfo(BaseModelJson<ShopOrder> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            shopOrder = bmj.Data;
            setShipping(bmj.Data.MReceiptAddress);
            txt_store.setText(bmj.Data.StoreInfoName);
            storeId = bmj.Data.StoreInfoId;
            txt_express_charges.setText(Double.valueOf(bmj.Data.Postage) > 0 ? String.format(home_rmb, bmj.Data.Postage) : "包邮");
            postal = Double.valueOf(bmj.Data.Postage);
            txt_dian_balance.setText(String.format(dian_balance, bmj.Data.MaxDzb));
            balance = bmj.Data.MaxDzb;
            txt_sub_express_charges.setText(String.format(home_rmb, bmj.Data.Postage));
            txt_pay_total_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney));
            payYuan = bmj.Data.MOrderMoney;
            txt_total_lb.setText(String.format(home_lb, bmj.Data.GoodsAllLbCount));
            int i = 0;
            for (OrderDetailModel buyCartInfoList : bmj.Data.OrderDetailList) {
                PreOrderItemView preOrderItemView = PreOrderItemView_.build(this);
                preOrderItemView.init(buyCartInfoList);
                ll_pre_order_item.addView(preOrderItemView, i);
            }
            sellerType = bmj.Data.SellerType;
            //设置商品总价（去除邮费）
            yuan = bmj.Data.MOrderMoney - Double.valueOf(bmj.Data.Postage);
            //设置费用
            if (bmj.Data.MOrderMoney > 0 && bmj.Data.GoodsAllLbCount > 0) {
                ll_lb.setVisibility(View.VISIBLE);
                ll_pay.setVisibility(View.VISIBLE);
                txt_rmb.setVisibility(View.VISIBLE);
                txt_plus.setVisibility(View.VISIBLE);
                //设置商品总价
                txt_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney));
                //设置龙币数
                txt_home_lb.setText(String.format(home_lb, bmj.Data.GoodsAllLbCount));
            } else if (bmj.Data.MOrderMoney > 0) {
                ll_lb.setVisibility(View.GONE);
                ll_pay.setVisibility(View.VISIBLE);
                txt_rmb.setVisibility(View.VISIBLE);
                txt_plus.setVisibility(View.GONE);
                txt_home_lb.setVisibility(View.GONE);
                txt_rmb.setText(String.format(home_rmb, bmj.Data.MOrderMoney));
            } else if (bmj.Data.GoodsAllLbCount > 0) {
                ll_pay.setVisibility(View.GONE);
                ll_lb.setVisibility(View.VISIBLE);
                txt_rmb.setVisibility(View.GONE);
                txt_plus.setVisibility(View.GONE);
                txt_home_lb.setVisibility(View.VISIBLE);
                txt_home_lb.setText(String.format(home_lb, bmj.Data.GoodsAllLbCount));
            }
            //设置商品总数
            txt_count.setText(String.format(text_count, bmj.Data.GoodsAllCount));
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
            longBi = bmj.Data.GoodsAllLbCount;
            //1:服务类，2：邮寄类
            ll_shipping.setVisibility((isService = "1".equals(bmj.Data.GoodsType)) ? View.GONE : View.VISIBLE);
            rl_express_charges.setVisibility((isService = "1".equals(bmj.Data.GoodsType)) ? View.GONE : View.VISIBLE);
            rl_postal.setVisibility((isService = "1".equals(bmj.Data.GoodsType)) ? View.GONE : View.VISIBLE);
        } else {
            finish();
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    @CheckedChange
    void use_dian(Boolean checked) {
        if (sellerType != 1) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("提示").setMessage("该商家不支持电子币支付").setNegativeButton("取消", null).setIcon(R.mipmap.logo).create().show();
        } else if (Constants.NORMAL.equals(pre.userType().get())) {
            AndroidTool.showToast(this, "只有VIP用户才可以使用电子币");
        } else {
            txt_dian_quantity.setClickable(checked);
            txt_dian_quantity.setText(checked ? useDianZiBi + "" : "0.0");
            txt_pay_total_rmb.setText(String.format(home_rmb, checked ? payYuan : (yuan + postal)));
        }
    }

    @Click
    void txt_dian_quantity() {
        if (txt_dian_quantity.isClickable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("输入您要使用的电子币数量");
            View inflate = layoutInflater.inflate(R.layout.custom_dialog_change_quantity, null, false);
            final EditText et = (EditText) inflate.findViewById(R.id.et_qty);
            et.setHint(String.valueOf(useDianZiBi));
            builder.setView(inflate);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newQuantity = et.getText().toString();
                    if (TextUtils.isEmpty(newQuantity)) return;
                    useDianZiBi = Double.valueOf("".equals(newQuantity) ? "0" : newQuantity);
                    if (useDianZiBi >= yuan && balance >= yuan) {//输入的电子币大于等于商品总价 并且电子币余额大于等于输入的电子币，使用的电子币为商品总价
                        useDianZiBi = yuan;
                    } else if (useDianZiBi >= yuan && balance < yuan) {//输入的电子币大于等于商品总价 并且电子币余额小于于输入的电子币，使用的电子币为电子币余额
                        useDianZiBi = balance;
                    } else if (useDianZiBi < yuan && useDianZiBi <= balance) {//输入的电子币小于商品总价 并且输入的电子币大于电子币余额，使用的电子币为 输入电子币

                    } else if (useDianZiBi < yuan && useDianZiBi > balance) { //输入的电子币小于商品总价 并且输入的电子币大于电子币余额，使用的电子币为电子币余额
                        useDianZiBi = balance;
                    }
                    useDianZiBi = Double.valueOf(AndroidTool.df.format(useDianZiBi));
                    txt_dian_quantity.setText(String.valueOf(useDianZiBi));
                    //设置实际付款金额 yuan（商品总价）-useDianZiBi（用的电子币数量） + postal邮费
                    payYuan = (Math.round((yuan - useDianZiBi) * 100) / 100.0) + postal;
                    txt_pay_total_rmb.setText(String.format(home_rmb, payYuan));
                    inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });
            builder.show();
        }
    }

    @Click
    void txt_take() {
        if (!isService && AndroidTool.checkTextViewIsNull(tv_shipping, txt_phone, tv_shipping_address)) {
            AndroidTool.showToast(this, "请选择收货地址");
        } else {
            if ((use_dian.isChecked() && useDianZiBi > 0) || longBi > 0) {
                final View view = layoutInflater.inflate(R.layout.pay_pass, null);
                adb = new AlertDialog.Builder(this);
                ad = adb.setView(view).create();
                ad.show();
                pass = (EditText) view.findViewById(R.id.pass);
                Button cancel = (Button) view.findViewById(R.id.cancel);
                Button confirm = (Button) view.findViewById(R.id.btn_confirm);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        ad.dismiss();
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AndroidTool.checkIsNull(pass)) {
                            AndroidTool.showToast(PreOrderActivity.this, "密码不能为空");
                        } else {
                            password = pass.getText().toString().trim();
                            ad.dismiss();
                            AndroidTool.showLoadDialog(PreOrderActivity.this);
                            payOrder();
                        }
                    }
                });
            } else {
                AndroidTool.showLoadDialog(PreOrderActivity.this);
                payOrder();
            }
        }
    }

    @Background
    void payOrder() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("kbn", Constants.ANDROID);
        shopOrder.TwoPass = password;
        shopOrder.MOrderDzb = use_dian.isChecked() ? useDianZiBi : 0;
        shopOrder.MPaymentType = rb_bao_pay.isChecked() ? 1 : (rb_wechat_pay.isChecked() ? 2 : 3);
        Gson gson = new Gson();
        Log.e("11111111111111111111111", gson.toJson(shopOrder));

        afterPayOrder(myRestClient.createOrderInfo(shopOrder));
    }

    @UiThread
    void afterPayOrder(BaseModelJson<ShopOrder> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            switch (bmj.Data.MPaymentType) {
                case Constants.ALI_PAY:
                case Constants.ALI_DZB:
                case Constants.ALI_DZB_LONGBI:
                case Constants.ALI_LONGBI:
                    mMyBackgroundTask.aliPay(bmj.Data.AlipayInfo, this, bmj.Data.MOrderId);
                    break;
                case Constants.WX_DZB:
                case Constants.WX_LONGBI:
                case Constants.WX_PAY:
                case Constants.WX_DZB_LONGBI:
                    if (bmj.Data.WxPayData != null) {
                        bmj.Data.WxPayData.extData = bmj.Data.MOrderId;
                        app.iWXApi.sendReq(bmj.Data.WxPayData);
                    }
                    break;
                case Constants.UMSPAY:
                case Constants.DZB_UMSPAY:
                case Constants.LONGBI_UMSPAY:
                case Constants.LONGBI_UMSPAY_DZB:
                    if (bmj.Data.unionPay != null) {
                        UmspayActivity_.intent(this).MOrderId(bmj.Data.MOrderId).order(bmj.Data.unionPay).start();
                        finish();
                    } else {
                        AndroidTool.showToast(this, "服务器繁忙");
                    }
                    break;
                default:
                    AndroidTool.showToast(this, "付款成功");
                    OrderDetailActivity_.intent(this).orderId(bmj.Data.MOrderId).start();
                    finish();
            }

        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }


    @Subscribe
    public void NotifyUI(PayResp resp) {
        switch (resp.errCode) {
            case 0:
                break;
            case -1:
                AndroidTool.showToast(this, "支付异常");
                break;
            case -2:
                AndroidTool.showToast(this, "您取消了支付");
                break;
        }
        OrderDetailActivity_.intent(this).orderId(resp.extData).start();
        finish();
    }

    @Subscribe
    public void NotifyUI(PayResult payResult) {
        switch (payResult.getResultStatus()) {
            case "9000":
                AndroidTool.showToast(this, "支付成功");
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
        OrderDetailActivity_.intent(this).orderId(payResult.getOrderId()).start();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        bus.unregister(this);
    }

//    void showVoucher(String str) {
//        order.setElectronics_evidence(str);
//        order.setLongbi_pay_state(1);
//        order.setDianzibi_pay_state(1);
//        order.setOrder_state(MyApplication.PAID);
//        View view = layoutInflater.inflate(R.layout.new_voucher, null);
//        TextView voucher = (TextView) view.findViewById(R.id.voucher);
//        ImageView img_close = (ImageView) view.findViewById(R.id.img_close);
//        voucher.setText(str.replaceAll("([\\d]{4})", "$1 "));
//        adb = new AlertDialog.Builder(this);
//        ad = adb.setView(view).create();
//        ad.setCanceledOnTouchOutside(false);
//        ad.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                NewOrderDetailActivity_.intent(NewPayOrderActivity.this).order(order).start();
//            }
//        });
//        ad.show();
//        img_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ad.dismiss();
//            }
//        });
//    }

    //设置 收货地址
    void setShipping(MReceiptAddressModel model) {
        if (model != null) {
            shopOrder.MReceiptAddress = model;
            tv_shipping.setText(String.format(txt_shipping, model.ReceiptName));
            txt_phone.setText(model.Mobile);
            tv_shipping_address.setText(String.format(txt_shipping_address, model.ProvinceName + model.CityName + model.AreaName + model.DetailAddress));
        } else {
            tv_shipping.setText("!填写收货地址");
        }
    }

    @Click
    void ll_shipping() {
        ShippingAddressActivity_.intent(this).isFinish(true).startForResult(1000);
    }

    @OnActivityResult(1000)
    void onSelectShippingAddress(int reault, @OnActivityResult.Extra MReceiptAddressModel model) {
        setShipping(model);
    }

    @Click
    void ll_store() {
        StoreInformationActivity_.intent(this).storeId(storeId).start();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("提示").setMessage("确定要放弃该订单吗？").setPositiveButton("放弃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("取消", null).setIcon(R.mipmap.logo).create().show();
    }
}
