package silicar.brady.libs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import silicar.brady.libs.view.util.AdapterViewHolder;

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
	 * 设置helper对应item参数显示视图
	 * @param helper
	 * @param item
	 * @param position
	 */
	public abstract void convert(AdapterViewHolder helper, T item, int position);

	//获取指定位置段视图
	//设置显示格式，更新初始化View时系统自动调用
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return createViewFromResource(position,convertView,parent, mItemLayoutId);

	}

	private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource)
	{
		//position数据项目的位置，convertView如果有旧视图重新使用，parent依附的父视图
		AdapterViewHolder viewHolder;
		if (reuse)
		{
			viewHolder = AdapterViewHolder.get(mContext,
					position, convertView, parent, resource);
		}
		else
		{
			viewHolder = AdapterViewHolder.get(mContext, position, parent, resource);
		}
		//设置helper对应item参数显示视图
		convert(viewHolder, getItem(position), position);
		return viewHolder.getConvertView();
	}

	//设置Spinner使用的资源布局
	public void setDropDownViewResource(int resource) {
		this.mDropDownLayoutId = resource;
	}

	//获取指定Spinner视图
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mDropDownLayoutId);
	}

	public int getLayoutId() {
		return mItemLayoutId;
	}

	public void setLayoutId(int layoutId) {
		this.mItemLayoutId = layoutId;
	}

	public boolean isReuse() {
		return reuse;
	}

	public void setReuse(boolean reuse) {
		this.reuse = reuse;
	}
}
