package silicar.brady.libs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用多条目Adapter模型
 * Created by Work on 2015/8/24.
 * @author 图图
 */
public abstract class CommonMultiItemAdapter<T> extends CommonAdapter<T> {

    public CommonMultiItemAdapter(Context context, List<T> data, int itemLayoutId) {
        super(context, data, itemLayoutId);
    }

    public CommonMultiItemAdapter(Context context, List<T> data, int itemLayoutId, boolean reuse) {
        super(context, data, itemLayoutId, reuse);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        setItemLayoutId(getItemLayoutId(viewType));
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        setDropDownViewResource(getDropDownLayoutId(viewType));
        return super.getDropDownView(position, convertView, parent);
    }

    /**
     *  共有几种类型的Item
     * @return
     */
    @Override
    public abstract int getViewTypeCount();

    /**
     * 返回Item使用的布局的类型
     * @param position
     * @return
     */
    @Override
    public abstract int getItemViewType(int position);

    /**
     * 返回指定布局类型使用的布局文件
     *
     * @param viewType
     * @return
     */
    public int getItemLayoutId(int viewType) {
        return 0;
    }

    //返回Spinner使用的资源布局文件
    public int getDropDownLayoutId(int viewType) {
        return 0;
    }
}
