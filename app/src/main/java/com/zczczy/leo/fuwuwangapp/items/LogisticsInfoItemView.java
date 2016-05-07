package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.LogisticsInfo;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zczczy on 2016/5/5.
 */
@EViewGroup(R.layout.activity_logistics_info_item)
public class LogisticsInfoItemView extends ItemView<LogisticsInfo> {

    @ViewById
    TextView txt_content, txt_time;

    @ViewById
    ImageView img_time_circle;

    public LogisticsInfoItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {
        txt_content.setText(_data.context);
        txt_time.setText(_data.ftime);
        txt_time.setSelected(_data.isLast);
        txt_content.setSelected(_data.isLast);
        img_time_circle.setSelected(_data.isLast);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
