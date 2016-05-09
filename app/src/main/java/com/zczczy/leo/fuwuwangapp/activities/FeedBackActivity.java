package com.zczczy.leo.fuwuwangapp.activities;

import android.view.View;
import android.widget.EditText;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
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
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_feedback)
public class FeedBackActivity extends BaseActivity {

    @ViewById
    EditText edit_feedback;

    MyAlertDialog dialog;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyRestClient myRestClient;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @Click
    void btn_tijiao() {
        if (isNetworkAvailable(this)) {
            String content = edit_feedback.getText().toString();
            if (StringUtils.isEmpty(content)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "内容不能为空！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            AndroidTool.showCancelabledialog(this);
            feedbackInfo();
        }
    }

    @Background
    void feedbackInfo() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<String> bm = myRestClient.FeedbackInfo(edit_feedback.getText().toString());
        showsuccess(bm);
    }

    @UiThread
    void showsuccess(BaseModelJson<String> bm) {
        AndroidTool.dismissLoadDialog();
        if (bm != null) {
            if (bm.Successful) {
                dialog = new MyAlertDialog(this, bm.Error, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.close();
                        finish();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            } else {
                MyAlertDialog dialog = new MyAlertDialog(this, bm.Error, null);
                dialog.show();
            }
        }
    }
}
