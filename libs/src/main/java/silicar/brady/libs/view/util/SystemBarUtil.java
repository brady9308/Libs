package silicar.brady.libs.view.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import silicar.brady.libs.view.custom.SystemBarTintManager;

/**
 * 自定义状态栏和导航栏样式
 * 版本兼容配置配置
 * 未配置Styles,必须先addFlags
 * Created by Brady on 2015/9/16.
 */
public class SystemBarUtil {
    private Activity activity;
    public SystemBarTintManager systemBarTintManager;
    private ViewGroup mDecorViewGroup;
    private View mContentView;

    public SystemBarUtil(Activity activity) {
        this.activity = activity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            systemBarTintManager = new SystemBarTintManager(activity);
        }
    }

    /**
     * 必须先设置setContentView
     * @return
     */
    public ViewGroup getDecorViewGroup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            mDecorViewGroup = systemBarTintManager.getDecorViewGroup();
        }
        else
        {
            mDecorViewGroup = (ViewGroup) mContentView;
        }
        return mDecorViewGroup;
    }

    public View getContentView() {
        return mContentView;
    }

    public View setContentView(View contentView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            mContentView = systemBarTintManager.setContentView(contentView);
        }
        else
        {
            mContentView = contentView;
        }
        return mContentView;
    }

    public View setContentView(int layoutId)
    {
        return setContentView(layoutId, null);
    }

    public View setContentView( int layoutId, ViewGroup root) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            mContentView = systemBarTintManager.setContentView(layoutId, root);
        }
        else
        {
            mContentView = activity.getLayoutInflater().inflate(layoutId, root);
        }
        return mContentView;
    }

    public void setFitsSystemWindows(boolean fitSystemWindows)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            systemBarTintManager.setFitsSystemWindows(fitSystemWindows);
    }

    public void setStatusBarTintEnabled(boolean enabled)
    {
        if (systemBarTintManager != null)
            systemBarTintManager.setStatusBarTintEnabled(enabled);
    }

    public void setNavigationBarTintEnabled(boolean enabled)
    {
        if (systemBarTintManager != null)
            systemBarTintManager.setNavigationBarTintEnabled(enabled);
    }

    public void setTintColor(int color)
    {
        if (systemBarTintManager != null)
            systemBarTintManager.setTintColor(color);
    }

    /**
     * 设置状态栏透明
     */
    public static void setTranslucentStatusBar(Activity activity)
    {
        addFlags(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 设置导航栏透明
     */
    public static void setTranslucentNavigationBar(Activity activity)
    {
        addFlags(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public static void addFlags(Activity activity, int flags)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            activity.getWindow().addFlags(flags);
            //WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            //localLayoutParams.flags = (flags | localLayoutParams.flags);
        }
    }
}
