package silicar.brady.libs.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import silicar.brady.libs.view.util.RecyclerViewHolder;

/**
 * RecyclerView通用多条目Adapter
 * Created by Work on 2015/9/7.
 * @version 1.0
 * @since 2015/9/7
 * @author 图图
 */
public abstract class RecyclerMultiItemAdapter<T> extends RecyclerAdapter<T> {
    public RecyclerMultiItemAdapter(Context content, List<T> data, int itemLayoutId) {
        super(content, data, itemLayoutId);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        setItemLayoutId(getItemLayoutId(viewType));
        return super.onCreateViewHolder(parent, viewType);
    }

    /**
     * 返回Item使用的布局类型
     * @param position
     * @return
     */
    @Override
    public abstract int getItemViewType(int position);

    /**
     * 返回指定布局类型使用的布局文件
     * @param viewType
     * @return
     */
    public abstract int getItemLayoutId(int viewType);
}
