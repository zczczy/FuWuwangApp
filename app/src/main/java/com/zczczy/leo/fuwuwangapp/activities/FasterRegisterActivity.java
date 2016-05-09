package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CardInfo;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.ImageUtil;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyAlertDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_faster_register)
public class FasterRegisterActivity extends BaseActivity {

    @ViewById
    TextView id_num, txt_name;

    @RestService
    MyRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Extra
    String fileName;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void agterView() {
        AndroidTool.showLoadDialog(this);
        getHttp();
    }

    @Click
    void btn_regist() {
        AndroidTool.showLoadDialog(this);
        regist();
    }

    @Click
    void btn_re_regist() {
        ImageUtil.cameraIntent(this, fileName);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
    }

    @Background
    void regist() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModel bmj = myRestClient.FastRegister(id_num.getText().toString(), txt_name.getText().toString());
        showSuccess(bmj);
    }

    @UiThread
    void showSuccess(BaseModel bmj) {
        if (bmj != null) {
            if (bmj.Successful) {
                MyAlertDialog dialog = new MyAlertDialog(this, "注册成功", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            } else {
                MyAlertDialog dialog = new MyAlertDialog(this, bmj.Error, null);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
        AndroidTool.dismissLoadDialog();
    }


    @Background
    void getHttp() {
//        String token = pre.token().get();
//        myRestClient.setHeader("Token",token);
        File file = new File(fileName);
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("file", new FileSystemResource(file.getAbsoluteFile()));
        myRestClient.setHeader("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
        myRestClient.setHeader("Content-Disposition", "form-data");
        myRestClient.setHeader("connection", "keep-alive");
        myRestClient.setHeader("charset", "UTF-8");
        BaseModelJson<CardInfo> bmj = myRestClient.GetIdCardInfo(formData);
        show(bmj);
    }

    @UiThread
    void show(BaseModelJson<CardInfo> bmj) {
        if (bmj != null) {
            if (bmj.Successful) {
                id_num.setText(bmj.Data.getId_number());
                txt_name.setText(bmj.Data.getName());
            } else {
                MyAlertDialog dialog = new MyAlertDialog(this, bmj.Error, null);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
        AndroidTool.dismissLoadDialog();
    }

    @OnActivityResult(ImageUtil.CAMERA_ACTIVITY)
    void onCameraResult(int resultCode, Intent data) {
        ImageUtil.corpIntent(this, AndroidTool.getUri(fileName), AndroidTool.getUri(fileName));
    }

    @OnActivityResult(ImageUtil.IMAGE_CROP)
    void onCodeResult(int resultCode, Intent data) {
        ImageUtil.resetPhotp(fileName);
        getHttp();
    }
}
