package com.zczczy.leo.fuwuwangapp.adapters;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.animators.internal.ViewHelper;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerImpl;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.items.BaseUltimateViewHolder;
import com.zczczy.leo.fuwuwangapp.items.ItemView;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2015/10/31.
 */
@EBean
public abstract class BaseUltimateRecyclerViewAdapter<T> extends UltimateViewAdapter<BaseUltimateViewHolder> implements SwipeItemManagerInterface {

    protected SwipeItemManagerImpl mItemManger = new SwipeItemManagerImpl(this);

    private List<T> items = new ArrayList<>();

    private int total = 0;

    private boolean isFirstOnly = true;

    private Interpolator mInterpolator = new LinearInterpolator();

    private int mDuration = 300;

    private int mLastPosition = 5;

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    private BindHeaderViewHolder bindHeaderViewHolder;

    @RestService
    MyDotNetRestClient myRestClient;

    @App
    MyApplication app;

    @Pref
    MyPrefs_ pre;

    @Bean
    OttoBus bus;

    @StringRes
    String no_net;

    @Bean
    MyErrorHandler myErrorHandler;

    boolean isRefresh;

    @RootContext
    Context context;

    @AfterInject
    void afterBaseInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    public int getAdapterItemCount() {

        return items.size();
    }

    /**
     * 获取更多的数据
     *
     * @param pageIndex
     * @param pageSize
     * @param objects
     */
    @Background
    public abstract void getMoreData(int pageIndex, int pageSize, boolean isRefresh, Object... objects);


