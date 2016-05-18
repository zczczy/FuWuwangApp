package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Wealth;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_wealth)
public class WealthActivity extends BaseActivity {

    @ViewById
    TextView txt_e_purse, txt_transfer, txt_withdraw, txt_bill, txt_stamp, txt_ticket, txt_total_dou, txt_bi, txt_dou, txt_status;

    @ViewById
    LinearLayout ll_stamp, ll_ticket, ll_total_dou, ll_bi, ll_dou, ll_status;

    @StringRes
    String txt_w_wealth;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;


    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        getHttp();
    }

    @Background
    void getHttp() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<Wealth> bmj = myRestClient.GetWealth();
        updateWealth(bmj);
    }

    @UiThread
    void updateWealth(BaseModelJson<Wealth> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                txt_e_purse.setText(bmj.Data.getBlance());
                txt_status.setText(bmj.Data.getUserType());
                txt_dou.setText(bmj.Data.getSelfPoint());
                txt_bi.setText(bmj.Data.getLongbicount());
                txt_total_dou.setText(bmj.Data.getAllPoint());
                txt_ticket.setText(bmj.Data.getQichejifen());
                txt_stamp.setText(bmj.Data.getLvyoujifen());
            }
        }
    }

    //转账
    @Click
    void txt_transfer() {
        TransferActivity_.intent(this).startForResult(1000);
    }

    //提现
    @Click
    void txt_withdraw() {
        WithDrawActivity_.intent(this).startForResult(1000);
    }

    //账单
    @Click
    void txt_bill() {
        if (isNetworkAvailable(this)) {
            TransactionRecordActivity_.intent(this).start();
        } else {
            Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
        }
    }

    @OnActivityResult(1000)
    void onResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            AndroidTool.showLoadDialog(this);
            getHttp();
        }
    }

}
