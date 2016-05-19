package com.zczczy.leo.fuwuwangapp.fragments;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.ActivityActivity_;
import com.zczczy.leo.fuwuwangapp.activities.AgentActivity_;
import com.zczczy.leo.fuwuwangapp.activities.BusinessInstituteActivity_;
import com.zczczy.leo.fuwuwangapp.activities.CooperationMerchantActivity_;
import com.zczczy.leo.fuwuwangapp.activities.ExperienceActivity_;
import com.zczczy.leo.fuwuwangapp.activities.FundActivity_;
import com.zczczy.leo.fuwuwangapp.activities.InformationActivity_;
import com.zczczy.leo.fuwuwangapp.activities.NoticeActivity_;
import com.zczczy.leo.fuwuwangapp.activities.ServiceActivity_;
import com.zczczy.leo.fuwuwangapp.model.Banner;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.viewgroup.BadgeView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
@EFragment(R.layout.fragment_news)
public class NewsFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener {

    @ViewById
    SliderLayout sliderLayout;

    @ViewById
    TextView txt_notice, txt_topic, txt_fund, txt_cooperation_merchant, txt_experience_center,
            txt_agent, txt_activity, txt_business_institute, txt_service;

    @ViewById
    LinearLayout ll_notice;

    @RestService
    MyDotNetRestClient myRestClient;

    @StringRes
    String no_net;

    @Bean
    MyErrorHandler myErrorHandler;

    static BadgeView badgeView = null;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        badgeView = new BadgeView(getActivity(), ll_notice);
        if (isNetworkAvailable(getActivity())) {
            GetBanner();
        }

    }

    public static void OpenBadgeView() {
        if (null != badgeView) {
//            if ("2".equals(MyApplication.getRmsg())) {
//                badgeView.show();
//                badgeView.setBackgroundResource(R.mipmap.bageredpoint);
//                badgeView.setVisibility(View.VISIBLE);
//            } else {
//                badgeView.setVisibility(View.GONE);
//                badgeView.hide();
//            }
        }
    }

    //查询Banner
    @Background
    void GetBanner() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        BaseModelJson<List<Banner>> bmj = myRestClient.GetTopBanner();
        afterGetBanner(bmj);
    }

    //绑定Banner
    @UiThread
    void afterGetBanner(BaseModelJson<List<Banner>> bmj) {
        if (bmj != null) {
            if (bmj.Successful) {
                for (int i = 0; i < bmj.Data.size(); i++) {
                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    //textSliderView.description(bmj.Data.get(i).aname)
                    textSliderView.image(bmj.Data.get(i).aimgurl);
                    textSliderView.setOnSliderClickListener(this);
                    sliderLayout.addSlider(textSliderView);
                }
                if (bmj.Data.size() <= 1) {
                    sliderLayout.stopAutoCycle();
                }
            }
        }
    }

    //公告
    @Click
    void txt_notice() {
        if (isNetworkAvailable(getActivity())) {
            // 点击控件小红点消失
//            MyApplication.setRmsg("1");
            OpenBadgeView();
            NoticeActivity_.intent(this).start();
        } else {
            Toast.makeText(getActivity(), no_net, Toast.LENGTH_SHORT).show();
        }
    }

    //资讯
    @Click
    void txt_topic() {
        if (isNetworkAvailable(getActivity())) {
            InformationActivity_.intent(this).start();
        } else {
            Toast.makeText(getActivity(), no_net, Toast.LENGTH_SHORT).show();
        }
    }

    //麻团基金
    @Click
    void txt_fund() {
        if (isNetworkAvailable(getActivity())) {
            FundActivity_.intent(this).start();
        } else {
            Toast.makeText(getActivity(), no_net, Toast.LENGTH_SHORT).show();
        }
    }

    //联盟商家
    @Click
    void txt_cooperation_merchant() {
        if (isNetworkAvailable(getActivity())) {
            CooperationMerchantActivity_.intent(this).start();
        } else {
            Toast.makeText(getActivity(), no_net, Toast.LENGTH_SHORT).show();
        }
    }

    //体验中心
    @Click
    void txt_experience_center() {
        if (isNetworkAvailable(getActivity())) {
            ExperienceActivity_.intent(this).start();
        } else {
            Toast.makeText(getActivity(), no_net, Toast.LENGTH_SHORT).show();
        }
    }

    //加盟代理
    @Click
    void txt_agent() {
        if (isNetworkAvailable(getActivity())) {
            AgentActivity_.intent(this).start();
        } else {
            Toast.makeText(getActivity(), no_net, Toast.LENGTH_SHORT).show();
        }
    }

    //活动
    @Click
    void txt_activity() {
        if (isNetworkAvailable(getActivity())) {
            ActivityActivity_.intent(this).start();
        } else {
            Toast.makeText(getActivity(), no_net, Toast.LENGTH_SHORT).show();
        }
    }

    //商学院
    @Click
    void txt_business_institute() {
        if (isNetworkAvailable(getActivity())) {
            BusinessInstituteActivity_.intent(this).start();
        } else {
            Toast.makeText(getActivity(), no_net, Toast.LENGTH_SHORT).show();
        }
    }

    //增值服务
    @Click
    void txt_service() {
        if (isNetworkAvailable(getActivity())) {
            ServiceActivity_.intent(this).start();
        } else {
            Toast.makeText(getActivity(), no_net, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            sliderLayout.stopAutoCycle();
        } else {
            sliderLayout.startAutoCycle();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
//        AndroidTool.showToast(getActivity(), slider.getDescription());
        sliderLayout.startAutoCycle();
    }
}

