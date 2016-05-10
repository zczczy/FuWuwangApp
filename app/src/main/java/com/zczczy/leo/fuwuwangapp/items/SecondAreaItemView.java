package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.StreetInfo;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * Created by Leo on 2015/12/26.
 */
@EViewGroup(R.layout.second_area_item)
public class SecondAreaItemView extends ItemView<StreetInfo> {

    @ViewById
    TextView txt_street;

    @ViewById
    ImageView img_street;

    @App
    MyApplication app;

    @ColorRes
    int buy_button,black;

    public SecondAreaItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {

        txt_street.setText(_data.StreetName);

        if(app.getNewStreet()!=null && app.getNewStreet().StreetInfoId==_data.StreetInfoId &&app.getNewStreet().AreaId ==_data.AreaId){

            img_street.setVisibility(View.VISIBLE);

            txt_street.setTextColor(buy_button);

        }else{

            txt_street.setTextColor(black);

            img_street.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
