package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.FirstCategoryAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.FirstServiceCategoryAdapter;
import com.zczczy.leo.fuwuwangapp.listener.PopupItemClickCallBack;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * Created by Leo on 2016/5/10.
 */
@EViewGroup(R.layout.drop_menu_pop)
public class CategoryPopItemView extends LinearLayout {
    @ViewById
    ListView rootCategory, childCategory;

    @ViewById
    FrameLayout child_lay;

    @Bean
    FirstServiceCategoryAdapter firstCategoryAdapter;

    @Bean
    SecondServiceCategoryAdapter secondCategoryAdapter;

    @App
    MyApplication app;

    PopupItemClickCallBack callBack;

    GoodsTypeModel firstCategory;

    @ColorRes
    int buy_button, black;

    public PopupItemClickCallBack getCallBack() {
        return callBack;
    }

    public CategoryPopItemView setCallBack(PopupItemClickCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public CategoryPopItemView(Context context) {
        super(context);
    }


    @AfterInject
    void afterInject() {
        firstCategory = app.getFirstCategory();
    }

    @AfterViews
    void afterView() {

        rootCategory.setAdapter(firstCategoryAdapter);
        //如果 index 没查出来数据  再查询一遍
        if (app.getFirstCategoryList() == null) {
            firstCategoryAdapter.getMoreData(0, 0);
        }
        childCategory.setAdapter(secondCategoryAdapter);

        if (app.getSecondCategory() != null && app.getFirstCategory() != null) {
            secondCategoryAdapter.getMoreData(0, 0, app.getFirstCategory().ChildGoodsType);
        }

    }

    @ItemClick
    void rootCategory(GoodsTypeModel firstCategory) {
        secondCategoryAdapter.getMoreData(0, 0, firstCategory.ChildGoodsType);
        for (int i = 0; i < firstCategoryAdapter.getFirstCategoryItemView().size(); i++) {
            if (firstCategoryAdapter.getFirstCategoryItemView().get(i)._data == firstCategory) {
                firstCategoryAdapter.getFirstCategoryItemView().get(i).txt_region.setTextColor(buy_button);
                firstCategoryAdapter.getFirstCategoryItemView().get(i).rl.setSelected(true);
            } else {
                firstCategoryAdapter.getFirstCategoryItemView().get(i).txt_region.setTextColor(black);
                firstCategoryAdapter.getFirstCategoryItemView().get(i).rl.setSelected(false);
            }
        }
        this.firstCategory = firstCategory;
    }

    @ItemClick
    void childCategory(GoodsTypeModel secondCategory) {
        if (callBack != null) {
            app.setFirstCategory(firstCategory);
            app.setSecondCategory(secondCategory);
            callBack.callBackCategory();
        }
    }
}
