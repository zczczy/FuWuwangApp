package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.items.CartDetailItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
@EBean
public class CartDetailItemAdapter extends BaseRecyclerViewAdapter<BuyCartInfoList> {

    @Override
    @Background
    public void getMoreData(Object... objects) {
        BaseModelJson<List<BuyCartInfoList>> bmj = null;
        if (objects.length > 0) {
            bmj = new BaseModelJson<>();
            bmj.Successful = true;
            bmj.Data = (List<BuyCartInfoList>) objects[0];
        }
        afterGetData(bmj);
    }

    @UiThread
    void afterGetData(BaseModelJson<List<BuyCartInfoList>> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
        } else if (bmj.Successful) {
            insertAll(bmj.Data, getItemCount());
        } else {
        }
    }

    @Override
    protected View onCreateItemView(ViewGroup parent) {
        return CartDetailItemView_.build(parent.getContext());
    }

}
