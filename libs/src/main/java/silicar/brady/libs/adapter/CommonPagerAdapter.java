package silicar.brady.libs.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用PagerAdapter模型
 * Created by Work on 2015/9/10.
 * @version 1.0
 * @since 2015/9/10
 * @author 图图
 */
public abstract class CommonPagerAdapter<T> extends PagerAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    private boolean reuse;
    private int mPageLimit;
    private SparseArray<View> mViews;

    public CommonPagerAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mDatas = data;
        reuse = false;
        setOffscreenPageLimit(1);
        mViews = new SparseArray<View>();
    }

    /**
     * 必须每个Pager都使用同一布局文件,才可使用reuse复用
     * @param context
     * @param data
     * @param reuse 设置是否复用,默认为false
     */
    public CommonPagerAdapter(Context context, List<T> data, boolean reuse) {
        this.mContext = context;
        this.mDatas = data;
        this.reuse = reuse;
        setOffscreenPageLimit(1);
        mViews = new SparseArray<View>();
    }

    /**
     * 获取显示视图
     * @param context
     * @param item
     * @return
     */
    public abstract View getView(Context context, ViewGroup container, ItemModel<T> item);

    /**
     * 要显示的视图可以进行缓存的时候，会调用这个方法进行初始化
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //Log.e("instantiateItem", "" + position);
        container.addView(getView(mContext, container, getItem(position)));
        //for (int i = 0; i < mPageLimit; i++)
        //    Log.e("getView", "v:" + getView(i) + "p:" + position);
        //Log.e("getView", "----------");
        return getItem(position);
    }

    /**
     * 视图超出了缓存的范围调用此方法销毁
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Log.e("destroyItem", "" + position);
        //container.removeView(getView(mContext, container, getItem(position)));
        container.removeView(getView(mContext, container, (ItemModel<T>)object));
        //设置是否复用,
        if (!reuse)
            putView(position, null);
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
    public ItemModel<T> getItem(int position)
    {
        return new ItemModel<T>(position, mDatas.get(position));
    }

    /**
     * 来判断显示的是否是当前视图
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        //Log.e("isViewFromObject", "");
        return view == getView(mContext, null, (ItemModel<T>) object);
    }

    /**
     * Adapter调用时,获取当前选中的Item
     * @param container
     * @param position 当前页面位置
     * @param object 当前页面Item
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    /**
     * 绑定layoutId,创建View
     * @param context
     * @param container
     * @param layoutId
     * @return
     */
    public View createView(Context context, ViewGroup container, int layoutId)
    {
        return LayoutInflater.from(context).inflate(layoutId, container, false);
    }

    /**
     * 通过key获取缓存的View
     * @param key
     * @return
     */
    public View getView(int key)
    {
        View view = mViews.get(key % mPageLimit);
        return  view;
    }

    /**
     * 缓存View
     * @param key
     * @param view
     */
    public void putView(int key, View view)
    {
        mViews.put(key % mPageLimit, view);
    }

    /**
     * 设置预读并缓存的View数量,默认为4个缓存页
     * 由于切换前一页destroyItem在instantiateItem之后,所以需比ViewPager多缓存一个页面
     * ViewPager修改了预读数量后记得修改
     * @param pageLimit 默认为1
     */
    public void setOffscreenPageLimit(int pageLimit)
    {
        mPageLimit = (pageLimit * 2 + 2);
    }

    public class ItemModel<T>
    {
        public int position;
        public T data;

        public ItemModel(int position, T data) {
            this.position = position;
            this.data = data;
        }
    }
}