package com.zczczy.leo.fuwuwangapp.activities;

import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.rest.MyBackgroundTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_index)
public class IndexActivity extends BaseActivity {

    @Bean
    MyBackgroundTask myBackgroundTask;


    @Bean
    OttoBus bus;

    int i = 0;

    @AfterViews
    void afterView() {
        bus.register(this);
        myBackgroundTask.getAdvertByKbn();
        myBackgroundTask.getHomeBanner();
        myBackgroundTask.getLotteryConfigInfo();
        myBackgroundTask.getHomeGoodsTypeList();
    }

    @Override
    public void finish() {
        bus.unregister(this);
        super.finish();
    }

    @Subscribe
    public void notifyUI(BaseModel bm) {
        i++;
        if (i == 4) {
            MainActivity_.intent(this).start();
            finish();
        }
    }
}
