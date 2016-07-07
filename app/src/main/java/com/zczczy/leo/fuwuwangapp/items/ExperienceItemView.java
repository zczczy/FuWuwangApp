package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.Experience;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/28.
 */
@EViewGroup(R.layout.activity_experience_item)
public class ExperienceItemView extends ItemView<Experience> {

    @ViewById
    TextView txt_company_name,txt_company_address;

    @ViewById
    ImageView img_company_logo;

    Context context;

    public ExperienceItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {

        txt_company_name.setText(_data.agentname);

        txt_company_name.setText(_data.agentname);

        txt_company_address.setText(_data.dizhi);

        if(!"".equals(_data.imgurl)&&!_data.imgurl.isEmpty()&&_data.imgurl!=null){

            Glide.with(context).load(_data.imgurl)
                    .centerCrop()
                    .crossFade()
                    .error(R.drawable.goods_default)
                    .placeholder(R.drawable.goods_default)
                    .into(img_company_logo);
        }

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}