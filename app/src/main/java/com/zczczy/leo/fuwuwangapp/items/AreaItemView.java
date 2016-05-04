package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.NewCity;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/4.
 */
@EViewGroup(R.layout.activity_province_item)
public class AreaItemView extends ItemView<NewArea> {

    @ViewById
    TextView txt_province;

    public AreaItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {
        txt_province.setText(_data.AreaName);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
