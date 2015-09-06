package silicar.brady.libs.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast提示工具类
 * Created by Work on 2015/9/1.
 * @version 1.0
 * @since 2015/9/2
 * @author 图图
 */
public class ToastUtil {

    private Context context;
    private Toast toast;
    private boolean isCancel = false;
    private int defGravity = Gravity.CENTER|Gravity.TOP;

    public ToastUtil(Context context) {
        this.context = context;
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        toast.setGravity(defGravity, 0, 200);
    }

    /**
     * 短时间显示Toast
     * @param message
     */
    public void showShort(CharSequence message)
    {
        show(message, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast
     * @param message
     */
    public void showShort(int message)
    {
        show(message, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     * @param message
     */
    public void showLong(CharSequence message)
    {
        show(message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     * @param message
     */
    public void showLong(int message)
    {
        show(message, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     * @param message
     * @param duration
     */
    public void show(CharSequence message, int duration)
    {
        callIsCancel();
        toast.setDuration(duration);
        toast.setText(message);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     * @param message
     * @param duration
     */
    public void show(int message, int duration)
    {
        callIsCancel();
        toast.setDuration(duration);
        toast.setText(message);
        toast.show();
    }

    public boolean isCancel() {
        return isCancel;
    }

    /**
     * 设置是否销毁Toast
     * @param isCancel
     */
    public void setIsCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    /**
     * 设置显示位置
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public void setGravity(int gravity, int xOffset, int yOffset)
    {
        toast.setGravity(gravity, xOffset, yOffset);
    }

    private void callIsCancel()
    {
        if (isCancel)
        {
            toast.cancel();
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
    }
}
