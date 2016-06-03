package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CommonSearchResultActivity_;
import com.zczczy.leo.fuwuwangapp.activities.CommonWebViewActivity_;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailInfoActivity_;
import com.zczczy.leo.fuwuwangapp.activities.LotteryInfoRecordActivity_;
import com.zczczy.leo.fuwuwangapp.activities.StoreInformationActivity_;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.annotations.res.StringRes;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
@EViewGroup(R.layout.fragment_home_advertisement)
public class HomeAdvertisementItemView extends ItemView<List<AdvertModel>> implements BaseSliderView.OnSliderClickListener {

    @ViewById
    SliderLayout new_slider_Layout;

    @ViewById
    ImageView ad_one, ad_two, ad_three, ad_four, ad_five, ad_six, ad_seven, ad_eight, ad_nine, img_winners_order;

    @ViewsById({R.id.img_trendw, R.id.img_trendm, R.id.img_huazhuang, R.id.img_digital, R.id.img_baby, R.id.img_xxxb, R.id.img_life, R.id.img_food, R.id.img_healthcare, R.id.img_lbdh})
    List<ImageView> imageViews;

    @ViewsById({R.id.text_trendw, R.id.text_trendm, R.id.text_huazhuang, R.id.text_digital, R.id.text_baby, R.id.text_xxxb, R.id.text_life, R.id.text_food, R.id.text_healthcare, R.id.text_lbdh})
    List<TextView> textViews;

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
        new_slider_Layout.removeAllViews();
        for (NewBanner nb : app.getNewBannerList()) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView.image(nb.BannerImgUrl);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bannerModel", nb);
            textSliderView.bundle(bundle);
            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.setOnSliderClickListener(this);
            new_slider_Layout.addSlider(textSliderView);
        }
        if ("1".equals(app.getLotteryConfig().AppHomeIsShow)) {
            img_winners_order.setVisibility(VISIBLE);
            Picasso.with(context).load(app.getLotteryConfig().AppLotteryImgUrl).into(img_winners_order);
        } else {
            img_winners_order.setVisibility(GONE);
        }

        for (AdvertModel am : app.getAdvertModelList()) {
            if (!StringUtils.isEmpty(am.AdvertImg)) {
                if (am.AdsenseTypeId == 3) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_one.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_one);
                } else if (am.AdsenseTypeId == 4) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_two.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_two);
                } else if (am.AdsenseTypeId == 5) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_three.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_three);
                } else if (am.AdsenseTypeId == 6) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_four.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_four);
                } else if (am.AdsenseTypeId == 7) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_five.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_five);
                } else if (am.AdsenseTypeId == 8) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_six.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_six);
                } else if (am.AdsenseTypeId == 9) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_seven.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_seven);
                } else if (am.AdsenseTypeId == 10) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_eight.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_eight);
                } else if (am.AdsenseTypeId == 11) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_nine.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_nine);
                }
            }
        }
        int i = 0;
        for (GoodsTypeModel gtm : app.getGoodsTypeModelList()) {
            RequestCreator rc = Picasso.with(context).load(gtm.GoodsTypeIcon);
            //.error(R.drawable.goods_default).placeholder(R.drawable.goods_default)
            rc.into(imageViews.get(i));
            imageViews.get(i).setContentDescription(gtm.GoodsTypeId + "");
            textViews.get(i).setText(gtm.GoodsTypeName);
            i++;
            if (i == 10) {
                break;
            }
        }
    }

    public void stopAutoCycle() {
        new_slider_Layout.stopAutoCycle();
    }

    public void startAutoCycle() {
        new_slider_Layout.startAutoCycle();
    }

    @Click(value = {R.id.ad_one, R.id.ad_two, R.id.ad_three, R.id.ad_four, R.id.ad_five, R.id.ad_six, R.id.ad_seven, R.id.ad_eight, R.id.ad_nine})
    void adClick(ImageView view) {
        if (view.getContentDescription() != null) {
            String[] temp = view.getContentDescription().toString().split(",");
            if (temp.length == 2) {
                //1跳转到店铺页面  2跳转到商品页面
                if ("1".equals(temp[0])) {
                    StoreInformationActivity_.intent(context).storeId(temp[1]).start();
                } else {
                    GoodsDetailInfoActivity_.intent(context).goodsId(temp[1]).start();
                }
            }
        }
    }

    @Click(value = {R.id.img_trendw, R.id.img_trendm, R.id.img_huazhuang, R.id.img_digital, R.id.img_baby, R.id.img_xxxb, R.id.img_life, R.id.img_food, R.id.img_healthcare, R.id.img_lbdh})
    void goodsTypeClick(ImageView view) {
        if (view.getContentDescription() != null) {
            CommonSearchResultActivity_.intent(context).goodsTypeId(Integer.valueOf(view.getContentDescription().toString())).isStart(true).start();
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
        if (slider.getBundle() != null && slider.getBundle().get("bannerModel") != null) {
            NewBanner bannerModel = (NewBanner) slider.getBundle().get("bannerModel");
            if (bannerModel != null) {
                //链接分类(1:商品详细，2：网页WebView)
                if (bannerModel.LinkType == 1) {
                    GoodsDetailInfoActivity_.intent(context).goodsId(bannerModel.LinkUrl).start();
                } else if (bannerModel.LinkType == 2) {
                    CommonWebViewActivity_.intent(context).title(bannerModel.BannerName).methodName(bannerModel.LinkUrl).start();
                }
            }
        }
    }
}
