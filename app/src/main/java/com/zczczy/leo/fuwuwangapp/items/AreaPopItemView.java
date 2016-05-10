package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.FirstAreaAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.SecondAreaAdapter;
import com.zczczy.leo.fuwuwangapp.listener.PopupItemClickCallBack;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.StreetInfo;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by Leo on 2016/5/10.
 */
@EViewGroup(R.layout.drop_menu_pop)
public class AreaPopItemView extends LinearLayout {

    @ViewById
    ListView rootCategory, childCategory;

    @ViewById
    FrameLayout child_lay;

    @Bean
    FirstAreaAdapter firstAreaAdapter;

    @Bean
    SecondAreaAdapter secondAreaAdapter;

    @App
    MyApplication app;

    @Pref
    MyPrefs_ pre;

    PopupItemClickCallBack callBack;

    NewArea newRegion;

    @ColorRes
    int buy_button, black;

    public AreaPopItemView(Context context) {
        super(context);
    }

    @AfterInject
    void afterInject() {
        newRegion = app.getNewRegion();
    }


    public AreaPopItemView setCallBack(PopupItemClickCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    @AfterViews
    void afterView() {
        rootCategory.setAdapter(firstAreaAdapter);
        //如果 index 没查出来数据  再差一遍
        if (app.getRegionList() == null) {
            firstAreaAdapter.getMoreData(0, 0, pre.cityId().get());
        }
        childCategory.setAdapter(secondAreaAdapter);
        if (app.getNewStreet() != null && app.getNewRegion() != null) {
            if (newRegion.listStreet != null) {
                secondAreaAdapter.getMoreData(0, 0, newRegion.listStreet);
            }
        }
    }

    @ItemClick
    void rootCategory(NewArea newRegion) {
        if (newRegion.listStreet != null) {
            secondAreaAdapter.getMoreData(0, 0, newRegion.listStreet);
        }
        for (int i = 0; i < firstAreaAdapter.getFirstAreaItemViewList().size(); i++) {
            if (firstAreaAdapter.getFirstAreaItemViewList().get(i)._data == newRegion) {
                firstAreaAdapter.getFirstAreaItemViewList().get(i).txt_region.setTextColor(buy_button);
                firstAreaAdapter.getFirstAreaItemViewList().get(i).rl.setSelected(true);
            } else {
                firstAreaAdapter.getFirstAreaItemViewList().get(i).txt_region.setTextColor(black);
                firstAreaAdapter.getFirstAreaItemViewList().get(i).rl.setSelected(false);
            }
        }
        this.newRegion = newRegion;
    }

    @ItemClick
    void childCategory(StreetInfo newStreet) {
        if (callBack != null) {
            app.setNewRegion(newRegion);
            app.setNewStreet(newStreet);
            callBack.callBack();
        }
    }
}
