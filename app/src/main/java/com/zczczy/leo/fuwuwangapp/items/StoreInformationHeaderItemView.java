package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;
import com.zczczy.leo.fuwuwangapp.model.StoreImg;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/5.
 */
@EViewGroup(R.layout.activity_store_information_header_item)
public class StoreInformationHeaderItemView extends ItemView<StoreDetailModel> {


    @ViewById
    SliderLayout sliderLayout;

    @ViewById
    TextView txt_store, txt_store_describe, txt_detail_address;

    @ViewById
    RatingBar ratingBar;

    Context context;

    public StoreInformationHeaderItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        ratingBar.setRating(_data.StorePX);
        txt_store.setText(_data.StoreName);
        txt_detail_address.setText(_data.StoreAddress);
        txt_store_describe.setText(_data.StoreDesc);
        for (StoreImg nb : _data.StoreImgList) {
            DefaultSliderView textSliderView = new DefaultSliderView(context);
            textSliderView.image(nb.StoreImgUrl)
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .error(R.drawable.goods_detail_banner)
                    .empty(R.drawable.goods_detail_banner)
            ;
            sliderLayout.addSlider(textSliderView);
        }
    }

    @Click
    void img_phone() {
        if (_data != null && (!AndroidTool.checkMPhone(_data.LinkTel) || !AndroidTool.checkTPhone(_data.LinkTel))) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + _data.LinkTel.trim());
            intent.setData(data);
            context.startActivity(intent);
        }
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
