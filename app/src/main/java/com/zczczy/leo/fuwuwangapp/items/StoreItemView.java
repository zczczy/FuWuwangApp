package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/10.
 */
@EViewGroup(R.layout.activity_store_item)
public class StoreItemView extends ItemView<StoreDetailModel> {

    @ViewById
    ImageView img_store_pic;

    @ViewById
    TextView txt_store_name, txt_store_region, txt_store_describe;

    @ViewById
    RatingBar ratingBar;


    Context context;


    public StoreItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (!StringUtils.isEmpty(_data.StoreImgSl)) {

            Glide.with(context)
                    .load(_data.StoreImgSl)
                    .centerCrop()
                    .crossFade()
                    .placeholder(R.drawable.goods_default)
                    .error(R.drawable.goods_default)
                    .into(img_store_pic);
        }

        txt_store_name.setText(_data.StoreName);

        txt_store_region.setText(_data.StreetName);

        txt_store_describe.setText(_data.StoreDesc);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
