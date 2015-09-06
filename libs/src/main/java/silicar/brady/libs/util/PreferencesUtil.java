package silicar.brady.libs.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences工具类
 * Created by Work on 2015/8/9.
 * @version 1.0
 * @since 2015/8/9
 * @author 图图
 */
public class PreferencesUtil {

    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferencesUtil(Context context, String fileName) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(fileName, this.context.MODE_PRIVATE);
    }

    public Map<String, ?> getAll()
    {
        return sharedPreferences.getAll();
    }

    public boolean getPreferences(String key, boolean defValue)
    {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public int getPreferences(String key, int defValue)
    {
        return sharedPreferences.getInt(key, defValue);
    }

    public long getPreferences(String key, long defValue)
    {
        return sharedPreferences.getLong(key, defValue);
    }

    public float getPreferences(String key, float defValue)
    {
        return sharedPreferences.getFloat(key, defValue);
    }

    public String getPreferences(String key, String defValue)
    {
        return sharedPreferences.getString(key, defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getPreferences(String key, Set<String> defValue)
    {
        return sharedPreferences.getStringSet(key, defValue);
    }

    public void setPreferences(String key, boolean defValue)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, defValue);
        editor.commit();
    }

    public void setPreferences(String key, int defValue)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, defValue);
        editor.commit();
    }

    public void setPreferences(String key, long defValue)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, defValue);
        editor.commit();
    }

    public void setPreferences(String key, float defValue)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, defValue);
        editor.commit();
    }

    public void setPreferences(String key, String defValue)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, defValue);
        editor.commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setPreferences(String key, Set<String> defValue)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, defValue);
        editor.commit();
    }
}
