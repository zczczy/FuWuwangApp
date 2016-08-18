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
import com.zczczy.leo.fuwuwangapp.views.GlideSliderView;

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
    TextView txt_store_describe, txt_detail_address, txt_phone;

    Context context;

    public StoreInformationHeaderItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        txt_detail_address.setText(_data.StoreAddress);
        txt_store_describe.setText(_data.StoreDesc);
        txt_phone.setText(_data.LinkTel);
        for (StoreImg nb : _data.StoreImgList) {
            GlideSliderView textSliderView = new GlideSliderView(context);
            textSliderView.image(nb.StoreImgUrl)
                    .error(R.drawable.goods_detail_banner)
                    .empty(R.drawable.goods_detail_banner)
            ;
            sliderLayout.addSlider(textSliderView);
        }

        if (_data == null || _data.StoreImgList.size() == 0) {
            sliderLayout.stopAutoCycle();
            sliderLayout.setBackgroundResource(R.drawable.goods_detail_banner);
        } else if (_data.StoreImgList.size() == 1) {
            sliderLayout.stopAutoCycle();
        } else {
            sliderLayout.startAutoCycle();
        }
    }

    public void stopAutoCycle() {
        sliderLayout.stopAutoCycle();
    }

    public void removeAllSliders() {
        sliderLayout.removeAllSliders();
    }

    public void startAutoCycle() {
        if (_data != null && _data.StoreImgList.size() > 1) {
            sliderLayout.startAutoCycle();
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
