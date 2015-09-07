package silicar.brady.libs.view.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonViewHolder
{
	private final SparseArray<View> mViews;
	private int mPosition;
	private int oldPosition;
	public View itemView;

	private CommonViewHolder(Context context, ViewGroup parent, int layoutId,
							 int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		itemView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		itemView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context		上下文
	 * @param position		位置
	 * @param convertView	旧视图，没有则新建
	 * @param parent		父控件
	 * @param layoutId		视图对应的布局文件
	 * @return
	 */
	public static CommonViewHolder get(Context context, int position, View convertView,
			ViewGroup parent, int layoutId)
	{
		if (convertView == null)
		{
			return new CommonViewHolder(context, parent, layoutId, position);
		}
		//((AdapterViewHolder)convertView.getTag()).setPosition(position);
		try {
			((CommonViewHolder) convertView.getTag()).setOldPosition(((CommonViewHolder) convertView.getTag()).getPosition());
			((CommonViewHolder)convertView.getTag()).setPosition(position);
		} catch (Exception e) {
			convertView.setTag(new CommonViewHolder(context, parent, layoutId, position));
		}
		return (CommonViewHolder) convertView.getTag();
	}

	public static CommonViewHolder get(Context context, int position, ViewGroup parent, int layoutId)
	{
		return new CommonViewHolder(context, parent, layoutId, position);
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
		view.setTag(mPosition);
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

	public int getPosition()
	{
		return mPosition;
	}

	public void setPosition(int mPosition) {
		this.mPosition = mPosition;
	}

	public int getOldPosition() {
		return oldPosition;
	}

	public void setOldPosition(int oldPosition) {
		this.oldPosition = oldPosition;
	}

	public Object getTag()
	{
		return itemView.getTag();
	}
}
