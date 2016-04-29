package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsDetailModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsImgListModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyScrollView;
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
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_goods_detail_info)
public class GoodsDetailInfoActivity extends BaseActivity implements MyScrollView.OnScrollListener, BaseSliderView.OnSliderClickListener {

    @ViewById
    MyScrollView myScrollView;

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    RelativeLayout theViewStay;

    @ViewById
    View mBuyLayout;

    @ViewById
    LinearLayout parent, ll_rebate, ll_store;

    @ViewById
    TextView goods_name, goods_describe, goods_by, goods_kucun, goods_knows, txt_rebate, txt_normal, txt_longbi;

    @ViewById
    CardView card_buy;

    @Pref
    MyPrefs_ pre;

    @Extra
    String goodsId;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    @ViewById
    SliderLayout sliderLayout;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        myScrollView.setOnScrollListener(this);
        // 当布局的状态或者控件的可见性发生改变回调的接口
        parent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // 这一步很重要，使得上面的购买布局和下面的购买布局重合
                        onScroll(myScrollView.getScrollY());
                    }
                });
        getGoodsDetailById(goodsId);
    }

    @Background
    void getGoodsDetailById(String goodsInfoId) {
        afterGetGoodsDetailById(myRestClient.getGoodsDetailById(goodsInfoId));
    }

    @UiThread
    void afterGetGoodsDetailById(BaseModelJson<GoodsDetailModel> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            goods_name.setText(bmj.Data.GodosName);
            goods_describe.setText(bmj.Data.GoodsDesc);
            goods_by.setText(bmj.Data.GoodsIsBy);
            goods_kucun.setText(bmj.Data.GoodsStock);
            goods_knows.setText(bmj.Data.GoodsPurchaseNotes);
            txt_rebate.setText(bmj.Data.TempDisp + "撒旦法撒旦发射发放的阿萨德法撒旦发射点发射得分阿萨德发生大阿道夫撒旦法撒旦发射发放的阿萨德法撒旦发射点发射得分阿萨德发生大阿道夫撒旦法撒旦发射发放的阿萨德法撒旦发射点发射得分阿萨德发生大阿道夫撒旦法撒旦发射发放的阿萨德法撒旦发射点发射得分阿萨德发生大阿道夫撒旦法撒旦发射发放的阿萨德法撒旦发射点发射得分阿萨德发生大阿道夫撒旦法撒旦发射发放的阿萨德法撒旦发射点发射得分阿萨德发生大阿道夫撒旦法撒旦发射发放的阿萨德法撒旦发射点发射得分阿萨德发生大阿道夫撒旦法撒旦发射发放的阿萨德法撒旦发射点发射得分阿萨德发生大阿道夫撒旦法撒旦发射发放的阿萨德法撒旦发射点发射得分阿萨德发生大阿道夫");
            txt_normal.setText(bmj.Data.GoodsPrice);
            txt_longbi.setText(bmj.Data.GoodsLBPrice);
            for (GoodsImgListModel nb : bmj.Data.GoodsImgList) {
                TextSliderView textSliderView = new TextSliderView(this);
                textSliderView.image(nb.GoodsImgUrl);
                textSliderView.setOnSliderClickListener(this);
                sliderLayout.addSlider(textSliderView);
            }
            if (bmj.Data.GoodsImgList.size() == 0) {
                TextSliderView textSliderView = new TextSliderView(this);
                textSliderView.image(bmj.Data.GoodsImgSl);
                textSliderView.setOnSliderClickListener(this);
                sliderLayout.addSlider(textSliderView);
            }

        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }


    @Click
    void txt_buy() {
        AndroidTool.showToast(this, "buy");
    }

    @Click
    void img_cart() {
        AndroidTool.showToast(this, "cart");
    }

    /**
     * 控制购买浮动条的 位置 上面的购买布局（theViewStay）和下面的购买布局（mBuyLayout）重合起来了
     * layout()这个方法是确定View的大小和位置的
     * ，然后将其绘制出来，里面的四个参数分别是View的四个点的坐标，他的坐标不是相对屏幕的原点，而且相对于他的父布局来说的，
     */
    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, mBuyLayout.getTop());
        theViewStay.layout(0, mBuyLayout2ParentTop, theViewStay.getWidth(), mBuyLayout2ParentTop + theViewStay.getHeight());
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
