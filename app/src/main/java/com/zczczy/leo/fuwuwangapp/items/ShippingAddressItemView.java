package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.AddShippingAddressActivity_;
import com.zczczy.leo.fuwuwangapp.adapters.ShippingAddressAdapter;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.MReceiptAddressModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/5/4.
 */
@EViewGroup(R.layout.activity_shipping_address_item)
public class ShippingAddressItemView extends ItemView<MReceiptAddressModel> {

    @ViewById
    TextView receipt_name, receipt_phone, receipt_wholeaddress;

    @ViewById
    CheckBox txt_default;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Pref
    MyPrefs_ pre;

    @StringRes
    String no_net;

    ShippingAddressAdapter shippingAddressAdapter;

    BaseViewHolder viewHolder;

    Context context;


    public ShippingAddressItemView(Context context) {
        super(context);
        this.context = context;
    }

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @Override
    protected void init(Object... objects) {
        shippingAddressAdapter = (ShippingAddressAdapter) objects[0];
        viewHolder = (BaseViewHolder) objects[1];

        receipt_name.setText(_data.ReceiptName);
        receipt_phone.setText(_data.Mobile);
        receipt_wholeaddress.setText(_data.ProvinceName + "-" + _data.CityName + "-" + _data.AreaName + _data.DetailAddress);
        txt_default.setChecked("1".equals(_data.IsPrimary));
    }

    @Click
    void txt_edit() {
        AddShippingAddressActivity_.intent(context).receiptAddressId(_data.MReceiptAddressId).start();
    }

    @Click
    void txt_delete() {
        delete();
    }

    @Background
    void delete() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        afterDelete(myRestClient.delReceiptAddress(_data.MReceiptAddressId));
    }

    @UiThread
    void afterDelete(BaseModel bm) {
        if (bm == null) {
            AndroidTool.showToast(context, no_net);
        } else if (!bm.Successful) {
            AndroidTool.showToast(context, bm.Error);
        } else {
            shippingAddressAdapter.deleteItem(_data,viewHolder.getAdapterPosition());
        }
    }

    @Click
    void txt_default() {
        if (!txt_default.isChecked()) {

        }
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
