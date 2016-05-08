package com.zczczy.leo.fuwuwangapp.activities;

import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CouponManageInfoAdapter;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.QueueMDetailModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_coupon_manager_info)
public class CouponManageInfoActivity extends BaseActivity {

    @ViewById
    TextView volume_txt, volume_personal, volume_bussiness, volume_duilie;

    @Extra
    String mianzhi, duilie, guize;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    @Pref
    MyPrefs_ pre;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(CouponManageInfoAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    LinearLayoutManager linearLayoutManager;

    Paint paint = new Paint();

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        volume_txt.setText(mianzhi);
        volume_duilie.setText(duilie);
        getHttp();
        ultimateRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(myAdapter);
        myAdapter.getMoreData(0, 10, false,guize);
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        ultimateRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(35).paint(paint).build());
    }

    @Background
    void getHttp() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<QueueMDetailModel> bmj = myRestClient.QueueMDetail(guize);
        show(bmj);
    }

    @UiThread
    void show(BaseModelJson<QueueMDetailModel> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                volume_personal.setText(bmj.Data.getCntgeren());
                volume_bussiness.setText(bmj.Data.getCntcom());
            }
        }
    }
}
