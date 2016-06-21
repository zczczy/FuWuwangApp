package com.zczczy.leo.fuwuwangapp.activities;

import android.Manifest;
import android.os.Build;
import android.util.Log;
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
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_qrcode)
public class QrCodeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private String code;

    @ViewById
    Button btn_paper_transfer, btn_coupon_scan;

    @ViewById
    EditText edt_coupon_code;

    @StringRes
    String txt_ewmzq;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyRestClient myRestClient;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    //排队按钮点击事件
    @Click
    void btn_paper_transfer() {
        if (isNetworkAvailable(this)) {
            code = edt_coupon_code.getText().toString();
            if (StringUtils.isEmpty(code)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "请输入二维码券号", null);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return;
            }
            AndroidTool.showCancelabledialog(this);
            getHttp(code);
        } else {
            Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    //扫一扫按钮点击事件
    @Click
    void btn_coupon_scan() {
        if (isNetworkAvailable(this)) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestCodeQrcodePermissions();
            }else{
                ScanActivity_.intent(this).startForResult(1000);
            }
        } else {
            Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
        }
    }

    @OnActivityResult(1000)
    void getBillId(int resultCode, @OnActivityResult.Extra String result) {
        if (resultCode == RESULT_OK) {
            if (isNetworkAvailable(this)) {
                getHttp(result);
            } else {
                Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Background
    void getHttp(String scanResult) {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<PaperFace> bmj = myRestClient.ScanLogin(scanResult);
        show_activity(bmj);
    }

    @UiThread
    void show_activity(BaseModelJson<PaperFace> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                //跳转到纸劵排队页
                PaperCouponQueueActivity_.intent(this).qi_id(bmj.Data.getQi_id()).queuesId(bmj.Data.getQueuesId()).queuesInRule(bmj.Data.getQueuesInRule()).start();
            } else {
                MyAlertDialog dialog = new MyAlertDialog(QrCodeActivity.this, bmj.Error, null);
                dialog.show();
                dialog.setCancelable(false);
            }
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
            ScanActivity_.intent(this).startForResult(1000);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.size() == 2) {
            ScanActivity_.intent(this).startForResult(1000);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        AndroidTool.showToast(this, "你拒绝授权!请到设置里去重新授权");
    }
}
