package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

/**
 * @author Created by LuLeo on 2016/7/13.
 *         you can contact me at :361769045@qq.com
 * @since 2016/7/13.
 */
@EViewGroup(R.layout.activity_merchant_detail_store)
public class MerchantStoreItemView extends ItemView<StoreDetailModel> {

    @ViewById
    ImageView img_company_logo;

    @ViewById
    TextView txt_store_name, txt_company_address, txt_company_phone;

    Context context;

    public MerchantStoreItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (!StringUtils.isEmpty(_data.StoreImgSl)) {
            Glide.with(context).load(_data.StoreImgSl).crossFade().centerCrop().skipMemoryCache(true)
                    .error(R.drawable.goods_default).placeholder(R.drawable.goods_default)
                    .into(img_company_logo);
        }
        txt_store_name.setText(_data.StoreName);
        txt_company_address.setText(_data.StoreAddress);
        txt_company_phone.setText(_data.LinkTel);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
