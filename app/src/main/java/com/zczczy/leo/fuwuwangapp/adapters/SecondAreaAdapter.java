package com.zczczy.leo.fuwuwangapp.adapters;

import android.content.Context;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.items.ItemView;
import com.zczczy.leo.fuwuwangapp.items.SecondAreaItemView_;
import com.zczczy.leo.fuwuwangapp.model.StreetInfo;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.List;

/**
 * Created by Leo on 2015/12/26.
 */

@EBean
public class SecondAreaAdapter extends MyBaseAdapter<StreetInfo> {


    @App
    MyApplication app;

    @Override
    @UiThread
    public void getMoreData(int pageIndex, int pageSize, Object... objects) {

        setList((List<StreetInfo>) objects[0]);
    }

    @Override
    protected ItemView<StreetInfo> getItemView(Context context) {
        return SecondAreaItemView_.build(context);
    }
}
