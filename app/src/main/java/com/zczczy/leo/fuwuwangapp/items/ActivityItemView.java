package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.Activity;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/28.
 */

@EViewGroup(R.layout.activity_activity_item)
public class ActivityItemView extends ItemView<Activity> {

    @ViewById
    TextView txt_act_name, act_type, act_time, act_address, act_man, txt_act_state;

    @ViewById
    ImageView img_act;

    Context context;

    public ActivityItemView(Context context) {
        super(context);

        this.context = context;
    }

    @Override
    protected void init(Object... objects) {

        txt_act_name.setText(_data.getAtitle());

        act_type.setText(_data.getAtype().getAtname());

        act_time.setText(_data.getAstarttime());

        act_address.setText(_data.getAaddress());

        act_man.setText(_data.getPromoterusername());

        if (_data.getStatus().equals("1")) {
            txt_act_state.setText("进行中");
        }
        if (_data.getStatus().equals("0")) {
            txt_act_state.setText("已开始");
        }
        if (_data.getStatus().equals("2")) {
            txt_act_state.setText("已结束");
        }

        if (!"".equals(_data.getAimgurl()) && _data.getAimgurl() != null && !_data.getAimgurl().isEmpty()) {

            Picasso.with(context).load(_data.getAimgurl()).error(R.drawable.goods_default).placeholder(R.drawable.goods_default).into(img_act);
        }

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
