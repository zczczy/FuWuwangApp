package com.zczczy.leo.fuwuwangapp.fragments;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.DetailGoodsCommentsAdapter;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsCommentsModel;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.tools.DisplayUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * @author Created by LuLeo on 2016/6/16.
 *         you can contact me at :361769045@qq.com
 * @since 2016/6/16.
 */
@EFragment(R.layout.fragment_goods_comments)
public class GoodsCommentsFragment extends BaseFragment {

    @Bean
    OttoBus bus;

    @ViewById
    LinearLayout parent;

    @FragmentArg
    String goodsId;

    @Bean(DetailGoodsCommentsAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    @AfterViews
    void afterView() {
        afterLoadMore();
    }

    @Subscribe
    public void notifyUI(BaseModelJson<PagerResult<GoodsCommentsModel>> bmj) {
        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(getActivity(), myAdapter.getItemCount() * 60)));
    }

    void afterLoadMore() {
        myAdapter.getMoreData(goodsId);
    }


    @Override
    public void onHiddenChanged(boolean isHidden) {
        if (isHidden) {
            bus.unregister(this);
        } else
            bus.register(this);
    }
}
