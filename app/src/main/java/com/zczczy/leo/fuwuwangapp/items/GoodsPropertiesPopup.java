package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zczczy.leo.fuwuwangapp.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/7.
 */
@EViewGroup(R.layout.goods_properties_popup)
public class GoodsPropertiesPopup extends LinearLayout {

    @ViewById
    LinearLayout root;

//    @ViewById
//    MyViewGroup myViewGroup;

    Context context;

    PopupWindow popupWindow;

    public GoodsPropertiesPopup(Context context) {
        super(context);
        this.context = context;
    }

    public void setData(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }

    @Touch
    void root(View v, MotionEvent event) {
        int height = v.getHeight();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y < height && popupWindow != null) {
                popupWindow.dismiss();
            }
        }
    }

    @Click
    void img_close() {
        popupWindow.dismiss();
    }

}
