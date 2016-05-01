package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.GoodsCommentsModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/1.
 */
@EViewGroup(R.layout.activity_goods_comments_item)
public class GoodsCommentsItemView extends ItemView<GoodsCommentsModel> {

    @ViewById
    ImageView img_avatar;

    @ViewById
    TextView txt_name,txt_comments,txt_time;

    @ViewById
    RatingBar ratingBar;

    public GoodsCommentsItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {
        txt_name.setText(_data.UserLogin);
        txt_comments.setText(_data.GoodsCommentsNr);
        txt_time.setText(_data.PlTime);
        ratingBar.setRating(_data.XNum);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
