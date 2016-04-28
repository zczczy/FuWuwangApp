package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.PaperFace;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyAlertDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_paper_coupon_queue_wx)
public class PaperCouponQueueWxActivity extends BaseActivity {

    //兑现券编号
    String coupon_code;
    //兑现券密码
    String coupon_pass;

    @ViewById
    EditText edt_coupon_code, edt_coupon_pass;

    @ViewById
    Button btn_paper_transfer;

    @StringRes
    String txt_ptzqpd;

    @Pref
    MyPrefs_ pre;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyRestClient myRestClient;

    @AfterInject
    void afterInject(){
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    //纸卷排队
    @Click
    void btn_paper_transfer(){
        if(isNetworkAvailable(this)){
            coupon_code = edt_coupon_code.getText().toString();
            coupon_pass = edt_coupon_pass.getText().toString();
            if(coupon_code==""||coupon_code==null||coupon_code.isEmpty()){
                MyAlertDialog dialog = new MyAlertDialog(this,"兑现劵编号不能为空",null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if(coupon_pass==""||coupon_pass==null||coupon_pass.isEmpty()){
                MyAlertDialog dialog = new MyAlertDialog(this,"兑现劵密码不能为空",null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            AndroidTool.showCancelabledialog(this);
            loginQueue();
        }
        else{
            Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void loginQueue(){
        String token = pre.token().get();
        myRestClient.setHeader("Token",token);
        BaseModelJson<PaperFace> bmj = myRestClient.LoginQueue(coupon_code, coupon_pass);
        showPaper(bmj);
    }

    @UiThread
    void showPaper(BaseModelJson<PaperFace> bmj){
        AndroidTool.dismissLoadDialog();
        if (bmj!=null) {
            if (bmj.Successful) {
                //跳转到纸劵排队页
                PaperCouponQueueActivity_.intent(this).qi_id(bmj.Data.getQi_id()).queuesId(bmj.Data.getQueuesId()).queuesInRule(bmj.Data.getQueuesInRule()).start();
            } else {
                MyAlertDialog dialog = new MyAlertDialog(this, bmj.Error, null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        }
    }
}
