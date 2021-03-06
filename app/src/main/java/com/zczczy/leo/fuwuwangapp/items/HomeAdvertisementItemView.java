package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CategoryActivity_;
import com.zczczy.leo.fuwuwangapp.activities.CommonSearchResultActivity_;
import com.zczczy.leo.fuwuwangapp.activities.CommonWebViewActivity_;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailActivity_;
import com.zczczy.leo.fuwuwangapp.activities.LotteryInfoRecordActivity_;
import com.zczczy.leo.fuwuwangapp.activities.StoreInformationActivity_;
import com.zczczy.leo.fuwuwangapp.activities.WebViewActivity_;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;
import com.zczczy.leo.fuwuwangapp.views.GlideSliderView;

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
        new_slider_Layout.removeAllSliders();
        for (NewBanner nb : app.getNewBannerList()) {
            GlideSliderView textSliderView = new GlideSliderView(context);
            textSliderView.image(nb.BannerImgUrl);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bannerModel", nb);
            textSliderView.bundle(bundle).empty(R.drawable.home_banner).
                    error(R.drawable.home_banner).
                    setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);
            new_slider_Layout.addSlider(textSliderView);
        }
        if ("1".equals(app.getLotteryConfig().AppHomeIsShow)) {
            img_winners_order.setVisibility(VISIBLE);
            Glide.with(context).load(app.getLotteryConfig().AppLotteryImgUrl)
                    .fitCenter()
                    .crossFade()
                    .skipMemoryCache(true)
                    .error(R.drawable.home_winners_order)
                    .placeholder(R.drawable.home_winners_order)
                    .into(img_winners_order);
        } else {
            img_winners_order.setVisibility(GONE);
        }

        for (AdvertModel am : app.getAdvertModelList()) {
            if (!StringUtils.isEmpty(am.AdvertImg)) {
                DrawableRequestBuilder<String> glide = Glide.with(context).load(am.AdvertImg).centerCrop().crossFade().skipMemoryCache(true);

                if (am.AdsenseTypeId == 3) {
                    ad_one.setContentDescription(am.JumpType + "," + am.InfoId);
                    glide.error(R.drawable.home_ad_one)
                            .placeholder(R.drawable.home_ad_one)
                            .into(ad_one);
                } else if (am.AdsenseTypeId == 4) {
                    ad_two.setContentDescription(am.JumpType + "," + am.InfoId);
                    glide.error(R.drawable.home_ad_one)
                            .placeholder(R.drawable.home_ad_one)
                            .into(ad_two);
                } else if (am.AdsenseTypeId == 5) {
                    ad_three.setContentDescription(am.JumpType + "," + am.InfoId);
                    glide.error(R.drawable.home_ad_one)
                            .placeholder(R.drawable.home_ad_one)
                            .into(ad_three);
                } else if (am.AdsenseTypeId == 6) {
                    ad_four.setContentDescription(am.JumpType + "," + am.InfoId);
                    glide.error(R.drawable.home_ad_one)
                            .placeholder(R.drawable.home_ad_one)
                            .into(ad_four);
                } else if (am.AdsenseTypeId == 7) {
                    ad_five.setContentDescription(am.JumpType + "," + am.InfoId);
                    glide.error(R.drawable.home_ad_five)
                            .placeholder(R.drawable.home_ad_five)
                            .into(ad_five);
                } else if (am.AdsenseTypeId == 8) {
                    ad_six.setContentDescription(am.JumpType + "," + am.InfoId);
                    glide.error(R.drawable.home_ad_five)
                            .placeholder(R.drawable.home_ad_five)
                            .into(ad_six);
                } else if (am.AdsenseTypeId == 9) {
                    ad_seven.setContentDescription(am.JumpType + "," + am.InfoId);
                    glide.error(R.drawable.home_ad_five)
                            .placeholder(R.drawable.home_ad_five)
                            .into(ad_seven);
                } else if (am.AdsenseTypeId == 10) {
                    ad_eight.setContentDescription(am.JumpType + "," + am.InfoId);
                    glide.error(R.drawable.home_ad_five)
                            .placeholder(R.drawable.home_ad_five)
                            .into(ad_eight);
                } else if (am.AdsenseTypeId == 11) {
                    ad_nine.setContentDescription(am.JumpType + "," + am.InfoId);
                    glide.error(R.drawable.home_winners_order)
                            .placeholder(R.drawable.home_winners_order)
                            .into(ad_nine);
                }
            }
        }
        int i = 0;
        for (GoodsTypeModel gtm : app.getGoodsTypeModelList()) {
            Glide.with(context).load(gtm.GoodsTypeIcon).skipMemoryCache(true)
                    .crossFade()
                    .centerCrop().into(imageViews.get(i));
            imageViews.get(i).setContentDescription(gtm.GoodsTypeId + "," + gtm.GoodsTypeName);
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
                } else if ("2".equals(temp[0])) {
                    GoodsDetailActivity_.intent(context).goodsId(temp[1]).start();
                } else if ("3".equals(temp[0])) {
                    CommonWebViewActivity_.intent(context).title("详情").methodName(temp[1]).start();
                } else if ("4".equals(temp[0])) {
//                    CategoryActivity_.intent(context).goodsTypeId(temp[1]).goodsType("2").title("详情").start();
                    CommonSearchResultActivity_.intent(context).goodsTypeId(Integer.valueOf(temp[1])).start();
                }
            }
        }
    }

    @Click(value = {R.id.img_trendw, R.id.img_trendm, R.id.img_huazhuang, R.id.img_digital, R.id.img_baby, R.id.img_xxxb, R.id.img_life, R.id.img_food, R.id.img_healthcare, R.id.img_lbdh})
    void goodsTypeClick(ImageView view) {
        if (view.getContentDescription() != null) {
            String[] temp = view.getContentDescription().toString().split(",");
            if (temp.length == 2) {
                CategoryActivity_.intent(context).goodsTypeId(temp[0]).goodsType("2").title(temp[1]).start();
            }
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
                    GoodsDetailActivity_.intent(context).goodsId(bannerModel.LinkUrl).start();
                } else if (bannerModel.LinkType == 2) {
                    CommonWebViewActivity_.intent(context).title(bannerModel.BannerName).methodName(bannerModel.LinkUrl).start();
                } else if (bannerModel.LinkType == 3) {
//                    CategoryActivity_.intent(context).goodsTypeId(bannerModel.LinkUrl).goodsType("2").title(bannerModel.BannerName).start();
                    CommonSearchResultActivity_.intent(context).goodsTypeId(Integer.valueOf(bannerModel.LinkUrl)).start();
                } else if (bannerModel.LinkType == 4) {
                    StoreInformationActivity_.intent(context).storeId(bannerModel.LinkUrl).start();
                }
            }
        }
    }
}
