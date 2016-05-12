package com.zczczy.leo.fuwuwangapp.adapters;

import android.content.Context;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.items.FirstCategoryItemView;
import com.zczczy.leo.fuwuwangapp.items.FirstServiceCategoryItemView;
import com.zczczy.leo.fuwuwangapp.items.FirstServiceCategoryItemView_;
import com.zczczy.leo.fuwuwangapp.items.ItemView;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/5/10.
 */
@EBean
public class FirstServiceCategoryAdapter extends MyBaseAdapter<GoodsTypeModel> {

    private List<FirstServiceCategoryItemView> firstCategoryItemView = new ArrayList<>();


    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @App
    MyApplication app;

    @StringRes
    String no_net;

    @RootContext
    Context context;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        if (app.getServiceGoodsTypeModelList().size() > 0) {
            setList(app.getServiceGoodsTypeModelList());
        }
    }


    @Override
    @Background
    public void getMoreData(int pageIndex, int pageSize, Object... objects) {
        BaseModelJson<List<GoodsTypeModel>> bmj = myRestClient.getGoodsType(MyApplication.SERVIE_CATEGORY);
        afterGetData(bmj);

    }

    @UiThread
    public void afterGetData(BaseModelJson<List<GoodsTypeModel>> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(context, no_net);
        } else if (bmj.Successful) {
            app.setServiceGoodsTypeModelList(bmj.Data);
            for (GoodsTypeModel firstCategory : app.getServiceGoodsTypeModelList()) {
                if (firstCategory.ChildGoodsType == null) {
                    firstCategory.ChildGoodsType = new ArrayList<>();
                }
                GoodsTypeModel secondCategory = new GoodsTypeModel();
                secondCategory.GoodsTypeName = "全部";
                secondCategory.GoodsTypeId = firstCategory.GoodsTypeId;
                secondCategory.GoodsTypePid = firstCategory.GoodsTypeId + "";
                firstCategory.ChildGoodsType.add(0, secondCategory);
            }
            setList(app.getServiceGoodsTypeModelList());
        } else {
            AndroidTool.showToast(context, bmj.Error);
        }
    }


    @Override
    protected ItemView<GoodsTypeModel> getItemView(Context context) {
        FirstServiceCategoryItemView itemView = FirstServiceCategoryItemView_.build(context);
        firstCategoryItemView.add(itemView);
        return itemView;
    }

    public List<FirstServiceCategoryItemView> getFirstCategoryItemView() {
        return firstCategoryItemView;
    }

    public void setFirstCategoryItemView(List<FirstServiceCategoryItemView> firstCategoryItemView) {
        this.firstCategoryItemView = firstCategoryItemView;
    }
}
