package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.LotteryInfo;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/3/9.
 */
@EViewGroup(R.layout.activity_lottery_info_item)
public class LotteryInfoItemView extends ItemView<LotteryInfo> {

    @ViewById
    TextView lottery_name, winners, lottery_create_time;


    public LotteryInfoItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {

        winners.setText(_data.UserName);

        lottery_name.setText(_data.ProductName);

        lottery_create_time.setText(_data.CreateTime);

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