    @UiThread
    protected void afterGetMoreData(BaseModelJson<PagerResult<T>> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            bmj = new BaseModelJson<>();
        } else if (bmj.Successful) {
            if (isRefresh) {
                clear();
            }
            setTotal(bmj.Data.RowCount);
            if (bmj.Data.ListData.size() > 0) {
                insertAll(bmj.Data.ListData, getItems().size());
            }
        } else {
            AndroidTool.showToast(context, bmj.Error);
        }
        bus.post(bmj);
    }


    /**
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseUltimateViewHolder viewHolder, int position) {
        if (getItemViewType(position) == VIEW_TYPES.NORMAL) {
            ItemView<T> itemView = (ItemView) viewHolder.itemView;
            itemView.init(items.get(customHeaderView != null ? position - 1 : position), this, viewHolder);
            if (viewHolder.swipeLayout != null) {
                mItemManger.updateConvertView(viewHolder, position);
            }
            setNormalClick(viewHolder);
        } else if (getItemViewType(position) == VIEW_TYPES.HEADER) {
            setHeaderClick(viewHolder);
            if (bindHeaderViewHolder != null) {
                bindHeaderViewHolder.onBindHeaderViewHolder(viewHolder);
            } else {
                onBindHeaderViewHolder(viewHolder);
            }

        } else if (getItemViewType(position) == VIEW_TYPES.FOOTER) {

        }
        if (!isFirstOnly || position > mLastPosition) {
            for (Animator anim : getAdapterAnimations(viewHolder.itemView, AdapterAnimationType.ScaleIn)) {
                anim.setDuration(mDuration).start();
                anim.setInterpolator(mInterpolator);
            }
            mLastPosition = position;
        } else {
            ViewHelper.clear(viewHolder.itemView);
        }
    }

    abstract void onBindHeaderViewHolder(BaseUltimateViewHolder viewHolder);

    /**
     * 绑定 HeaderView
     */
    public interface BindHeaderViewHolder {

        void onBindHeaderViewHolder(BaseUltimateViewHolder viewHolder);
    }

    public void setBindHeaderViewHolder(BindHeaderViewHolder bindHeaderViewHolder) {
        this.bindHeaderViewHolder = bindHeaderViewHolder;
    }

    @Override
    public BaseUltimateViewHolder getViewHolder(View view) {

        return new BaseUltimateViewHolder(view);
    }

    @Override
    public BaseUltimateViewHolder onCreateViewHolder(ViewGroup parent) {
        final View view = onCreateItemView(parent);
        // //修正 item不充满
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        final BaseUltimateViewHolder baseViewHolder = getViewHolder(view);
        SwipeLayout swipeLayout = baseViewHolder.swipeLayout;
        if (swipeLayout != null) {
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {

                }
            });
            swipeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SwipeLayout) v).close();
                }
            });
            swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getOpenItems().get(0) != -1) {
                        closeItem(getOpenItems().get(0));
                        closeAllExcept(null);
                    } else {
                        view.performClick();
                    }
                }
            });
            swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (getOpenItems().get(0) != -1) {
                        //closeItem(getOpenItems().get(0));
                        closeAllExcept(null);
                    } else {
                        view.performLongClick();
                    }
                    return false;
                }
            });
        }
        return baseViewHolder;
    }

    /**
     * 设置普通itemclick事件
     *
     * @param viewHolder
     */
    private void setNormalClick(final BaseUltimateViewHolder viewHolder) {
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getOpenItems().get(0) != -1 && viewHolder.swipeLayout != null) {
                        //closeItem(getOpenItems().get(0));
                        closeAllExcept(null);
                    } else {
                        onItemClickListener.onItemClick(viewHolder, items.get(customHeaderView != null ? viewHolder.getAdapterPosition() - 1 : viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                    }
                }
            });
        }
        if (onItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (getOpenItems().get(0) != -1 && viewHolder.swipeLayout != null) {
                        //closeItem(getOpenItems().get(0));
                        closeAllExcept(null);
                    } else {
                        onItemLongClickListener.onItemLongClick(viewHolder, items.get(customHeaderView != null ? viewHolder.getAdapterPosition() - 1 : viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 设置头部点击事件
     *
     * @param viewHolder
     */
    private void setHeaderClick(final BaseUltimateViewHolder viewHolder) {

        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getOpenItems().get(0) != -1) {
                        //closeItem(getOpenItems().get(0));
                        closeAllExcept(null);
                    } else {
                        onItemClickListener.onHeaderClick(viewHolder, viewHolder.getAdapterPosition());
                    }
                }
            });
        }
        if (onItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (getOpenItems().get(0) != -1) {
                        //closeItem(getOpenItems().get(0));
                        closeAllExcept(null);
                    } else {
                        onItemLongClickListener.onHeaderLongClick(viewHolder, viewHolder.getAdapterPosition());
                    }
                    return false;
                }
            });
        }

    }


    /**
     * 设置 ItemView
     *
     * @param parent
     * @return
     */
    protected abstract View onCreateItemView(ViewGroup parent);


    public void insert(T object, int position) {
        items.add(position, object);
        if (customHeaderView != null) position++;
        notifyItemInserted(position);
    }

    public void insertAll(List<T> list, int position) {
        items.addAll(position, list);
        if (customHeaderView != null) position++;
        notifyItemInserted(position);
    }


    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Remove a item of  the list of the adapter
     *
     * @param position position
     */
    public void remove(int position) {
        if (items.size() > 0 && getItemViewType(position) == VIEW_TYPES.NORMAL) {
            items.remove(customHeaderView != null ? position - 1 : position);
            notifyItemRemoved(position);
        } else {
            customHeaderView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Clear the list of the adapter
     */
    public void clear() {
        int size = items.size();
        items.clear();
        total = 0;
        notifyItemRangeRemoved(customHeaderView != null ? 1 : 0, size);
    }


    @Override
    public void onItemDismiss(int position) {
        remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        swapPositions(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * allow resource layout id to be introduced
     *
     * @param mLayout res id
     */
    public void setCustomLoadMoreView(Context context, @LayoutRes int mLayout) {
        View h_layout = LayoutInflater.from(context).inflate(mLayout, null);
        setCustomLoadMoreView(h_layout);
    }

    /**
     * allow resource layout id to be introduced
     *
     * @param mLayout res id
     */
    public void setCustomLoadMoreView(@LayoutRes int mLayout) {
        View h_layout = LayoutInflater.from(context).inflate(mLayout, null);
        setCustomLoadMoreView(h_layout);
    }


    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public SwipeItemManagerImpl.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(SwipeItemManagerImpl.Mode mode) {
        mItemManger.setMode(mode);
    }


    @Override
    public long generateHeaderId(int position) {
//        if (getItems().get(customHeaderView != null ? position - 1 : position).Error.length() > 0)
//            return getItems().get(customHeaderView != null ? position - 1 : position).Error.charAt(0);
//        else return -1;
        return -1;
    }


    /**
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {

        void onItemClick(RecyclerView.ViewHolder viewHolder, T obj, int position);

        void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position);

    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener<T> {

        void onItemLongClick(RecyclerView.ViewHolder viewHolder, T obj, int position);

        void onHeaderLongClick(RecyclerView.ViewHolder viewHolder, int position);

    }

    public class VIEW_TYPES {
        public static final int NORMAL = 0;
        public static final int HEADER = 1;
        public static final int FOOTER = 2;
        public static final int CHANGED_FOOTER = 3;
    }
}
