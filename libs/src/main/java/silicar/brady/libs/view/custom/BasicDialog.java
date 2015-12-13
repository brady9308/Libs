package silicar.brady.libs.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import silicar.brady.libs.view.interfaces.DialogInitInterface;

/**
 * Created by Brady on 2015/10/14.
 */
public abstract class BasicDialog extends Dialog {

    private View mDecor;
    protected DisplayMetrics mMetrics;
    protected int orientation;
    protected int minHeight;
    protected int maxHeight;
    protected boolean defaultHeight;

    protected DialogInitInterface.OnDialogInitListener onDialogInitListener;

    public BasicDialog(Context context) {
        super(context);
        init();
    }

    public BasicDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    protected BasicDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    protected void init()
    {
        minHeight = -1;
        maxHeight = -1;
        mMetrics = getDisplayMetrics();
        orientation = getContext().getResources().getConfiguration().orientation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initView();
        initListener();
        setListener();
    }

    protected void initView()
    {
        mDecor  = getWindow().getDecorView();
    }

    protected void initListener() {
        if (onDialogInitListener != null)
            onDialogInitListener.initDialog(this, getWindow().getDecorView());
        //通过measure计算高度会出现二次绘制重叠的错误
        //int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        //mDecor.measure(measureSpec, measureSpec);
        //4.3版本如果布局发生改变,横竖屏切换会保留Dialog
        //getWindow().getAttributes().height = ViewGroup.LayoutParams.MATCH_PARENT;
        mDecor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e("height", "-" + mDecor.getMeasuredHeight());
                Log.e("layout-height", "-" + mDecor.getLayoutParams().height);

                if (maxHeight != -1 && mDecor.getMeasuredHeight() > maxHeight && mDecor.getMeasuredHeight() > minHeight) {
                    Log.e("max-height", "-" + maxHeight);
                    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                    //Log.e("layout-height", "-" + layoutParams.height);
                    layoutParams.height = maxHeight;
                    getWindow().setAttributes(layoutParams);
                    //setLayout在RelativeLayout才有效,mDecor.setLayoutParams无效
                    //getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, maxHeight);
                } else
                if (minHeight != -1 && mDecor.getMeasuredHeight() < minHeight) {
                    //mDecor.setMinimumHeight(minHeight);
                    Log.e("min-height", "-" + minHeight);
                    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                    layoutParams.height = minHeight;
                    getWindow().setAttributes(layoutParams);
                } else
                if (mDecor.getMeasuredHeight() != minHeight && mDecor.getMeasuredHeight() != maxHeight){
                    Log.e("other-height", "-" + mDecor.getMeasuredHeight());
                    Log.e("other-height", "-" + minHeight);
                    Log.e("other-height", "-" + maxHeight);
                    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                    if (layoutParams.height != mDecor.getMeasuredHeight()) {
                        layoutParams.height = mDecor.getMeasuredHeight();
                        getWindow().setAttributes(layoutParams);
                    }
                }

                if (orientation != getContext().getResources().getConfiguration().orientation) {
                    orientation = getContext().getResources().getConfiguration().orientation;
                    mMetrics = getDisplayMetrics();
                    if (defaultHeight)
                        setDefaultHeight();
                    requestLayout();
                    Log.e("request-height", "-" + mDecor.getMeasuredHeight());
                    Log.e("layout-height", "-" + mDecor.getLayoutParams().height);
                }
                //Log.e("display", "-" + mMetrics.widthPixels);
                //Log.e("display", "-" + mMetrics.heightPixels);
            }
        });
    }

    protected abstract void setListener();

    public void requestLayout() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);
        mDecor.requestLayout();
    }

    protected DisplayMetrics getDisplayMetrics()
    {
        DisplayMetrics metric = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    public int getDefaultMinHeight()
    {
        //return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, (float) (mMetrics.widthPixels * 0.618), getContext().getResources().getDisplayMetrics());
        return (int) (mMetrics.widthPixels < mMetrics.heightPixels ? (mMetrics.widthPixels * 0.618) : (mMetrics.heightPixels * 0.618));
    }

    public int getDefaultMaxHeight()
    {
        //return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, (float) (mMetrics.widthPixels * 1.382), getContext().getResources().getDisplayMetrics());
        return (int) (mMetrics.widthPixels < mMetrics.heightPixels ? (mMetrics.heightPixels * 0.618) : (mMetrics.heightPixels));
    }

    public void setDefaultHeight(boolean defaultHeight)
    {
        this.defaultHeight = defaultHeight;
        if (defaultHeight)
        {
            setDefaultHeight();
        }
    }

    private void setDefaultHeight()
    {
        setMinHeight(getDefaultMinHeight());
        setMaxHeight(getDefaultMaxHeight());
    }

    public boolean isDefaultHeight() {
        return defaultHeight;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setOnDialogInitListener(DialogInitInterface.OnDialogInitListener onDialogInitListener) {
        this.onDialogInitListener = onDialogInitListener;
    }

}
