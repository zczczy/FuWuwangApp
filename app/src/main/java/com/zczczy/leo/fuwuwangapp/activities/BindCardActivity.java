package com.zczczy.leo.fuwuwangapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * @author Created by LuLeo on 2016/6/28.
 *         you can contact me at :361769045@qq.com
 * @since 2016/6/28.
 */
@EActivity(R.layout.activity_bind_card)
public class BindCardActivity extends BaseActivity {

    @ViewById
    Button btn_bind;

    @ViewById
    EditText edt_card_no;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        myRestClient.setHeader("Token", pre.token().get());
    }

    @AfterViews
    void afterView() {
        if (isNetworkAvailable(this)) {
            AndroidTool.showLoadDialog(this);
            getMyCardNo();
        } else {

        }
    }

    @Background
    void getMyCardNo() {
        afterGetMyCardNo(myRestClient.GetMyCardNo());
    }

    @UiThread
    void afterGetMyCardNo(BaseModelJson<String> result) {
        AndroidTool.dismissLoadDialog();
        if (result == null) {
            AndroidTool.showToast(BindCardActivity.this, no_net);
        } else if (!result.Successful) {
            AndroidTool.showToast(this, result.Error);
        } else {
            if (StringUtils.isEmpty(result.Data) || "0".equals(result.Data.trim())) {
                btn_bind.setVisibility(View.VISIBLE);
            } else {
                btn_bind.setVisibility(View.GONE);
                edt_card_no.setText(result.Data);
                edt_card_no.setEnabled(false);
            }
        }
    }

    @Click
    void btn_bind() {
        if (AndroidTool.checkIsNull(edt_card_no)) {
            AndroidTool.showToast(this, "请填写会员卡");
        } else {
            AndroidTool.showLoadDialog(this);
            bindCardNo();
        }
    }

    @Background
    void bindCardNo() {
        afterBindCardNo(myRestClient.BindCardNo(edt_card_no.getText().toString().trim()));
    }

    @UiThread
    void afterBindCardNo(BaseModel result) {
        AndroidTool.dismissLoadDialog();
        if (result == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!result.Successful) {
            AndroidTool.showToast(this, result.Error);
        } else {
            AndroidTool.showToast(this, result.Error);
            btn_bind.setVisibility(View.GONE);
            edt_card_no.setEnabled(false);
        }
    }

}
