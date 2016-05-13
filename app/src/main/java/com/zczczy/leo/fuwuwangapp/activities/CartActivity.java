package com.zczczy.leo.fuwuwangapp.activities;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
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
import com.zczczy.leo.fuwuwangapp.tools.DisplayUtil;
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
    LinearLayout ll_checkout, ll_delete;

    @ViewById
    RelativeLayout rl_root;

    @ViewById
    TextView txt_total_rmb, txt_total_lb;

    @StringRes
    String home_lb, he_ji;

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
        recyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myTitleBar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_delete.isShown()) {
                    ll_delete.setVisibility(View.GONE);
                    myTitleBar.setRightText("编辑");
                    ll_checkout.setVisibility(View.VISIBLE);
                } else {
                    myTitleBar.setRightText("取消");
                    ll_delete.setVisibility(View.VISIBLE);
                    ll_checkout.setVisibility(View.GONE);
                }
            }
        });
        setTotalMoney();
    }

    @Click
    void ll_delete() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("提示").setMessage("确定要删除吗？").setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                calc();
                if(list.size()>0){
                    deleteShopping();
                }
            }
        }).setNegativeButton("取消", null).setIcon(R.mipmap.logo).create().show();
    }

    @Background
    void deleteShopping() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        Map<String, String> map = new HashMap<>(1);
        String temp = "";
        for (int i = 0; i < list.size(); i++) {
            temp += list.get(i).BuyCartInfoIds;
        }
        map.put("BuyCartInfoIds", temp.substring(0, temp.lastIndexOf(',')));
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
            myTitleBar.setRightText("编辑");
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
            popupWindow = new PopupWindow(cartBuyPopup, ViewGroup.LayoutParams.MATCH_PARENT, rl_root.getHeight() - DisplayUtil.dip2px(this, 50), true);
            cartBuyPopup.setData(bmj, popupWindow);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.showAtLocation(rl_root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, DisplayUtil.dip2px(this, 50));
        }
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
                        checkOutModel.rmbTotal += cm.GoodsPrice * cm.ProductCount;
                        checkOutModel.lbTotal += cm.GoodsLBPrice * cm.ProductCount;
                    }
                }
            }
        }
        list = new ArrayList<>();
        //取出有商品的店铺
        for (CheckOutModel com : tempList) {
            if (com.ProductCount > 0) {
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
        txt_total_rmb.setText(String.format(he_ji, d));
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

    }

    public void notifyChanged(){
        cb_all.setChecked(false);
        setTotalMoney();
    }
}
