package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.MReceiptAddressModel;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/4.
 */
@EViewGroup(R.layout.activity_shipping_address_item)
public class ShippingAddressItemView extends ItemView<MReceiptAddressModel> {

    @ViewById
    TextView receipt_name, receipt_phone, receipt_wholeaddress;

    @ViewById
    CheckBox txt_default;

    public ShippingAddressItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {
        receipt_name.setText(_data.ReceiptName);
        receipt_phone.setText(_data.Mobile);
        receipt_wholeaddress.setText(_data.ProvinceName + "-" + _data.CityName + "-" + _data.AreaName + _data.DetailAddress);
        txt_default.setChecked("1".equals(_data.IsPrimary));
    }

    @Click
    void txt_edit() {

    }

    @Click
    void txt_delete() {

    }

    @Click
    void txt_default() {
        if(!txt_default.isChecked()){

        }
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
