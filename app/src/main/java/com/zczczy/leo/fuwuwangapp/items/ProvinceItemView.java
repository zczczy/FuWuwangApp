package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.NewProvince;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/4.
 */
@EViewGroup(R.layout.activity_province_item)
public class ProvinceItemView extends ItemView<NewProvince> {

    @ViewById
    TextView txt_province;

    public ProvinceItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {
        txt_province.setText(_data.ProvinceName);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
