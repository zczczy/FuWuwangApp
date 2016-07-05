package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.fragments.GoodsCommentsFragment;
import com.zczczy.leo.fuwuwangapp.fragments.GoodsCommentsFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.GoodsDetailFragment;
import com.zczczy.leo.fuwuwangapp.fragments.GoodsDetailFragment_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * @author Created by LuLeo on 2016/7/4.
 *         you can contact me at :361769045@qq.com
 * @since 2016/7/4.
 */
@EActivity(R.layout.new_activity_goods_detail)
public class GoodsDetailActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    TextView txt_goods_name, txt_coupon, txt_store_name;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    @ViewById
    RadioButton rb_good_detail, rb_good_review;

    @Extra
    String goodsId;

    @ViewById
    SliderLayout sliderLayout;

    FragmentManager fragmentManager;

    GoodsDetailFragment goodsDetailFragment;

    GoodsCommentsFragment goodsCommentsFragment;

    String linkUrl, PlUrl;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        fragmentManager = getSupportFragmentManager();
    }

    @AfterViews
    void afterView() {
        myTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUserIsLogin()) {
                    CartActivity_.intent(GoodsDetailActivity.this).start();
                } else {
                    LoginActivity_.intent(GoodsDetailActivity.this).start();
                }
            }
        });
        getGoodsDetailById(goodsId);
    }

    @CheckedChange
    void rb_good_detail(boolean isChecked) {
        if (isChecked) {
            changeFragment(linkUrl);
        } else {
            changeFragment(goodsId);
        }
    }

    void changeFragment(String parameter) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (goodsDetailFragment != null) {
            transaction.hide(goodsDetailFragment);
        }
        if (goodsCommentsFragment != null) {
            transaction.hide(goodsCommentsFragment);
        }
        if (rb_good_detail.isChecked()) {
            if (goodsDetailFragment == null) {
                goodsDetailFragment = GoodsDetailFragment_.builder().linkUrl(parameter).build();
                transaction.add(R.id.goods_detail_fragment, goodsDetailFragment);
            } else {
                transaction.show(goodsDetailFragment);
            }
        } else {
            if (goodsCommentsFragment == null) {
//                goodsCommentsFragment = GoodsDetailFragment_.builder().linkUrl(parameter).build();
                goodsCommentsFragment = GoodsCommentsFragment_.builder().goodsId(parameter).build();
                transaction.add(R.id.goods_detail_fragment, goodsCommentsFragment);
            } else {
                transaction.show(goodsCommentsFragment);
            }
        }
        //transaction.commit();
        transaction.commitAllowingStateLoss();
    }

    @Background
    void getGoodsDetailById(String goodsInfoId) {
        afterGetGoodsDetailById(myRestClient.getGoodsDetailById(goodsInfoId));
    }

    @UiThread
    void afterGetGoodsDetailById(BaseModelJson<Goods> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {

        } else {

        }
    }

    @Click
    void ll_review() {

    }

    @Click
    void txt_more_review() {

    }

    @Click
    void txt_store() {

    }

}
