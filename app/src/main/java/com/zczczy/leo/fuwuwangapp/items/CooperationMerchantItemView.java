package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.CooperationMerchant;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/4/28.
 */
@EViewGroup(R.layout.activity_cooperation_merchant_item)
public class CooperationMerchantItemView extends ItemView<CooperationMerchant> {

    @ViewById
    TextView txt_company_name, txt_major, txt_company_address;


    @ViewById
    ImageView img_company_logo;

    @DrawableRes
    Drawable joined, not_joined;

    Context context;

    public CooperationMerchantItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (StringUtils.isEmpty(_data.CompanyKbn)) {
        } else if (_data.SellerInfoId == 0) {
            not_joined.setBounds(0, 0, not_joined.getMinimumWidth(), not_joined.getMinimumHeight());
            txt_company_name.setCompoundDrawables(null, null, not_joined, null);
        } else {
            joined.setBounds(0, 0, joined.getMinimumWidth(), joined.getMinimumHeight());
            txt_company_name.setCompoundDrawables(null, null, joined, null);
        }

        txt_company_name.setText(_data.cp_name_zh);
        txt_major.setText("           " + _data.cp_type);
        txt_company_address.setText("           " + _data.cp_address);

        if (!"".equals(_data.cp_pic) && _data.cp_pic != null && !_data.cp_pic.isEmpty()) {
            Glide.with(context).load(_data.cp_pic)
                    .centerCrop()
                    .crossFade()
                    .error(R.drawable.goods_default)
                    .placeholder(R.drawable.goods_default)
                    .into(img_company_logo);
        }

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}