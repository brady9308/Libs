package silicar.brady.libs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import silicar.brady.libs.view.util.CommonViewHolder;
import silicar.brady.libs.view.util.RecyclerViewUtil.ViewHolder;
import silicar.brady.libs.view.util.RecyclerViewUtil;

/**
 * RecyclerView通用Adapter
 * Created by Work on 2015/9/6.
 * @version 1.0
 * @since 2015/9/6
 * @author 图图
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewUtil.ViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;
    private int mItemLayoutId;

    public RecyclerAdapter(Context content, List<T> data, int itemLayoutId) {
        this.mContext = content;
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = data;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public RecyclerViewUtil.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewUtil.ViewHolder holder = new RecyclerViewUtil.ViewHolder(
                mInflater.inflate(mItemLayoutId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewUtil.ViewHolder holder, int position) {
        onBindViewHolder(holder, getItem(position), position);
    }

    /**
     * 绑定ViewHolder数据
     * @param holder
     * @param item
     * @param position
     */
    public abstract void onBindViewHolder(RecyclerViewUtil.ViewHolder holder, T item, int position);

    /**
     * 返回Adapter数据集
     * @return
     */
    public List<T> getData()
    {
        return mDatas;
    }

    /**
     * 返回Adapter数据条目数量
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 返回指定数据条目内容
     * @param position
     * @return
     */
    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    /**
     * 返回指定数据条目的ID
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * 设置当前使用的布局
     * @param layoutId
     */
    public void setItemLayoutId(int layoutId) {
        this.mItemLayoutId = layoutId;
    }

    /**
     * 返回当前使用的布局
     * @return
     */
    public int getItemLayoutId() {
        return mItemLayoutId;
    }
}
