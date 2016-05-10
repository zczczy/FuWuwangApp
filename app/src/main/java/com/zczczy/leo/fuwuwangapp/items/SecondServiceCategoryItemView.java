package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * Created by Leo on 2016/5/10.
 */
@EViewGroup(R.layout.service_second_area_item)
public class SecondServiceCategoryItemView extends ItemView<GoodsTypeModel> {

    @ViewById
    TextView txt_street;

    @ViewById
    ImageView img_street;

    @App
    MyApplication app;

    @ColorRes
    int buy_button,black;

    public SecondServiceCategoryItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {

        txt_street.setText(_data.GoodsTypeName);

        if(app.getSecondCategory()!=null && app.getSecondCategory().GoodsTypeId==_data.GoodsTypeId &&app.getSecondCategory().GoodsTypePid ==_data.GoodsTypePid){

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
