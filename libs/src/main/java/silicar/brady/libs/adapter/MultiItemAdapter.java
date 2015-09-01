package silicar.brady.libs.adapter;

import android.content.Context;

import java.util.List;

/**
 * 通用多条目Adapter模型
 * Created by Work on 2015/8/24.
 * @author 图图
 */
public abstract class MultiItemAdapter<T> extends CommonAdapter<T> {

    public MultiItemAdapter(Context context, List<T> data, int itemLayoutId) {
        super(context, data, itemLayoutId);
    }

    public MultiItemAdapter(Context context, List<T> data, int itemLayoutId, boolean reuse) {
        super(context, data, itemLayoutId, reuse);
    }

    /**
     *  共有几种类型的Item
     * @return
     */
    @Override
    public abstract int getViewTypeCount();

    /**
     * 返回Item的类型
     * @param position
     * @return
     */
    @Override
    public abstract int getItemViewType(int position);
}
