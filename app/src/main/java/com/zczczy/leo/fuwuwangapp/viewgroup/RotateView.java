package com.zczczy.leo.fuwuwangapp.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Leo on 2016/3/4.
 */
public class RotateView extends ImageView {


    private String[] strArr = {"0", "1", "2", "3", "4", "5", "6", "7"};

    private String[] strArrDe = {"0", "45", "90", "135", "180", "225", "270", "315"};

    public RotateView(Context context) {
        super(context);
    }

    public RotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //设定旋转中心
        setPivotX(getMeasuredWidth() / 2);
        setPivotY(getMeasuredHeight() / 2);
    }


}
