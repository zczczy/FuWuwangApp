package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.GoodsCommentsModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/1.
 */
@EViewGroup(R.layout.activity_goods_comments_item)
public class GoodsCommentsItemView extends ItemView<GoodsCommentsModel> {

    @ViewById
    ImageView img_avatar;

    @ViewById
    TextView txt_name, txt_comments, txt_time;

    @ViewById
    RatingBar ratingBar;

    Context context;

    public GoodsCommentsItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        txt_name.setText(_data.UserLogin);
        txt_comments.setText(_data.GoodsCommentsNr);
        txt_time.setText(_data.PlTime);
        ratingBar.setRating(_data.XNum);
        if (!StringUtils.isEmpty(_data.HeadImg)) {
            Picasso.with(context).load(_data.HeadImg).placeholder(R.drawable.default_header).error(R.drawable.default_header).into(img_avatar);
        }
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
