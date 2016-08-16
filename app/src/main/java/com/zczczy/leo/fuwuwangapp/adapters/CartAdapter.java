package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.activities.CartActivity;
import com.zczczy.leo.fuwuwangapp.items.CartDetailItemView_;
import com.zczczy.leo.fuwuwangapp.items.CartItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;
import com.zczczy.leo.fuwuwangapp.model.CartInfo;
import com.zczczy.leo.fuwuwangapp.model.CartModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
@EBean
public class CartAdapter extends BaseRecyclerViewAdapter<CartModel> {


    @RootContext
    CartActivity mCartActivity;

    @Override
    @Background
    public void getMoreData(Object... objects) {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        BaseModelJson<List<CartInfo>> bmj;
        bmj = myRestClient.getBuyCartInfo();
        afterGetData(bmj);
    }

    @UiThread
    void afterGetData(BaseModelJson<List<CartInfo>> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
        } else if (bmj.Successful) {
            clear();
            List<CartModel> list = new ArrayList<>();
            for (CartInfo aa : bmj.Data) {
                CartModel cm0 = new CartModel();
                cm0.StoreInfoId = aa.StoreInfoId;
                cm0.StoreName = aa.StoreName;
                cm0.level = 0;
                list.add(cm0);
                for (BuyCartInfoList bb : aa.BuyCartInfoList) {
                    CartModel cm = new CartModel();
                    cm.BuyCartInfoId = bb.BuyCartInfoId;
                    cm.CreateTime = bb.CreateTime;
                    cm.GodosName = bb.GodosName;
                    cm.GoodsImgSl = bb.GoodsImgSl;
                    cm.GoodsInfoId = bb.GoodsInfoId;
                    cm.GoodsLBPrice = bb.GoodsLBPrice;
                    cm.GoodsPrice = bb.GoodsPrice;
                    cm.ProductCount = bb.ProductCount;
                    cm.StoreInfoId = aa.StoreInfoId;
                    cm.StoreName = aa.StoreName;
                    cm.GoodsAttributeName = bb.GoodsAttributeName;
                    cm.UserInfoId = bb.UserInfoId;
                    cm.level = 1;
                    list.add(cm);
                }
            }
            insertAll(list, getItemCount());
            mCartActivity.notifyChanged();
        }
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return CartItemView_.build(parent.getContext());
        } else {
            return CartDetailItemView_.build(parent.getContext());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItemData(position).level;
    }

    public void checkAll() {
        for (CartModel cm : getItems()) {
            cm.isChecked = true;
        }
        notifyDataSetChanged();
    }

    public void unCheckAll() {
        for (CartModel cm : getItems()) {
            cm.isChecked = false;
        }
        notifyDataSetChanged();
    }
}
