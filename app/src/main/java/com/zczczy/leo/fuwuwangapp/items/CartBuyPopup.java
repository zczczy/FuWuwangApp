package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CartBuyPopAdapter;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CheckOutModel;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

import java.util.List;

/**
 * Created by Leo on 2016/5/7.
 */
@EViewGroup(R.layout.cart_buy_popup)
public class CartBuyPopup extends LinearLayout {


    @ViewById
    LinearLayout root;

    @ViewById
    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;

    @Bean(CartBuyPopAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    @ColorRes
    int line_color;

    Context context;

    PopupWindow popupWindow;

    public CartBuyPopup(Context context) {
        super(context);
        this.context = context;
    }


    @AfterViews
    void afterView() {
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).margin(0).paint(paint).build());

    }

    public void setData(BaseModelJson<List<CheckOutModel>> bmj, PopupWindow popupWindow) {
        this.popupWindow = popupWindow;

        myAdapter.getMoreData(bmj);
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
