package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.Purse;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/29.
 */
@EViewGroup(R.layout.activity_union_member_item)
public class UnionMemberItemView extends ItemView<Purse> {

    @ViewById
    TextView txt_unionmember_name, txt_unionmember_status, txt_truename, txt_membermadou, txt_memberstatics, txt_member_totalmd, txt_member_regist_name;

    public UnionMemberItemView(Context context) {
        super(context);
    }


    @Override
    protected void init(Object... objects) {

        txt_unionmember_name.setText(_data.getM_Uid());

        txt_unionmember_status.setText(_data.getGradename());

        txt_truename.setText(_data.getM_realrname());

        txt_memberstatics.setText(_data.getChildCount());

        txt_membermadou.setText(_data.getPoint());

        txt_member_totalmd.setText(_data.getAllPoint());

        txt_member_regist_name.setText(_data.getM_SignDate());

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}

