package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.Purse;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/28.
 */
@EViewGroup(R.layout.activity_transaction_record_item)
public class TransactionRecordItemView extends ItemView<Purse> {

    @ViewById
    TextView txt_transaction_name, txt_transaction_money, txt_transaction_date, txt_transaction_common;


    public TransactionRecordItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {

        txt_transaction_name.setText(_data.getTradeName());

        txt_transaction_money.setText(_data.getMoney());

        txt_transaction_date.setText(_data.getTradeTime());

        txt_transaction_common.setText(_data.getExplain());

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
