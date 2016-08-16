package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.items.SecondCategoryItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

/**
 * Created by leo on 2016/5/4.
 */
@EBean
public class CommonCategoryAdapter extends BaseRecyclerViewAdapter<GoodsTypeModel> {


    @Override
    @Background
    public void getMoreData(Object... objects) {
        BaseModelJson<List<GoodsTypeModel>> bmj = new BaseModelJson<>();
        bmj.Data = (List<GoodsTypeModel>) objects[0];
        bmj.Successful = true;
//        if (objects.length == 1) {
//            bmj = myRestClient.getGoodsTypeByPid(objects[0].toString());
//        }
        afterGetData(bmj);
    }

    @UiThread
    void afterGetData(BaseModelJson<List<GoodsTypeModel>> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            bmj = new BaseModelJson<>();
//            AndroidTool.showToast(context, no_net);
        } else if (bmj.Successful) {
            if (bmj.Data.size() > 0) {
                insertAll(bmj.Data, getItems().size());
            }
        }

    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return SecondCategoryItemView_.build(parent.getContext());
    }
}
