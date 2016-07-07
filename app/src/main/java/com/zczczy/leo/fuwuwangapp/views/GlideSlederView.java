package com.zczczy.leo.fuwuwangapp.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import java.io.File;

/**
 * Created by leo on 2016/7/8.
 */
public class GlideSlederView extends BaseSliderView {

    private RequestManager mGlide;

    private String mUrl;
    private File mFile;
    private int mRes;

    protected GlideSlederView(Context context) {
        super(context);
    }

    protected void bindEventAndShow(final View v, ImageView targetImageView) {
        final BaseSliderView me = this;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(me);
                }
            }
        });

        if (targetImageView == null)
            return;

//        if (mLoadListener != null) {
//            mLoadListener.onStart(me);
//        }
        RequestManager p = (mGlide != null) ? mGlide : Glide.with(mContext);
        DrawableTypeRequest rq = null;
        if (mUrl != null) {
            rq = p.load(mUrl);
        } else if (mFile != null) {
            rq = p.load(mFile);
        } else if (mRes != 0) {
            p.load(mRes);
        } else {
            return;
        }
        if (rq == null) {
            return;
        }
        if (getEmpty() != 0) {
            rq.placeholder(getEmpty());
        }
        if (getError() != 0) {
            rq.error(getError());
        }
        rq.crossFade().centerCrop().into(targetImageView);
    }


    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_default, null);
        ImageView target = (ImageView) v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);
        bindEventAndShow(v, target);
        return v;
    }
}
