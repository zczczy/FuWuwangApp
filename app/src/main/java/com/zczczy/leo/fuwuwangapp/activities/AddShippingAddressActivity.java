package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.MReceiptAddressModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

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

/**
 * Created by Leo on 2016/5/4.
 */
@EActivity(R.layout.activity_add_shipping_address)
public class AddShippingAddressActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    EditText edt_shipping, edt_phone, txt_detail_address;

    @ViewById
    TextView txt_p_c_a;

    @ViewById
    LinearLayout ll_companyarea;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Pref
    MyPrefs_ pre;

    @Extra
    int receiptAddressId;

    int areaId = 0;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        if (receiptAddressId != 0) {
            myTitleBar.setTitle("编辑收货地址");
            AndroidTool.showLoadDialog(this);
            getMReceiptAddressById();
        }
    }

    @Background
    void getMReceiptAddressById() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        afterGetMReceiptAddressById(myRestClient.getMReceiptAddressById(receiptAddressId));
    }

    @UiThread
    void afterGetMReceiptAddressById(BaseModelJson<MReceiptAddressModel> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
        } else {
            edt_shipping.setText(bmj.Data.ReceiptName);
            edt_phone.setText(bmj.Data.Mobile);
            txt_p_c_a.setText(bmj.Data.ProvinceName + bmj.Data.CityName + bmj.Data.AreaName);
            txt_detail_address.setText(bmj.Data.DetailAddress);
            areaId = bmj.Data.AreaId;
        }
    }

    @Click
    void ll_companyarea() {
        ProvinceActivity_.intent(this).startForResult(1000);
    }

    @OnActivityResult(1000)
    void onSelectPCA(int resultCode, @OnActivityResult.Extra String pca, @OnActivityResult.Extra int areaId) {
        if (resultCode == RESULT_OK) {
            txt_p_c_a.setText(pca);
            this.areaId = areaId;
        }
    }


    @Click
    void btn_save() {
        if (AndroidTool.checkIsNull(edt_shipping)) {
            AndroidTool.showToast(this, "收货人不能为空");
        } else if (AndroidTool.checkIsNull(edt_phone)) {
            AndroidTool.showToast(this, "联系电话不能为空");
        } else if (areaId == 0) {
            AndroidTool.showToast(this, "请选择省、市、区");
        } else if (AndroidTool.checkIsNull(txt_detail_address)) {
            AndroidTool.showToast(this, "详细地址不能为空");
        } else {
            AndroidTool.showLoadDialog(this);
            addShippingAddress();
        }
    }

    @Background
    void addShippingAddress() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        MReceiptAddressModel model = new MReceiptAddressModel();
        model.DetailAddress = txt_detail_address.getText().toString().trim();
        model.AreaId = areaId;
        model.ReceiptName = edt_shipping.getText().toString().trim();
        model.Mobile = edt_phone.getText().toString().trim();
        afterAddShippingAddress(myRestClient.addMReceiptAddress(model));
    }

    @UiThread
    void afterAddShippingAddress(BaseModel bm) {
        if (bm == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bm.Successful) {
            AndroidTool.showToast(this, bm.Error);
        } else {
            finish();
        }
    }
}
