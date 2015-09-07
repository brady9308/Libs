package silicar.brady.libs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import silicar.brady.libs.view.util.CommonViewHolder;

/**
 * 通用Adapter模型
 * Created by Work on 2015/8/24.
 * @author 图图
 */
public abstract class CommonAdapter<T> extends BaseAdapter
{
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	private int mItemLayoutId;
	private int mDropDownLayoutId;
	private boolean reuse;

	public CommonAdapter(Context context, List<T> data, int itemLayoutId)
	{
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = data;
		this.mItemLayoutId = mDropDownLayoutId = itemLayoutId;
		reuse = true;
	}

	public CommonAdapter(Context context, List<T> data, int itemLayoutId, boolean reuse)
	{
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = data;
		this.mItemLayoutId = mDropDownLayoutId = itemLayoutId;
		this.reuse = reuse;
	}

	public List<T> getData()
	{
		return mDatas;
	}

	//返回适配器条目数量
	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	//获取指定位置的数据项目
	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	//获取指定位置上的行ID
	@Override
	public long getItemId(int position)
	{
		return position;
	}

	/**
	 * 设置holder对应item参数显示视图
	 * @param holder
	 * @param item
	 * @param position
	 */
	public abstract void onBindViewHolder(CommonViewHolder holder, T item, int position);

	//获取指定位置段视图
	//设置显示格式，更新初始化View时系统自动调用
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return createViewFromResource(position,convertView,parent, mItemLayoutId);

	}

	//获取指定Spinner视图
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mDropDownLayoutId);
	}

	private View createViewFromResource(int position, View convertView, ViewGroup parent, int layoutId)
	{
		//position数据项目的位置，convertView如果有旧视图重新使用，parent依附的父视图
		CommonViewHolder viewHolder;
		if (reuse)
		{
			viewHolder = CommonViewHolder.get(mContext,
					position, convertView, parent, layoutId);
		}
		else
		{
			viewHolder = CommonViewHolder.get(mContext, position, parent, layoutId);
		}
		//设置holder对应item参数显示视图
		onBindViewHolder(viewHolder, getItem(position), position);
		return viewHolder.getItemView();
	}

	//返回Spinner使用的资源布局
	public int getDropDownLayoutId() {
		return mDropDownLayoutId;
	}

	//设置Spinner使用的资源布局
	public void setDropDownViewResource(int resource) {
		this.mDropDownLayoutId = resource;
	}

	public int getItemLayoutId() {
		return mItemLayoutId;
	}

	public void setItemLayoutId(int layoutId) {
		this.mItemLayoutId = layoutId;
	}

	public boolean isReuse() {
		return reuse;
	}

	/**
	 * 设置是否使用复用
	 * @param reuse
	 */
	public void setReuse(boolean reuse) {
		this.reuse = reuse;
	}
}
