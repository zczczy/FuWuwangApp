package com.zczczy.leo.fuwuwangapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.MReceiptAddressModel;
import com.zczczy.leo.fuwuwangapp.model.MemberInfo;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.ImageUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
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

    String image;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        getUserDefaultAddress();
        getMemberInfo();
        if (MyApplication.VIP.equals(pre.userType().toString())) {
            txt_name.setEnabled(false);
            edt_email.setEnabled(false);
        }
    }

    @Background
    void getUserDefaultAddress() {
        afterGetUserDefaultAddress(myRestClient.getUserDefaultAddress());
    }

    @UiThread
    void afterGetUserDefaultAddress(BaseModelJson<MReceiptAddressModel> result) {
        if (result != null && result.Successful) {
            txt_change.setText(result.Data.ProvinceName + result.Data.CityName + result.Data.AreaName + result.Data.DetailAddress);
        } else {
            txt_change.setText("");
        }
    }

    @Click
    void img_avatar() {
        getPermissions();
    }


    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 127);
            } else {
                takePhoto();
            }
        } else {
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                AndroidTool.showToast(this, "您拒绝授权，该功能不可用");
                return;
            }
        }
        takePhoto();
    }

    void takePhoto() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        intent.setShowGif(false);
        startActivityForResult(intent, 1000);
    }

    /**
     * 上传图片
     *
     * @param avatarUrl
     */
    @Background
    void uploadAvatar(String avatarUrl) {
        MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
        FileSystemResource image = new FileSystemResource(avatarUrl);
        data.set("image", image);
        myRestClient.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
        afterUploadAvatar(myRestClient.uploadAvatar(data));
    }

    /**
     * 上传成功后 上传地址
     *
     * @param bmj
     */
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

    /**
     * 更新头像图片地址
     *
     * @param img
     */
    @Background
    void updateMemberInfoImg(String img) {
        Map<String, String> map = new HashMap<>(1);
        map.put("HeadImg", img);

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

    /**
     * 选择图片
     *
     * @param resultCode 请求code
     * @param photos     返回
     */
    @OnActivityResult(1000)
    void onSelectPicture(int resultCode, @OnActivityResult.Extra(value = PhotoPickerActivity.KEY_SELECTED_PHOTOS) ArrayList<String> photos) {
        if (resultCode == RESULT_OK) {
            image = photos.get(0);
            ImageUtil.corpIntent(this, AndroidTool.getUri(photos.get(0)), AndroidTool.getUri(photos.get(0)));
        }
    }

    @OnActivityResult(ImageUtil.IMAGE_CROP)
    void onCodeResult() {
        AndroidTool.showLoadDialog(this);
        ImageUtil.resetPhotp(image);
        uploadAvatar(image);
    }

    @Background
    void getMemberInfo() {
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
        ShippingAddressActivity_.intent(this).startForResult(1000);
    }

    @OnActivityResult(1000)
    void afterSetDefault() {
        getUserDefaultAddress();
    }

    @Background
    void changeInfo(String MemberEmail, String MemberQQ, String MemberBlog) {
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
