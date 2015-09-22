package silicar.brady.libs.view.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView工具类
 * Created by Work on 2015/9/8.
 * @version 1.0
 * @since 2015/9/8
 * @author 图图
 */
public class RecyclerViewUtil {

    /**
     * 分割线实现类
     * 实现瀑布流以外的布局添加分割线
     */
    public static class DefaultItemDecoration extends RecyclerView.ItemDecoration
    {

        private static final int[] ATTRS = new int[] { android.R.attr.listDivider };
        private Drawable mDivider;
        private Drawable mHorizontalDivider;
        private Drawable mVerticalDivider;

        public static int isLinearLayoutManager = 1 << 1;
        public static int isGridLayoutManager = isLinearLayoutManager << 1;
        public static int isStaggeredGridLayoutManager = isGridLayoutManager << 1;

        public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
        public static final int VERTICAL = OrientationHelper.VERTICAL;

        public DefaultItemDecoration(Context context)
        {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            setDivider(a.getDrawable(0));
            a.recycle();
        }

        @Override
        public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(canvas, parent, state);
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            int foLayoutManager = getLayoutManager(layoutManager);
            if ((foLayoutManager & VERTICAL) == VERTICAL)
            {
                drawVertical(canvas, parent, foLayoutManager);
                drawHorizontal(canvas, parent, foLayoutManager);
            }
            else
            {
                drawHorizontal(canvas, parent, foLayoutManager);
                drawVertical(canvas, parent, foLayoutManager);
            }
        }

