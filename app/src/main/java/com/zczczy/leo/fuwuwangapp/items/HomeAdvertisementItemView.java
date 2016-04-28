package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
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

    @RestService
    MyDotNetRestClient myDotNetRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    Context context;

    public HomeAdvertisementItemView(Context context) {
        super(context);
        this.context = context;

    }

    @AfterViews
    void  afterView(){
        myDotNetRestClient.setRestErrorHandler(myErrorHandler);
        GetNewBanner();
    }

    @Background
    void GetNewBanner(){
        BaseModelJson<List<NewBanner>> bmj=myDotNetRestClient.GetHomeBanner();
        afterGetNewBanner(bmj);
    }
    @UiThread
    void afterGetNewBanner(BaseModelJson<List<NewBanner>>bmj){
        if (bmj!=null&&bmj.Successful){
            for (int i = 0; i < bmj.Data.size(); i++) {
                TextSliderView textSliderView = new TextSliderView(context);
                //textSliderView.description(bmj.Data.get(i).aname)
                textSliderView.image(bmj.Data.get(i).BannerImgUrl);
                textSliderView.setOnSliderClickListener(this);
                new_slider_Layout.addSlider(textSliderView);
            }

        }

    }


    @Override
    protected void init(Object... objects) {

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
