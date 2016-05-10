package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.adapters.MyBaseAdapter;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by Leo on 2016/5/10.
 */
@EBean
public class SecondServiceCategoryAdapter extends MyBaseAdapter<GoodsTypeModel> {
    @App
    MyApplication app;

    @Override
    public void getMoreData(int pageIndex, int pageSize, Object... objects) {
        setList((List<GoodsTypeModel>) objects[0]);
    }

    @Override
    protected ItemView<GoodsTypeModel> getItemView(Context context) {
        return SecondServiceCategoryItemView_.build(context);
    }
}
