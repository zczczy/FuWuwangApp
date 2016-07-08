package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.Notice;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/4/28.
 */
@EViewGroup(R.layout.activity_notice_item)
public class NoticeItemView extends ItemView<Notice> {


    @ViewById
    ImageView img_nimg;

    @ViewById
    TextView txt_title, txt_date;

    Context context;

    public NoticeItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (!StringUtils.isEmpty(_data.NimgUrl)) {
            Glide.with(context).load(_data.NimgUrl).centerCrop().crossFade()
                    .placeholder(R.drawable.goods_default).error(R.drawable.goods_default).into(img_nimg);
        }
        txt_title.setText(_data.getTitle());

        txt_date.setText(_data.getDate());

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
