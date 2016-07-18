package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.StoreInformationActivity_;
import com.zczczy.leo.fuwuwangapp.model.CooperationMerchant;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author Created by LuLeo on 2016/7/13.
 *         you can contact me at :361769045@qq.com
 * @since 2016/7/13.
 */
@EViewGroup(R.layout.activity_merchant_detail_header_view)
public class MerchantDetailHeaderView extends ItemView<CooperationMerchant> {

    @ViewById
    TextView txt_merchant, txt_describe;

    @ViewById
    LinearLayout ll_store;

    Context context;

    public MerchantDetailHeaderView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        txt_merchant.setText(_data.cp_name_zh);
        txt_describe.setText(_data.cp_info);
        ll_store.removeAllViews();
        for (final StoreDetailModel storeDetailModel : _data.StoreList) {
            MerchantStoreItemView s = MerchantStoreItemView_.build(context);
            s.init(storeDetailModel);
            ll_store.addView(s);
            s.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    StoreInformationActivity_.intent(context).storeId(storeDetailModel.StoreInfoId).start();
                }
            });
        }
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
