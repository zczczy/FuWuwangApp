package com.zczczy.leo.fuwuwangapp.activities;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CartAdapter;
import com.zczczy.leo.fuwuwangapp.items.CartBuyPopup;
import com.zczczy.leo.fuwuwangapp.items.CartBuyPopup_;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CartModel;
import com.zczczy.leo.fuwuwangapp.model.CheckOutModel;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leo on 2016/5/1.
 */
@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    CartAdapter myAdapter;

    @ViewById
    LinearLayout ll_checkout, ll_delete, ll_cart_jiesuan;

    @ViewById
    RelativeLayout rl_root;

    @ViewById
    TextView txt_total_rmb, txt_total_lb;

    @StringRes
    String home_lb, he_ji, cart_no_goods, text_edit, text_delete, text_cancel, text_tip, text_tip_confirm;

    @ViewById
    CheckBox cb_all;

    LinearLayoutManager linearLayoutManager;

    PopupWindow popupWindow;

    List<CheckOutModel> list;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;


    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        recyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myTitleBar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_delete.isShown()) {
                    ll_delete.setVisibility(View.GONE);
                    myTitleBar.setRightText(text_edit);
                    ll_checkout.setVisibility(View.VISIBLE);
                } else {
                    myTitleBar.setRightText(text_cancel);
                    ll_delete.setVisibility(View.VISIBLE);
                    ll_checkout.setVisibility(View.GONE);
                }
            }
        });
        setTotalMoney();

        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<CartModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, CartModel obj, int position) {
                if (obj.level == 0) {
                    StoreInformationActivity_.intent(CartActivity.this).storeId(obj.StoreInfoId).start();
                } else {
                    GoodsDetailActivity_.intent(CartActivity.this).goodsId(obj.GoodsInfoId).start();
                }
            }
        });
    }

    @Click
    void ll_delete() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(text_tip).setMessage(text_tip_confirm).setPositiveButton(text_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                calc();
                if (list.size() > 0) {
                    deleteShopping();
                } else {
                    AndroidTool.showToast(CartActivity.this, "购物车为空");
                }
            }
        }).setNegativeButton(text_cancel, null).setIcon(R.mipmap.logo).create().show();
    }

    @Background
    void deleteShopping() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        Map<String, String> map = new HashMap<>(1);
        String temp = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                temp += list.get(i).BuyCartInfoIds;
            } else {
                temp += list.get(i).BuyCartInfoIds + ",";
            }
        }
        map.put("BuyCartInfoIds", temp);
        afterDeleteShopping(myRestClient.deleteShoppingCartById(map));
    }

    @UiThread
    void afterDeleteShopping(BaseModel bm) {
        if (bm == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bm.Successful) {
            AndroidTool.showToast(this, bm.Error);
        } else {
            myAdapter.getMoreData();
            ll_delete.setVisibility(View.GONE);
            myTitleBar.setRightText(text_edit);
            ll_checkout.setVisibility(View.VISIBLE);
        }
    }

    @Click
    void ll_checkout() {
        calc();
        if (list.size() > 0) {
            BaseModelJson<List<CheckOutModel>> bmj = new BaseModelJson<>();
            bmj.Successful = true;
            bmj.Data = list;
            CartBuyPopup cartBuyPopup = CartBuyPopup_.build(this);
            popupWindow = new PopupWindow(cartBuyPopup, ViewGroup.LayoutParams.MATCH_PARENT, rl_root.getHeight(), true);
            cartBuyPopup.setData(bmj, popupWindow);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            popupWindow.setBackgroundDrawable(dw);
            boolean hasSoftKey = ViewConfiguration.get(this).hasPermanentMenuKey();
            if (hasSoftKey || isNavigationBarAvailable()) {
                popupWindow.showAtLocation(rl_root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, ll_cart_jiesuan.getHeight() * 2);
            } else {
                popupWindow.showAtLocation(rl_root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, ll_cart_jiesuan.getHeight() + 1);
            }
        } else {
            AndroidTool.showToast(this, cart_no_goods);
        }
    }

    public boolean isNavigationBarAvailable() {

        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);

        return (!(hasBackKey && hasHomeKey));
    }

    void calc() {
        //定义一个临时集合
        List<CheckOutModel> tempList = new ArrayList<>();
        //添加店铺
        for (CartModel cm : myAdapter.getItems()) {
            if (cm.level == 0) {
                CheckOutModel checkOutModel = new CheckOutModel();
                checkOutModel.StoreInfoId = cm.StoreInfoId;
                checkOutModel.StoreName = cm.StoreName;
                checkOutModel.isChecked = cm.isChecked;
                checkOutModel.BuyCartInfoIds = "";
                tempList.add(checkOutModel);
            }

        }
        //统计店铺下面的对应商品
        for (CartModel cm : myAdapter.getItems()) {
            if (cm.isChecked && cm.level == 1) {
                for (CheckOutModel checkOutModel : tempList) {
                    if (cm.StoreInfoId.equals(checkOutModel.StoreInfoId)) {
                        checkOutModel.BuyCartInfoIds += String.valueOf(cm.BuyCartInfoId + ",");
                        checkOutModel.ProductCount += cm.ProductCount;
                        checkOutModel.rmbTotal += Double.valueOf(cm.GoodsPrice) * cm.ProductCount;
                        checkOutModel.lbTotal += cm.GoodsLBPrice * cm.ProductCount;
                    }
                }
            }
        }
        list = new ArrayList<>();
        //取出有商品的店铺
        for (CheckOutModel com : tempList) {
            if (com.ProductCount > 0) {
                //去掉最后一个","
                com.BuyCartInfoIds = com.BuyCartInfoIds.substring(0, com.BuyCartInfoIds.lastIndexOf(','));
                list.add(com);
            }
        }
    }

    public void setTotalMoney() {
        calc();
        double d = 0.00;
        int lb = 0;
        for (int i = 0; i < list.size(); i++) {
            d += list.get(i).rmbTotal;
            lb += list.get(i).lbTotal;
        }
        txt_total_rmb.setText(String.format(he_ji, new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP)));
        txt_total_lb.setText(String.format(home_lb, lb));
    }

    @Override
    public void onPause() {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
        super.onPause();
    }

    @Override
    public void onResume() {
        myAdapter.getMoreData();
        super.onResume();
    }

    @Click
    void cb_all() {
        if (cb_all.isChecked()) {
            myAdapter.checkAll();
        } else {
            myAdapter.unCheckAll();
        }
        setTotalMoney();
    }

    public void notifyChanged() {
        cb_all.setChecked(false);
        setTotalMoney();
    }
}
