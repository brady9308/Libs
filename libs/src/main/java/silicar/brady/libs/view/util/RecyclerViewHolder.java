package silicar.brady.libs.view.util;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView通用ViewHolder
 * Created by Work on 2015/9/6.
 * @version 1.0
 * @since 2015/9/6
 * @author 图图
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<View>();
    }

    public View getItemView()
    {
        return itemView;
    }

    /**
     * 通过控件的Id获取对应的子控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public View getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        //view.setTag(mPosition);
        return  view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public TextView setText(int viewId, String text)
    {
        TextView view = (TextView) getView(viewId);
        view.setText(text);
        return view;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ImageView setImageResource(int viewId, int drawableId)
    {
        ImageView view = (ImageView) getView(viewId);
        view.setImageResource(drawableId);
        return view;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ImageView setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = (ImageView) getView(viewId);
        view.setImageBitmap(bm);
        return view;
    }
}
