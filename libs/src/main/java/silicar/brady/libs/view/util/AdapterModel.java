package silicar.brady.libs.view.util;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import silicar.brady.libs.adapter.CommonAdapter;

/**
 * Adapter通用抽象模型
 * Created by Work on 2015/10/15.
 */
public abstract class AdapterModel<T> {

    public static final int POSITION = Integer.MIN_VALUE + 1;
    public static final int VIEW_HOLDER = Integer.MIN_VALUE + 2;

    protected Object TAG;
    protected Context mContext;

    public List<T> list;
    public CommonAdapter<T> adapter;

    //Item控件事件监听
    //protected View.OnKeyListener mOnItemKeyListener;
    protected View.OnClickListener mOnItemClickListener;
    protected View.OnLongClickListener mOnItemLongClickListener;
    //protected View.OnKeyListener mOnChildKeyListener;
    protected View.OnClickListener mOnChildClickListener;
    protected View.OnLongClickListener mOnChildLongClickListener;

    //Adapter回调接口
    //protected AdapterInterface.OnItemKeyListener onItemKeyListener;
    protected AdapterInterface.OnItemClickListener onItemClickListener;
    protected AdapterInterface.OnItemLongClickListener onItemLongClickListener;
    //protected AdapterInterface.OnChildKeyListener onChildKeyListener;
    protected AdapterInterface.OnChildClickListener onChildClickListener;
    protected AdapterInterface.OnChildLongClickListener onChildLongClickListener;

    public AdapterModel(Context context, int layoutId) {
        mContext = context;
        list = new ArrayList<>();
        //回调接口返回true则拦截当前类方法,false则执行回调接口和当前类方法
        mOnItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag(POSITION);
                if (onItemClickListener != null) {
                    if (!onItemClickListener.onItemClick(AdapterModel.this, v, position))
                        onItemClick(AdapterModel.this, v, position);
                }
                else
                    onItemClick(AdapterModel.this, v, position);
            }
        };
        mOnItemLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = (int) v.getTag(POSITION);
                if (onItemLongClickListener != null)
                    return onItemLongClickListener.onItemLongClick(AdapterModel.this, v, position) ? true : onItemLongClick(AdapterModel.this, v, position);
                return onItemLongClick(AdapterModel.this, v, position);
            }
        };
        mOnChildClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag(POSITION);
                if (onChildClickListener != null){
                    if (!onChildClickListener.onChildClick(AdapterModel.this, v, position))
                        onChildClick(AdapterModel.this, v, position);
                }
                else
                    onChildClick(AdapterModel.this, v, position);
            }
        };
        mOnChildLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = (int) v.getTag(POSITION);
                if (onItemLongClickListener != null)
                    return onChildLongClickListener.onChildLongClick(AdapterModel.this, v, position) ? true : onChildLongClick(AdapterModel.this, v, position);
                return onChildLongClick(AdapterModel.this, v, position);
            }
        };
    }

    //Adapter事件处理
    protected abstract void onItemClick(AdapterModel adapterModel, View view, int position);

    protected abstract void onChildClick(AdapterModel adapterModel, View view, int position);

    protected abstract boolean onItemLongClick(AdapterModel adapterModel, View view, int position);

    protected abstract boolean onChildLongClick(AdapterModel adapterModel, View view, int position);

    public Object getTAG() {
        return TAG;
    }

    public void setTAG(Object TAG) {
        this.TAG = TAG;
    }

    //Adapter外部回调接口
    public void setOnItemClickListener(AdapterInterface.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterInterface.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnChildClickListener(AdapterInterface.OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public void setOnChildLongClickListener(AdapterInterface.OnChildLongClickListener onChildLongClickListener) {
        this.onChildLongClickListener = onChildLongClickListener;
    }

    public static interface AdapterInterface
    {
        interface OnItemKeyListener{
            boolean onItemKey();
        }

        interface OnItemClickListener{
            boolean onItemClick(AdapterModel adapterModel, View view, int position);
        }

        interface OnItemLongClickListener{
            boolean onItemLongClick(AdapterModel adapterModel, View view, int position);
        }

        interface OnChildKeyListener{
            boolean onChildKey();
        }

        interface OnChildClickListener{
            boolean onChildClick(AdapterModel adapterModel, View view, int position);
        }

        interface OnChildLongClickListener{
            boolean onChildLongClick(AdapterModel adapterModel, View view, int position);
        }
    }
}
