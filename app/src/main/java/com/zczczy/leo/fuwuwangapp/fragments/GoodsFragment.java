package com.zczczy.leo.fuwuwangapp.fragments;

import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.VerticalDividerItemDecoration;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailActivity_;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.DetailGoodsRecommendAdapter;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.DisplayUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * @author Created by LuLeo on 2016/6/16.
 *         you can contact me at :361769045@qq.com
 * @since 2016/6/16.
 */
@EFragment(R.layout.fragment_goods_comments)
public class GoodsFragment extends BaseFragment {

    @ViewById
    RecyclerView recyclerView;

    @Bean
    OttoBus bus;

    @ViewById
    LinearLayout parent;

    @FragmentArg
    Goods goods;

    @ViewById
    RecyclerView recycler_view;

    @Bean(DetailGoodsRecommendAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    GridLayoutManager gridLayoutManager;

    RecyclerView.ItemDecoration itemDecoration;

    Paint paint = new Paint();

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler_view.setLayoutManager(gridLayoutManager);
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        itemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity()).margin(21)
                .visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
                    @Override
                    public boolean shouldHideDivider(int position, RecyclerView parent) {
                        return position == 0;
                    }
                }).paint(paint).build();

        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity())
                .margin(21).visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
                    @Override
                    public boolean shouldHideDivider(int position, RecyclerView parent) {
                        return false;
                    }
                }).paint(paint).build());

        recycler_view.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<Goods>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Goods obj, int position) {
                GoodsDetailActivity_.intent(GoodsFragment.this).goodsId(obj.GoodsInfoId).start();
            }
        });


        afterLoadMore();
    }

    @Subscribe
    public void notifyUI(BaseModelJson<List<Goods>> bmj) {

        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(getActivity(), (myAdapter.getItemCount() + 1) / 2 * 240)));
    }

    void afterLoadMore() {
        myAdapter.getMoreData(goods.RecommendedList);
    }


    @Override
    public void onHiddenChanged(boolean isHidden) {
        if (isHidden) {
            bus.unregister(this);
        } else
            bus.register(this);
    }
}