        /**
         * 绘制水平线
         * @param canvas
         * @param parent
         * @param foLayoutManager
         */
        public void drawHorizontal(Canvas canvas, RecyclerView parent, int foLayoutManager)
        {
            int spanCount = getSpanCount(parent);
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++)
            {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                int left = child.getLeft() - params.leftMargin;
                int right = child.getRight() + params.rightMargin;
//			if ((foLayoutManager & VERTICAL) == VERTICAL)
//				right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth()/2;
//			else
//				right = child.getRight() + params.rightMargin;
                int top = child.getBottom() + params.bottomMargin - mHorizontalDivider.getIntrinsicHeight()/2;
                int bottom = top + mHorizontalDivider.getIntrinsicHeight();
                mHorizontalDivider.setBounds(left, top, right, bottom);
                if (!isLastRow(foLayoutManager, i, spanCount, childCount))
                    mHorizontalDivider.draw(canvas);
            }
        }

        /**
         * 绘制垂直线
         * @param canvas
         * @param parent
         * @param foLayoutManager
         */
        public void drawVertical(Canvas canvas, RecyclerView parent, int foLayoutManager)
        {
            int spanCount = getSpanCount(parent);
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++)
            {
                final View child = parent.getChildAt(i);

                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                int top = child.getTop() - params.topMargin;
                int bottom = child.getBottom() + params.bottomMargin;
//			if ((foLayoutManager & VERTICAL) == VERTICAL)
//				bottom = child.getBottom() + params.bottomMargin;
//			else
//				bottom = child.getBottom() + params.bottomMargin + mDivider.getIntrinsicHeight()/2;
//			if ((foLayoutManager & isLinearLayoutManager) == isLinearLayoutManager)
//				bottom = child.getBottom() + params.bottomMargin;
                int left = child.getRight() + params.rightMargin - (mVerticalDivider.getIntrinsicWidth()/2);
                int right = left + mVerticalDivider.getIntrinsicWidth();
                mVerticalDivider.setBounds(left, top, right, bottom);
                if (!isLastColumn(foLayoutManager, i, spanCount, childCount))
                    mVerticalDivider.draw(canvas);
            }
        }

        /**
         * 获得分组数
         * @param parent
         * @return
         */
        private int getSpanCount(RecyclerView parent)
        {
            // 列数
            int spanCount = -1;
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager)
            {

                spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            } else if (layoutManager instanceof StaggeredGridLayoutManager)
            {
                spanCount = ((StaggeredGridLayoutManager) layoutManager)
                        .getSpanCount();
            }
            return spanCount;
        }

        private int getLayoutManager(RecyclerView.LayoutManager layoutManager)
        {
            if (layoutManager instanceof StaggeredGridLayoutManager)
            {
                int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
                if (orientation == VERTICAL)
                    return isStaggeredGridLayoutManager | VERTICAL;
                else
                    return isStaggeredGridLayoutManager | HORIZONTAL;
            }
            else if (layoutManager instanceof GridLayoutManager)
            {
                int orientation = ((GridLayoutManager) layoutManager).getOrientation();
                if (orientation == VERTICAL)
                    return isGridLayoutManager | VERTICAL;
                else
                    return isGridLayoutManager | HORIZONTAL;
            }
            else if (layoutManager instanceof LinearLayoutManager)
            {
                int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
                if (orientation == VERTICAL)
                    return isLinearLayoutManager | VERTICAL;
                else
                    return isLinearLayoutManager | HORIZONTAL;
            }
            return -1;
        }

        /**
         * 判断是否最后一列
         * @param foLayoutManager
         * @param position
         * @param spanCount
         * @param childCount
         * @return
         */
        private boolean isLastColumn(int foLayoutManager, int position, int spanCount, int childCount)
        {
            if ((foLayoutManager & isStaggeredGridLayoutManager) == isStaggeredGridLayoutManager)
            {
                if ((foLayoutManager & VERTICAL) == VERTICAL)
                {
                    if ((position + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                    {
                        return true;
                    }
                } else
                {
                    childCount = childCount - (childCount - 1) % spanCount -1;
                    if (position >= childCount)// 如果是最后一列，则不需要绘制右边
                        return true;
                }
            }
            else if ((foLayoutManager & isGridLayoutManager) == isGridLayoutManager)
            {
                if ((foLayoutManager & VERTICAL) == VERTICAL)
                {
                    if ((position + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                    {
                        return true;
                    }
                } else
                {
                    childCount = childCount - (childCount - 1) % spanCount -1;
                    if (position >= childCount)// 如果是最后一列，则不需要绘制右边
                        return true;
                }
            }
            else if ((foLayoutManager & isLinearLayoutManager) == isLinearLayoutManager)
            {
                if ((foLayoutManager & VERTICAL) == VERTICAL)
                    return true;
                else
                if (position == childCount-1) return true;
            }
            return false;
        }

        /**
         * 判断是否最后一行
         * @param foLayoutManager
         * @param position
         * @param spanCount
         * @param childCount
         * @return
         */
        private boolean isLastRow(int foLayoutManager, int position, int spanCount, int childCount)
        {
            if ((foLayoutManager & isStaggeredGridLayoutManager) == isStaggeredGridLayoutManager)
            {
                if ((foLayoutManager & VERTICAL) == VERTICAL)
                {
                    childCount = childCount - (childCount - 1) % spanCount -1;
                    // 如果是最后一行，则不需要绘制底部
                    if (position >= childCount)
                        return true;
                } else
                // StaggeredGridLayoutManager 且横向滚动
                {
                    // 如果是最后一行，则不需要绘制底部
                    if ((position + 1) % spanCount == 0)
                    {
                        return true;
                    }
                }
            }
            else if ((foLayoutManager & isGridLayoutManager) == isGridLayoutManager)
            {
                if ((foLayoutManager & VERTICAL) == VERTICAL)
                {
                    childCount = childCount - (childCount - 1) % spanCount -1;
                    // 如果是最后一行，则不需要绘制底部
                    if (position >= childCount)
                        return true;
                } else
                {
                    // 如果是最后一行，则不需要绘制底部
                    if ((position + 1) % spanCount == 0)
                    {
                        return true;
                    }
                }
            }
            else if ((foLayoutManager & isLinearLayoutManager) == isLinearLayoutManager)
            {
                if ((foLayoutManager & VERTICAL) == VERTICAL) {
                    if (position == childCount-1) return true;}
                else
                    return true;
            }
            return false;
        }

        public Drawable getDivider() {
            return mDivider;
        }

        /**
         * 设置分割线样式
         * @param divider
         */
        public void setDivider(Drawable divider) {
            mHorizontalDivider = mVerticalDivider = mDivider = divider;
        }


        public Drawable getHorizontalDivider() {
            return mHorizontalDivider;
        }

        /**
         * 设置水平线样式
         * @param horizontalDivider
         */
        public void setHorizontalDivider(Drawable horizontalDivider) {
            this.mHorizontalDivider = horizontalDivider;
            mDivider = null;
        }

        public Drawable getVerticalDivider() {
            return mVerticalDivider;
        }

        /**
         * 设置垂直线样式
         * @param verticalDivider
         */
        public void setVerticalDivider(Drawable verticalDivider) {
            this.mVerticalDivider = verticalDivider;
            mDivider = null;
        }

//	/**
//	 * 设置偏移(绘制Child之前)
//	 * 鉴于绘制在Child之前,动画效果在Child之后,这里不进行绘制避免错位
//	 * @param outRect
//	 * @param view
//	 * @param parent
//	 * @param state
//	 */
//	@Override
//	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
//		super.getItemOffsets(outRect, view, parent, state);
//		int spanCount = getSpanCount(parent);
//		int childCount = parent.getAdapter().getItemCount();
//		int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
//		LayoutManager layoutManager = parent.getLayoutManager();
//		int foLayoutManager = getLayoutManager(layoutManager);
//		if (isLastRow(foLayoutManager, itemPosition, spanCount, childCount))
//		{
//			outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);// 如果是最后一行，则不需要绘制底部
//		} else if (isLastColumn(foLayoutManager, itemPosition, spanCount, childCount))
//		{
//			outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());// 如果是最后一列，则不需要绘制右边
//		}
//		else
//		{
//			outRect.set(0, 0, mDivider.getIntrinsicWidth(),
//					mDivider.getIntrinsicHeight());//最后项绘制方式
//		}
//	}
    }

    /**
     * RecyclerView通用ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> mViews;

        public ViewHolder(View itemView) {
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
}
