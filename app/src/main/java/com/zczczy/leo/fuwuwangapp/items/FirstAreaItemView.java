package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.NewArea;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * Created by Leo on 2016/5/10.
 */
@EViewGroup(R.layout.first_area_item)
public class FirstAreaItemView extends ItemView<NewArea> {

    @ViewById
    protected TextView txt_region;

    @ViewById
    protected RelativeLayout rl;

    @App
    MyApplication app;

    @ColorRes
    int buy_button, black;

    public FirstAreaItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {

        if (app.getNewRegion() != null && app.getNewRegion().AreaId == _data.AreaId) {

            rl.setSelected(true);

            txt_region.setTextColor(buy_button);

        } else {

            rl.setSelected(false);

            txt_region.setTextColor(black);
        }

        txt_region.setText(_data.AreaName);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
