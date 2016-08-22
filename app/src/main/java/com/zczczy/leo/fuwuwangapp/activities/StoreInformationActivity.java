package com.zczczy.leo.fuwuwangapp.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.fragments.CategoryFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.MineFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.ServiceFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.StoreAllGoodsFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.StoreHomeFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.StoreNewArrivalFragment_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;
import com.zczczy.leo.fuwuwangapp.viewgroup.FragmentTabHost;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/5.
 */
@EActivity(R.layout.activity_store_information)
public class StoreInformationActivity extends BaseActivity {

    @ViewById
    public FragmentTabHost tabHost;

    @ViewById
    MyTitleBar myTitleBar;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @StringArrayRes
    String[] storeTabTitle, storeTabTag;

    @ViewById
    ImageView img_index;

    @ViewById
    RatingBar ratingBar;

    @ViewById(android.R.id.tabs)
    TabWidget tabWidget;

    @ViewById
    TextView txt_store_name, txt_title_search;

    Class[] classTab = {StoreHomeFragment_.class, StoreAllGoodsFragment_.class, StoreNewArrivalFragment_.class, MineFragment_.class};

    @DrawableRes
    Drawable store_home_selector, store_all_goods_selector, store_new_arrival_selector, store_category_selector;

    Drawable[] drawables = new Drawable[4];

    @Extra
    String storeId;

    @StringRes
    String search_store_hint;

    StoreDetailModel storeDetailModel;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        drawables[0] = store_home_selector;
        drawables[1] = store_all_goods_selector;
        drawables[2] = store_new_arrival_selector;
        drawables[3] = store_category_selector;
    }


    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        txt_title_search.setText(search_store_hint);
        myTitleBar.setCustomViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity_.intent(StoreInformationActivity.this).isStore(true).storeId(storeId).start();
            }
        });
        getStoreInfo();
    }

    @Background
    void getStoreInfo() {
        afterGetStoreInfo(myRestClient.getStoreDetailById(storeId));
    }

    @UiThread
    void afterGetStoreInfo(BaseModelJson<StoreDetailModel> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
        } else {
            if (Constants.STORE_STATE_ACTIVITY.equals(bmj.Data.StoreStatus)) {
                storeDetailModel = bmj.Data;
                if (!StringUtils.isEmpty(bmj.Data.StoreIndexImg)) {
                    Glide.with(this).load(bmj.Data.StoreIndexImg)
                            .asGif()
                            .crossFade()
                            .centerCrop()
                            .error(R.drawable.store_index)
                            .placeholder(R.drawable.store_index).into(img_index);
                }
                txt_store_name.setText(bmj.Data.StoreName);
                ratingBar.setRating(bmj.Data.StorePX);
            } else {
                AndroidTool.showToast(this, "该店铺已锁定");
                finish();
            }
        }
        initTab();
    }


    protected void initTab() {
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        for (int i = 0; i < storeTabTag.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("storeId", storeId);
            bundle.putSerializable("storeDetailModel", storeDetailModel);
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(storeTabTag[i]);
            tabSpec.setIndicator(buildIndicator(i));
            tabHost.addTab(tabSpec, classTab[i], bundle);
        }
        tabHost.setCurrentTab(0);
        tabWidget.getChildTabViewAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AndroidTool.showToast(StoreInformationActivity.this, "111111111");
            }
        });
    }

    protected View buildIndicator(int position) {
        View view = layoutInflater.inflate(R.layout.tab_indicator, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);
        imageView.setImageDrawable(drawables[position]);
        TextView textView = (TextView) view.findViewById(R.id.text_indicator);
        textView.setText(storeTabTitle[position]);
        return view;
    }


}
