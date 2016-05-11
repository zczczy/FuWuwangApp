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
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.StoreAdapter;
import com.zczczy.leo.fuwuwangapp.items.AreaPopItemView_;
import com.zczczy.leo.fuwuwangapp.items.CategoryPopItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.listener.PopupItemClickCallBack;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CityModel;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by Leo on 2016/5/10.
 */
@EActivity(R.layout.activity_service_goods)
public class ServiceGoodsActivity extends BaseActivity implements PopupItemClickCallBack {


    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(StoreAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    @ViewById
    TextView txt_area, txt_category;

    @ViewById
    ImageView img_area, img_category;

    @ViewById
    LinearLayout ll_area, ll_category, root;

    @Bean
    OttoBus bus;

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

    LinearLayoutManager linearLayoutManager;

    MaterialHeader materialHeader;


    PopupWindow areaPopWin, categoryPopWin;

    int pageIndex = 1;

    boolean isRefresh;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        ultimateRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(myAdapter);
        ultimateRecyclerView.enableLoadmore();
        myAdapter.setCustomLoadMoreView(R.layout.custom_bottom_progressbar);
        afterLoadMore();
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                if (myAdapter.getItems().size() >= myAdapter.getTotal()) {
                    AndroidTool.showToast(ServiceGoodsActivity.this, "没有更多的数据了！~");
                    ultimateRecyclerView.disableLoadmore();
//                    myAdapter.notifyItemRemoved(maxLastVisiblePosition);
                } else {
                    pageIndex++;
                    afterLoadMore();
                }
            }
        });
        ultimateRecyclerView.setCustomSwipeToRefresh();
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        ultimateRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(35).paint(paint).build());
        refreshingMaterial();
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<StoreDetailModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, StoreDetailModel obj, int position) {
                StoreInformationActivity_.intent(ServiceGoodsActivity.this).storeId(obj.StoreInfoId).start();

            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
    }

    void refreshingMaterial() {
        materialHeader = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        materialHeader.setColorSchemeColors(colors);
        materialHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        materialHeader.setPadding(0, 15, 0, 10);
        materialHeader.setPtrFrameLayout(ultimateRecyclerView.mPtrFrameLayout);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh = true;
                pageIndex = 1;
                afterLoadMore();
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
        myAdapter.getMoreData(pageIndex, MyApplication.PAGE_COUNT, isRefresh, StreetInfoId, GoodsTypeId, TwoGoodsTypeId, CityId, AreaId);
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


    @Subscribe
    public void notifyUIi(BaseModel bm) {
        if (isRefresh) {
            linearLayoutManager.scrollToPosition(0);
            ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
            isRefresh = false;
            if (myAdapter.getTotal() > 0 && myAdapter.getItems().size() < myAdapter.getTotal()) {
                ultimateRecyclerView.reenableLoadmore(layoutInflater.inflate(R.layout.custom_bottom_progressbar, null));
            } else {
                ultimateRecyclerView.disableLoadmore();
            }
        } else if (pageIndex == 1) {
            linearLayoutManager.scrollToPosition(0);
        }
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
