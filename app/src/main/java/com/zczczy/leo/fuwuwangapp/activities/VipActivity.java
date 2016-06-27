package com.zczczy.leo.fuwuwangapp.activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.UserBaseInfo;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.ImageUtil;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

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
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_vip)
public class VipActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @ViewById
    TextView txt_username, txt_realname;

    @ViewById
    MyTitleBar myTitleBar;

    @StringRes
    String my_lottery_record;

    @RestService
    MyDotNetRestClient newMyRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    //身份证扫描注册相关
    String fileName;

    public int CAMERA_ACTIVITY = 1;

    public int IMAGE_CROP = 3;

    @AfterInject
    void afterInject() {
        newMyRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        fileName = AndroidTool.BaseFilePath() + System.currentTimeMillis() + ".jpg";
        AndroidTool.showLoadDialog(this);
        getBind();
    }


    @Background
    void getBind() {
        String token = pre.token().get();
        newMyRestClient.setHeader("Token", token);
        BaseModelJson<UserBaseInfo> bmj = newMyRestClient.GetZcUserById();
        setBind(bmj);
    }

    @UiThread
    void setBind(BaseModelJson<UserBaseInfo> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                pre.username().put(bmj.Data.getM_Uid());
                pre.realname().put(bmj.Data.getM_realrName());
                txt_username.setText(bmj.Data.getM_Uid());
                txt_realname.setText(bmj.Data.getM_realrName());
            } else {
                AndroidTool.showToast(this, bmj.Error);
            }
        }
    }

    //财富
    @Click
    void ll_wealth() {
        if (isNetworkAvailable(this)) {
            WealthActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //资料完善
    @Click
    void ll_perfect_info() {
        if (isNetworkAvailable(this)) {
            PerfectInfoActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //兑现卷排队
    @Click
    void ll_queue() {
        if (isNetworkAvailable(this)) {
            QueueActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //电子币抽奖记录
    @Click
    void ll_quan_record() {
        LotteryInfoRecordActivity_.intent(this).title(my_lottery_record).method(0).start();
    }

    //兑现卷管理
    @Click
    void ll_manager() {
        if (isNetworkAvailable(this)) {
            CouponManageActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //推荐
    @Click
    void ll_suggest() {
        if (isNetworkAvailable(this)) {
            SuggestActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }

    }

    //身份证扫描注册
    @Click
    void ll_scan() {
        if (isNetworkAvailable(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestCodeQrcodePermissions();
            } else {
                scan();
            }
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    @Click
    void ll_address() {
        MyAddressActivity_.intent(this).start();
    }

    void scan() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f1 = new File(fileName);
        Uri u1 = Uri.fromFile(f1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u1);
        startActivityForResult(intent, CAMERA_ACTIVITY);
    }


    @OnActivityResult(ImageUtil.CAMERA_ACTIVITY)
    void onCameraResult(int resultCode, Intent data) {
        if (resultCode != 0) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(AndroidTool.getUri(fileName), "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, AndroidTool.getUri(fileName));
            intent.putExtra("crop", "true");
            intent.putExtra("noFaceDetection", true);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            startActivityForResult(intent, IMAGE_CROP);
        }
    }

    @OnActivityResult(ImageUtil.IMAGE_CROP)
    void onCodeResult(int resultCode, Intent data) {
        ImageUtil.resetPhotp(fileName);
        FasterRegisterActivity_.intent(this).fileName(fileName).start();
    }

    //游戏中心
    @Click
    void ll_game_club() {
        if (isNetworkAvailable(this)) {
            WebViewActivity_.intent(this).header("游戏中心").url("http://appapia.86fuwuwang.com/" + "DetailPage/GameDisp?kbn=2").start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    @Click
    void ll_safe() {
        if (isNetworkAvailable(this)) {
            SafeMessengerActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //我的联盟会员
    @Click
    void ll_mymember() {
        if (isNetworkAvailable(this)) {
            UnionMemberActivity_.intent(this).userId(0).username("我").start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    // 意见反馈
    @Click
    void ll_feedback() {
        if (isNetworkAvailable(this)) {
            FeedBackActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(1)
    private void requestCodeQrcodePermissions() {
        //, Manifest.permission.FLASHLIGHT
        String[] perms = {Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", 1, perms);
        } else {
            scan();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.size() == 2) {
            scan();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        AndroidTool.showToast(this, "你拒绝授权!请到设置里去重新授权");
    }
}
