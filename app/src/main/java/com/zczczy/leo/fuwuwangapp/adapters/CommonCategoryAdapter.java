package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.items.FirstCategoryItemView_;
import com.zczczy.leo.fuwuwangapp.items.SecondCategoryItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2016/5/4.
 */
@EBean
public class CommonCategoryAdapter extends BaseRecyclerViewAdapter<GoodsTypeModel> {

    @RestService
    MyDotNetRestClient myRestClient;

    @App
    MyApplication app;

    @Pref
    MyPrefs_ pre;

    @StringRes
    String no_net;

    @Bean
    MyErrorHandler myErrorHandler;

    int type = 0;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }


    @Override
    @Background
    public void getMoreData(Object... objects) {
        BaseModelJson<List<GoodsTypeModel>> bmj = null;
        switch (Integer.valueOf(objects[0].toString())) {
            case 0:
                bmj = new BaseModelJson<>();
                bmj.Data= new ArrayList<>();
                bmj.Successful = true;
                for (int i = 0; i < 10; i++) {
                    GoodsTypeModel goodsTypeModel = new GoodsTypeModel();
                    goodsTypeModel.GoodsTypeName = "一级分类" + i;
                    bmj.Data.add(goodsTypeModel);
                }
                break;
            case 1:
                bmj = new BaseModelJson<>();
                bmj.Data= new ArrayList<>();
                bmj.Successful = true;
                for (int i = 0; i < 10; i++) {
                    GoodsTypeModel goodsTypeModel = new GoodsTypeModel();
                    goodsTypeModel.GoodsTypeName = "二级分类" + i;
                    goodsTypeModel.GoodsTypeIcon="https://img.alicdn.com/imgextra/i4/1974919058/TB2.H.xnVXXXXbpXXXXXXXXXXXX_!!1974919058.jpg_190x190q90.jpg_.webp";
                    bmj.Data.add(goodsTypeModel);
                }
                bmj = new BaseModelJson<>();
                break;
        }
        afterGetData(bmj);
    }

    @UiThread
    void afterGetData(BaseModelJson<List<GoodsTypeModel>> bmj) {
        if (bmj == null) {

//            AndroidTool.showToast(context, no_net);
        } else if (bmj.Successful) {
            if (bmj.Data.size() > 0) {
                insertAll(bmj.Data, getItems().size());
            }
        }
    }


    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        if (type == CommonCategoryType.SECOND) {
            return SecondCategoryItemView_.build(parent.getContext());
        } else {
            return FirstCategoryItemView_.build(parent.getContext());
        }
    }


    public static class CommonCategoryType {
        public static int FIRST = 0;
        public static int SECOND = 1;
    }

}
