package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.LotteryInfoRecordActivity_;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
@EViewGroup(R.layout.fragment_home_advertisement)
public class HomeAdvertisementItemView extends ItemView<List<AdvertModel>> implements BaseSliderView.OnSliderClickListener {

    @ViewById
    SliderLayout new_slider_Layout;

    @ViewById
    ImageView ad_one, ad_two, ad_three, ad_four, ad_five, ad_six, ad_seven, ad_eight, ad_nine, img_winners_order,
            img_trendw, img_trendm, img_huazhuang, img_digital, img_baby, img_life, img_food, img_healthcare, img_service, img_whole;

    @ViewById
    TextView text_trendw, text_trendm, text_huazhuang, text_digital, text_baby, text_life, text_food, text_healthcare, text_service, text_whole;

    ImageView[] imageViews = new ImageView[10];

    TextView[] textViews = new TextView[10];

    Context context;

    @App
    MyApplication app;

    @StringRes
    String winners_notice;

    public HomeAdvertisementItemView(Context context) {
        super(context);
        this.context = context;


    }

    @UiThread
    void afterGetData() {
        init();
    }

    @Override
    protected void init(Object... objects) {

        imageViews[0] = img_trendw;
        imageViews[1] = img_trendm;
        imageViews[2] = img_huazhuang;
        imageViews[3] = img_digital;
        imageViews[4] = img_baby;
        imageViews[5] = img_life;
        imageViews[6] = img_food;
        imageViews[7] = img_healthcare;
        imageViews[8] = img_service;
//        imageViews[9] = img_whole;

        textViews[0] = text_trendw;
        textViews[1] = text_trendm;
        textViews[2] = text_huazhuang;
        textViews[3] = text_digital;
        textViews[4] = text_baby;
        textViews[5] = text_life;
        textViews[6] = text_food;
        textViews[7] = text_healthcare;
        textViews[8] = text_service;
//        textViews[9] = text_whole;


        if (!app.isFirst()) {
            for (NewBanner nb : app.getNewBannerList()) {
                TextSliderView textSliderView = new TextSliderView(context);
                textSliderView.image(nb.BannerImgUrl);
                textSliderView.setOnSliderClickListener(this);
                new_slider_Layout.addSlider(textSliderView);
            }
            app.setFirst(true);
        }

        if ("1".equals(app.getLotteryConfig().AppHomeIsShow)) {
            img_winners_order.setVisibility(VISIBLE);
            Picasso.with(context).load(app.getLotteryConfig().AppLotteryImgUrl).into(img_winners_order);
        } else {
            img_winners_order.setVisibility(GONE);
        }

        for (AdvertModel am : app.getAdvertModelList()) {
            RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
            if (am.AdsenseTypeId == 3) {
                rc.into(ad_one);
            } else if (am.AdsenseTypeId == 4) {
                rc.into(ad_two);
            } else if (am.AdsenseTypeId == 5) {
                rc.into(ad_three);
            } else if (am.AdsenseTypeId == 6) {
                rc.into(ad_four);
            } else if (am.AdsenseTypeId == 7) {
                rc.into(ad_five);
            } else if (am.AdsenseTypeId == 8) {
                rc.into(ad_six);
            } else if (am.AdsenseTypeId == 9) {
                rc.into(ad_seven);
            } else if (am.AdsenseTypeId == 10) {
                rc.into(ad_eight);
            } else if (am.AdsenseTypeId == 11) {
                rc.into(ad_nine);
            }
        }

        int i = 0;
        for (GoodsTypeModel gtm : app.getGoodsTypeModelList()) {
            RequestCreator rc = Picasso.with(context).load(gtm.GoodsTypeIcon).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
            rc.into(imageViews[i]);
            textViews[i].setText(gtm.GoodsTypeName);
            i++;
        }
    }

    @Click
    void img_winners_order() {
        LotteryInfoRecordActivity_.intent(context).title(winners_notice).method(1).start();
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
