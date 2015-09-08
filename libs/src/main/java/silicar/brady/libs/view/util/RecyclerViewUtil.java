package silicar.brady.libs.view.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

        public DefaultItemDecoration(Context context)
        {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }

        /**
         * 绘制水平线
         * @param canvas
         * @param parent
         */
        public void drawHorizontal(Canvas canvas, RecyclerView parent)
        {
            int childCount = parent.getChildCount();

            int spanCount = getSpanCount(parent);
            for (int i = 0; i < childCount; i++)
            {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
//			Log.e("spanCount", ""+params.leftMargin);
//			Log.e("spanCount", ""+params.rightMargin);
//			Log.e("spanCount", ""+params.topMargin);
//			Log.e("spanCount", ""+params.bottomMargin);
//			int left = child.getLeft() - params.leftMargin;
//			int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
//			int top = child.getBottom() + params.bottomMargin;
//			int bottom = top + mDivider.getIntrinsicHeight();
                int left = child.getLeft() - params.leftMargin;
                //int right = child.getRight() + params.rightMargin ;
                int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth()/2;
                int top = child.getBottom() + params.bottomMargin - mDivider.getIntrinsicHeight()/2;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                if (!isLastRow(parent, i, spanCount, childCount))
                    mDivider.draw(canvas);
            }
        }

        /**
         * 绘制垂直线
         * @param canvas
         * @param parent
         */
        public void drawVertical(Canvas canvas, RecyclerView parent)
        {
            int childCount = parent.getChildCount();
            int spanCount = getSpanCount(parent);
            for (int i = 0; i < childCount; i++)
            {
                final View child = parent.getChildAt(i);

                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                //			Log.e("spanCount", ""+params.leftMargin);
                //			Log.e("spanCount", ""+params.rightMargin);
                //			Log.e("spanCount", ""+params.topMargin);
                //			Log.e("spanCount", ""+params.bottomMargin);
                //			int top = child.getTop() - params.topMargin;
                //			int bottom = child.getBottom() + params.bottomMargin + mDivider.getIntrinsicHeight();
                //			int left = child.getRight() + params.rightMargin;
                //			int right = left + mDivider.getIntrinsicWidth();
                int top = child.getTop() - params.topMargin;
                //int bottom = child.getBottom() + params.bottomMargin + mDivider.getIntrinsicHeight()/2;
                int bottom = child.getBottom() + params.bottomMargin;
                int left = child.getRight() + params.rightMargin - (mDivider.getIntrinsicWidth()/2);
                int right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                if (!isLastColumn(parent, i, spanCount, childCount))
                    mDivider.draw(canvas);
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

        /**
         * 判断是否最后一列
         * @param parent
         * @param position
         * @param spanCount
         * @param childCount
         * @return
         */
        private boolean isLastColumn(RecyclerView parent, int position, int spanCount,
                                     int childCount)
        {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager)
            {
                int orientation = ((GridLayoutManager) layoutManager).getOrientation();
                if (orientation == GridLayoutManager.VERTICAL)
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
            } else if (layoutManager instanceof StaggeredGridLayoutManager)
            {
                int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
                if (orientation == StaggeredGridLayoutManager.VERTICAL)
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
            else if (layoutManager instanceof LinearLayoutManager)
            {
                int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
                if (orientation == LinearLayoutManager.VERTICAL)
                    return true;
                else
                if (position == childCount-1) return true;
            }
            return false;
        }

        /**
         * 判断是否最后一行
         * @param parent
         * @param position
         * @param spanCount
         * @param childCount
         * @return
         */
        private boolean isLastRow(RecyclerView parent, int position, int spanCount,
                                  int childCount)
        {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager)
            {
                int orientation = ((GridLayoutManager)layoutManager).getOrientation();
                // StaggeredGridLayoutManager 且纵向滚动
                if (orientation == GridLayoutManager.VERTICAL)
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
            } else if (layoutManager instanceof StaggeredGridLayoutManager)
            {
                int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
                // StaggeredGridLayoutManager 且纵向滚动
                if (orientation == StaggeredGridLayoutManager.VERTICAL)
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
            else if (layoutManager instanceof LinearLayoutManager)
            {
                int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
                if (orientation == LinearLayoutManager.VERTICAL) {
                    if (position == childCount-1) return true;}
                else
                    return true;
            }
            return false;
        }
//
//	/**
//	 * 判断是否第一列
//	 * @param parent
//	 * @param position
//	 * @param spanCount
//	 * @param childCount
//	 * @return
//	 */
//	private boolean isFirstColumn(RecyclerView parent, int position, int spanCount,
//								 int childCount)
//	{
//		LayoutManager layoutManager = parent.getLayoutManager();
//		if (layoutManager instanceof GridLayoutManager)
//		{
//			if ((position) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
//			{
//				return true;
//			}
//		} else if (layoutManager instanceof StaggeredGridLayoutManager)
//		{
//			int orientation = ((StaggeredGridLayoutManager) layoutManager)
//					.getOrientation();
//			if (orientation == StaggeredGridLayoutManager.VERTICAL)
//			{
//				if ((position + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
//				{
//					return true;
//				}
//			} else
//			{
//				childCount = childCount - childCount % spanCount;
//				if (position >= childCount)// 如果是第一列，则不需要绘制左边
//					return true;
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 判断是否第一行
//	 * @param parent
//	 * @param position
//	 * @param spanCount
//	 * @param childCount
//	 * @return
//	 */
//	private boolean isFirstRow(RecyclerView parent, int position, int spanCount,
//							  int childCount)
//	{
//		Log.e("spanCount", ""+spanCount);
//		LayoutManager layoutManager = parent.getLayoutManager();
//		if (layoutManager instanceof GridLayoutManager)
//		{
//			int orientation = ((GridLayoutManager)layoutManager).getOrientation();
//			childCount = childCount - childCount % spanCount;
//			if (orientation == GridLayoutManager.VERTICAL) {
//				if (position < spanCount)// 如果是第一行，则不需要绘制顶部
//                    return true;
//			}
//			else
//			{
//				if (position % spanCount == 0)
//					return true;
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 设置偏移(绘制Child之前)
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
//		if (isFirstRow(parent, itemPosition, spanCount, childCount))
//		{
//			//outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);// 如果是最后一行，则不需要绘制底部
//			if (itemPosition == 0)
//				outRect.set(0, 0, 0, 0);
//			else
//				outRect.set(mDivider.getIntrinsicWidth(), 0, 0, 0);
//		} else if (isFirstColumn(parent, itemPosition, spanCount, childCount))
//		{
//			//outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());// 如果是最后一列，则不需要绘制右边
//			if (itemPosition == 0)
//				outRect.set(0, 0, 0, 0);
//			else
//				outRect.set(0, mDivider.getIntrinsicHeight(), 0, 0);
//		}
//		else
//		{
//			//outRect.set(0, 0, mDivider.getIntrinsicWidth(),
//			//		mDivider.getIntrinsicHeight());//最后项绘制方式
//			outRect.set(mDivider.getIntrinsicWidth(),
//					mDivider.getIntrinsicHeight(), 0, 0);
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
