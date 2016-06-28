package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.ExchangeLongBiModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author Created by LuLeo on 2016/6/28.
 *         you can contact me at :361769045@qq.com
 * @since 2016/6/28.
 */
@EViewGroup(R.layout.activity_exchange_long_bi_item)
public class ExchangeLongBiItemView extends ItemView<ExchangeLongBiModel> {


    @ViewById
    TextView txt_code, txt_time, txt_exchange;


    public ExchangeLongBiItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {
        txt_code.setText(_data.qi_loginKey);
        txt_time.setText(_data.qi_beginTime);
        txt_exchange.setText("兑换");
        txt_exchange.setBackgroundResource(R.drawable.order_gray);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
