package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.YpdRecord;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/28.
 */
@EViewGroup(R.layout.activity_queue_see_item)
public class QueueSeeItemView extends ItemView<YpdRecord> {

    @ViewById
    TextView txt_mianzhi,txt_num,txt_date;

    public QueueSeeItemView(Context context) {
        super(context);
    }


    @Override
    protected void init(Object... objects) {
        txt_mianzhi.setText(_data.getMianzhi_id());
        txt_num.setText(_data.getCnt());
        txt_date.setText(_data.getDateVal());
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}