package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by leo on 2016/5/4.
 */
@EViewGroup(R.layout.fragment_first_category_item)
public class FirstCategoryItemView extends ItemView<GoodsTypeModel> {

    @ViewById
    TextView txt_first_category;

    public FirstCategoryItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {
        txt_first_category.setText(_data.GoodsTypeName);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
