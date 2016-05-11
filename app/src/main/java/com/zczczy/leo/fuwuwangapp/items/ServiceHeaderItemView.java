package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CommonSearchResultActivity_;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailInfoActivity_;
import com.zczczy.leo.fuwuwangapp.activities.ServiceGoodsActivity_;
import com.zczczy.leo.fuwuwangapp.activities.StoreInformationActivity_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.rest.MyBackgroundTask;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Leo on 2016/5/9.
 */
@EViewGroup(R.layout.fragment_service_header)
public class ServiceHeaderItemView extends ItemView<AdvertModel> {

    @ViewById
    ImageView ad_one, ad_two, ad_three, ad_four, ad_five, ad_six, ad_seven, ad_eight, ad_nine;


    @ViewsById(value = {R.id.img_food, R.id.img_longbi, R.id.img_yule, R.id.img_hotel, R.id.img_liren, R.id.img_around})
    List<ImageView> imageViews;

    @ViewsById(value = {R.id.text_food, R.id.text_longbi, R.id.text_yule, R.id.text_hotel, R.id.text_liren, R.id.text_around})
    List<TextView> textViews;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @App
    MyApplication app;

    @Bean
    MyBackgroundTask myBackgroundTask;

    Context context;

    public ServiceHeaderItemView(Context context) {
        super(context);
        this.context = context;
    }

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        init();
    }

    @Background
    public void getServiceAd() {
        afterGetServiceAd(myRestClient.getAdvertByKbn(MyApplication.SERVIE_CATEGORY));
    }

    @UiThread
    void afterGetServiceAd(BaseModelJson<List<AdvertModel>> bmj) {
        if (bmj != null && bmj.Successful) {
            app.setServiceAdvertModelList(bmj.Data);
            setAd();
        }
    }

    @Override
    protected void init(Object... objects) {
        if (app.getServiceAdvertModelList().size() > 0) {
            setAd();
        } else {
            getServiceAd();
        }

        //设置分类
        int i = 0;
        for (GoodsTypeModel gtm : app.getServiceGoodsTypeModelList()) {
            RequestCreator rc = Picasso.with(context).load(gtm.GoodsTypeIcon);
//                    .error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
            rc.into(imageViews.get(i));
            imageViews.get(i).setContentDescription(gtm.GoodsTypeId + "");
            textViews.get(i).setText(gtm.GoodsTypeName);
            i++;
            if (i == 6) {
                break;
            }
        }

    }

    void setAd() {
        for (AdvertModel am : app.getServiceAdvertModelList()) {
            if (!StringUtils.isEmpty(am.AdvertImg)) {
                if (am.AdsenseTypeId == 12) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_one.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_one);
                } else if (am.AdsenseTypeId == 13) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_two.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_two);
                } else if (am.AdsenseTypeId == 14) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_three.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_three);
                } else if (am.AdsenseTypeId == 15) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_four.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_four);
                } else if (am.AdsenseTypeId == 16) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_five.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_five);
                } else if (am.AdsenseTypeId == 17) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_six.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_six);
                } else if (am.AdsenseTypeId == 18) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_seven.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_seven);
                } else if (am.AdsenseTypeId == 19) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_eight.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_eight);
                } else if (am.AdsenseTypeId == 20) {
                    RequestCreator rc = Picasso.with(context).load(am.AdvertImg).error(R.drawable.goods_default).placeholder(R.drawable.goods_default);
                    ad_nine.setContentDescription(am.JumpType + "," + am.InfoId);
                    rc.into(ad_nine);
                }
            }
        }
    }

    @Click(value = {R.id.img_food, R.id.img_longbi, R.id.img_yule, R.id.img_hotel, R.id.img_liren, R.id.img_around})
    void goodsTypeClick(ImageView view) {
        if (view.getContentDescription() != null) {
            ServiceGoodsActivity_.intent(context).typeId(Integer.valueOf(view.getContentDescription().toString())).start();
        }
//        CommonSearchResultActivity_.intent(context).goodsTypeId(Integer.valueOf(view.getContentDescription().toString())).start();
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

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
