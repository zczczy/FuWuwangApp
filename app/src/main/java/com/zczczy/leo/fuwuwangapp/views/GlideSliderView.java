package com.zczczy.leo.fuwuwangapp.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import java.io.File;

/**
 * Created by leo on 2016/7/8.
 */
public class GlideSliderView extends BaseSliderView {

    private RequestManager mGlide;

    private String mUrl;
    private File mFile;
    private int mRes;

    private ImageLoadListener mLoadListener;


    public GlideSliderView(Context context) {
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

        if (mLoadListener != null) {
            mLoadListener.onStart(me);
        }
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
        v.findViewById(com.daimajia.slider.library.R.id.loading_bar).setVisibility(View.INVISIBLE);
        rq.diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).skipMemoryCache(true).centerCrop().into(targetImageView);
    }

    /**
     * set a url as a image that preparing to load
     *
     * @param url
     * @return
     */
    public BaseSliderView image(String url) {
        if (mFile != null || mRes != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mUrl = url;
        return this;
    }

    /**
     * set a file as a image that will to load
     *
     * @param file
     * @return
     */
    public BaseSliderView image(File file) {
        if (mUrl != null || mRes != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mFile = file;
        return this;
    }

    public BaseSliderView image(int res) {
        if (mUrl != null || mFile != null) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mRes = res;
        return this;
    }

    /**
     * set a listener to get a message , if load error.
     *
     * @param l
     */
    public void setOnImageLoadListener(ImageLoadListener l) {
        mLoadListener = l;
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_default, null);
        ImageView target = (ImageView) v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);
        bindEventAndShow(v, target);
        return v;
    }

    public void setmGlide(RequestManager mGlide) {
        this.mGlide = mGlide;
    }
}


