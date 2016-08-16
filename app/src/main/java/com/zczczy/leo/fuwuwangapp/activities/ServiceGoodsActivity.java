package com.zczczy.leo.fuwuwangapp.activities;

import android.animation.ObjectAnimator;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.StoreAdapter;
import com.zczczy.leo.fuwuwangapp.items.AreaPopItemView_;
import com.zczczy.leo.fuwuwangapp.items.CategoryPopItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.listener.PopupItemClickCallBack;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by Leo on 2016/5/10.
 */
@EActivity(R.layout.activity_service_goods)
public class ServiceGoodsActivity extends BaseUltimateRecyclerViewActivity<StoreDetailModel> implements PopupItemClickCallBack {

    @ViewById
    TextView txt_area, txt_category;

    @ViewById
    ImageView img_area, img_category;

    @ViewById
    LinearLayout ll_area, ll_category, root;

    @DrawableRes
    Drawable popBackground;

    @Pref
    MyPrefs_ pre;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Extra
    int typeId;

    @Extra
    String typeName;

    PopupWindow areaPopWin, categoryPopWin;


    @Bean
    void setAdapter(StoreAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        txt_category.setText(typeName);
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<StoreDetailModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, StoreDetailModel obj, int position) {
                StoreInformationActivity_.intent(ServiceGoodsActivity.this).storeId(obj.StoreInfoId).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
        myTitleBar.setCustomViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity_.intent(ServiceGoodsActivity.this).isService(true).start();
            }
        });
    }

    void afterLoadMore() {
        Integer StreetInfoId = null;
        Integer GoodsTypeId = null;
        Integer TwoGoodsTypeId = null;
        String CityId = null;
        String AreaId = null;
        if (app.getNewRegion() == null && app.getNewStreet() == null) {
            CityId = pre.cityId().get();
        } else if (app.getNewStreet() != null && app.getNewRegion() != null) {
            if (app.getNewRegion().AreaId.equals(app.getNewStreet().StreetInfoId + "")) {
                AreaId = app.getNewRegion().AreaId;
            } else {
                StreetInfoId = app.getNewStreet().StreetInfoId;
            }
        }

        if (app.getFirstCategory() == null && app.getSecondCategory() == null) {
            GoodsTypeId = typeId;
        } else if (app.getFirstCategory() != null && app.getSecondCategory() != null) {
            if (app.getFirstCategory().GoodsTypeId == app.getSecondCategory().GoodsTypeId) {
                GoodsTypeId = app.getSecondCategory().GoodsTypeId;
            } else {
                TwoGoodsTypeId = app.getSecondCategory().GoodsTypeId;
            }
        }
        myAdapter.getMoreData(pageIndex, Constants.PAGE_COUNT, isRefresh, 1, StreetInfoId, GoodsTypeId, TwoGoodsTypeId, CityId, AreaId);
    }

    @Click
    void ll_category() {
        categoryPopWin = new PopupWindow(CategoryPopItemView_.build(this).setCallBack(this), ViewGroup.LayoutParams.MATCH_PARENT, root.getHeight() * 2 / 3, true);
        categoryPopWin.setBackgroundDrawable(popBackground);
        categoryPopWin.showAsDropDown(ll_category, 5, 1);
        categoryPopWin.update();
        ObjectAnimator oa = ObjectAnimator.ofFloat(img_category, "rotation", 360f, 180f);
        oa.setDuration(300);
        oa.start();
        categoryPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ObjectAnimator oa = ObjectAnimator.ofFloat(img_category, "rotation", 180f, 360f);
                oa.setDuration(300);
                oa.start();
            }
        });
    }

    @Click
    void ll_area() {
        areaPopWin = new PopupWindow(AreaPopItemView_.build(this).setCallBack(this), ViewGroup.LayoutParams.MATCH_PARENT, root.getHeight() * 2 / 3, true);
        areaPopWin.setBackgroundDrawable(popBackground);
        areaPopWin.showAsDropDown(ll_area, 5, 1);
        areaPopWin.update();
        ObjectAnimator oa = ObjectAnimator.ofFloat(img_area, "rotation", 360f, 180f);
        oa.setDuration(300);
        oa.start();
        areaPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ObjectAnimator oa = ObjectAnimator.ofFloat(img_area, "rotation", 180f, 360f);
                oa.setDuration(300);
                oa.start();
            }
        });
    }


    @Override
    public void callBackCategory() {
        if (app.getSecondCategory() != null) {
            txt_category.setText(app.getSecondCategory().GoodsTypeName);
        }
        isRefresh = true;
        pageIndex = 1;
        afterLoadMore();
        categoryPopWin.dismiss();
    }

    @Override
    public void callBack(Object... objects) {
        if (app.getNewStreet() != null) {
            txt_area.setText(app.getNewStreet().StreetName);
        }
        isRefresh = true;
        pageIndex = 1;
        afterLoadMore();
        areaPopWin.dismiss();

    }

    public void finish() {
        app.setFirstCategory(null);
        app.setSecondCategory(null);
        app.setNewRegion(null);
        app.setNewStreet(null);
        super.finish();
    }


    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        bus.register(this);
        super.onResume();
    }


}
