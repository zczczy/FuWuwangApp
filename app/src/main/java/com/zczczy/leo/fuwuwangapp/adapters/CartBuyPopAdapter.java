package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.items.CartBuyPopItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;
import com.zczczy.leo.fuwuwangapp.model.CartInfo;
import com.zczczy.leo.fuwuwangapp.model.CartModel;
import com.zczczy.leo.fuwuwangapp.model.CheckOutModel;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/5/7.
 */
@EBean
public class CartBuyPopAdapter extends BaseRecyclerViewAdapter<CheckOutModel> {


    @Override
    @Background
    public void getMoreData(Object... objects) {
        afterGetData((BaseModelJson<List<CheckOutModel>>) objects[0]);
    }

    @UiThread
    void afterGetData(BaseModelJson<List<CheckOutModel>> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
        } else if (bmj.Successful) {
            insertAll(bmj.Data, getItemCount());
        }
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return CartBuyPopItemView_.build(parent.getContext());
    }
}
