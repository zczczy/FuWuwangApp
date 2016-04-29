package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.QueueCompanyDetail;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/29.
 */
@EViewGroup(R.layout.activity_coupon_manager_info_item)
public class CouponManageInfoItemView extends   ItemView<QueueCompanyDetail> {

    @ViewById
    TextView txt_company_name,company_num;

    public CouponManageInfoItemView(Context context) {
        super(context);
    }


    @Override
    protected void init(Object... objects) {

        txt_company_name.setText(_data.getCompanyName());

        company_num.setText(_data.getNum());
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
