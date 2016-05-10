package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
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
@EViewGroup(R.layout.service_first_area_item)
public class FirstServiceCategoryItemView extends ItemView<GoodsTypeModel> {

    @ViewById
    public TextView txt_region;

    @ViewById
    public RelativeLayout rl;

    @App
    MyApplication app;

    @ColorRes
    int buy_button,black;

    public FirstServiceCategoryItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {

        if(app.getFirstCategory() !=null && app.getFirstCategory().GoodsTypeId==_data.GoodsTypeId){

            rl.setSelected(true);

            txt_region.setTextColor(buy_button);

        }else{

            rl.setSelected(false);

            txt_region.setTextColor(black);
        }

        txt_region.setText(_data.GoodsTypeName);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
