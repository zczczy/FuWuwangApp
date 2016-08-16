package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;


import com.zczczy.leo.fuwuwangapp.activities.CategoryActivity;
import com.zczczy.leo.fuwuwangapp.items.FirstCategoryItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import java.util.List;

/**
 * Created by Leo on 2016/5/4.
 */
@EBean
public class CategoryAdapter extends BaseRecyclerViewAdapter<GoodsTypeModel> {

    @RootContext
    CategoryActivity categoryActivity;

    @Override
    public void getMoreData(Object... objects) {
        BaseModelJson<List<GoodsTypeModel>> bmj = null;
        switch (objects[0].toString()) {
            case "0":
                for (GoodsTypeModel g :
                        app.getGoodsTypeModelList()) {
                    if (g.GoodsTypeId == Integer.valueOf(objects[1].toString())) {
                        bmj = new BaseModelJson<>();
                        bmj.Data = g.ChildGoodsType;
                        bmj.Successful = true;
                        break;
                    }
                }
                break;
            case "1":
        }
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
        categoryActivity.notifyUI(bmj);
    }


    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return FirstCategoryItemView_.build(context);
    }


}