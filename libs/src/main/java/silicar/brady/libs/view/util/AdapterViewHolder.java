package silicar.brady.libs.view.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterViewHolder
{
	private final SparseArray<View> mViews;
	private int mPosition;
	private int oldPosition;
	private View mConvertView;

	private AdapterViewHolder(Context context, ViewGroup parent, int layoutId,
							  int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
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
	public static AdapterViewHolder get(Context context, int position, View convertView,
			ViewGroup parent, int layoutId)
	{
		if (convertView == null)
		{
			return new AdapterViewHolder(context, parent, layoutId, position);
		}
		//((AdapterViewHolder)convertView.getTag()).setPosition(position);
		try {
			((AdapterViewHolder) convertView.getTag()).setOldPosition(((AdapterViewHolder) convertView.getTag()).getPosition());
			((AdapterViewHolder)convertView.getTag()).setPosition(position);
		} catch (Exception e) {
			convertView.setTag(new AdapterViewHolder(context, parent, layoutId, position));
		}
		return (AdapterViewHolder) convertView.getTag();
	}

	public static AdapterViewHolder get(Context context, int position, ViewGroup parent, int layoutId)
	{
		return new AdapterViewHolder(context, parent, layoutId, position);
	}

	public View getConvertView()
	{
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId)
	{
		View view = mViews.get(viewId);
		if (view == null)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		view.setTag(mPosition);
		return (T) view;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public AdapterViewHolder setText(int viewId, String text)
	{
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public AdapterViewHolder setImageResource(int viewId, int drawableId)
	{
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param bm
	 * @return
	 */
	public AdapterViewHolder setImageBitmap(int viewId, Bitmap bm)
	{
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
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
		return mConvertView.getTag();
	}
}
