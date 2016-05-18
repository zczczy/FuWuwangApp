package com.zczczy.leo.fuwuwangapp.activities;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.MemberInfo;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.RegexUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by zczczy on 2016/5/5.
 */
@EActivity(R.layout.activity_member)
public class MemberInfoActivity extends BaseActivity {

    @ViewById
    TextView txt_change;

    @ViewById
    EditText txt_name, edt_blog, edt_qq, edt_email;

    @RestService
    MyDotNetRestClient myRestClient;

    @ViewById
    ImageView img_avatar;

    @Bean
    MyErrorHandler myErrorHandler;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        getMemberInfo();
    }


    @Click
    void img_avatar() {
        PhotoPickerIntent intent = new PhotoPickerIntent(MemberInfoActivity.this);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        intent.setShowGif(true);
        startActivityForResult(intent, 1000);
    }

    @Background
    @Trace()
    void uploadAvatar(String avatarUrl) {
        MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
        FileSystemResource image = new FileSystemResource(avatarUrl);
        data.set("image", image);
        myRestClient.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
        afterUploadAvatar(myRestClient.uploadAvatar(data));
    }

    @UiThread
    void afterUploadAvatar(String bmj) {
        if (bmj == null) {
            AndroidTool.dismissLoadDialog();
            AndroidTool.showToast(this, no_net);
        } else if (bmj.equals("0")) {
            AndroidTool.dismissLoadDialog();
            AndroidTool.showToast(this, "上传失败");
        } else {
            Log.e("img", bmj);
            updateMemberInfoImg(bmj);
        }
    }

    @Background
    void updateMemberInfoImg(String img) {
        Map<String, String> map = new HashMap<>(1);
        map.put("HeadImg", img);
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        afterUpdateMemberInfoImg(myRestClient.updateMemberInfoImg(map));
    }

    @UiThread
    void afterUpdateMemberInfoImg(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {
            AndroidTool.showToast(this, "上传失败");
        } else {
            pre.avatar().put(bmj.Data);
            Picasso.with(this).load(bmj.Data).placeholder(R.drawable.default_header).error(R.drawable.default_header).into(img_avatar);
        }
    }


    @OnActivityResult(1000)
    void onSelectPicture(int resultCode, @OnActivityResult.Extra(value = PhotoPickerActivity.KEY_SELECTED_PHOTOS) ArrayList<String> photos) {
        if (resultCode == RESULT_OK) {
            AndroidTool.showLoadDialog(this);
            uploadAvatar(photos.get(0));
        }
    }


    @Background
    void getMemberInfo() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        afterGetMemberInfo(myRestClient.getMemberInfo());
    }

    @UiThread
    void afterGetMemberInfo(BaseModelJson<MemberInfo> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
        } else {
            txt_name.setText(bmj.Data.MemberRealName);
            edt_email.setText(bmj.Data.MemberEmail);
            edt_qq.setText(bmj.Data.MemberQQ);
            edt_blog.setText(bmj.Data.MemberBlog);
            if (!StringUtils.isEmpty(bmj.Data.HeadImg)) {
                Picasso.with(this).load(bmj.Data.HeadImg).placeholder(R.drawable.default_header).error(R.drawable.default_header).into(img_avatar);
            }
            pre.avatar().put(bmj.Data.HeadImg);
        }
    }


    //保存
    @Click
    void btn_save() {
        changeInfo(edt_email.getText().toString().trim(), edt_qq.getText().toString().trim(), edt_blog.getText().toString().trim());
    }

    @Click
    void ll_shipping() {
        ShippingAddressActivity_.intent(this).start();
    }

    @Background
    void changeInfo(String MemberEmail, String MemberQQ, String MemberBlog) {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("kbn", MyApplication.ANDROID);
        HashMap<String, String> map = new HashMap<>();
        map.put("MemberBlog", MemberBlog);
        map.put("MemberQQ", MemberQQ);
        map.put("MemberEmail", MemberEmail);
        map.put("MemberRealName", txt_name.getText().toString().trim());
        BaseModel bmj = myRestClient.updateMemberInfo(map);
        afterChange(bmj);
    }

    @UiThread
    void afterChange(BaseModel bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            AndroidTool.showToast(this, "保存成功");
            finish();
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }
}
