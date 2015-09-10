package silicar.brady.libs.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
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
    private int mPageLimit = 3;
    private SparseArray<View> mViews;

    public CommonPagerAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mDatas = data;
        mViews = new SparseArray<View>();
    }

    /**
     * 获取显示视图
     * @param context
     * @param item
     * @return
     */
    public abstract View getView(Context context, ItemModel<T> item);

    /**
     * 要显示的视图可以进行缓存的时候，会调用这个方法进行初始化
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(getView(mContext, getItem(position)));
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
        container.removeView(getView(mContext, getItem(position)));
        //container.removeView(getView((T)object));
        //设置缓存为空
        //putView(position, null);
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
        return view == getView(mContext, (ItemModel<T>) object);
    }

    /**
     * Adapter调用时,获取当前选中的Item
     * @param container
     * @param position 当前页面位置
     * @param object
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
     * 设置缓存的View数量,默认为3
     * ViewPager修改了缓存数量后记得修改
     * @param pageLimit
     */
    public void setOffscreenPageLimit(int pageLimit)
    {
        mPageLimit = pageLimit;
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
