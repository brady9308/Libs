package silicar.brady.libs.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 工具类<br>
 * Created by henrybit on 15/3/11.
 * @version 1.0
 * @since 2015/03/11
 */
public class Tools {

    public static class Id
    {
        private static int mId = 0x7F000000;
        public static int createId()
        {
            if(mId == 0) --mId;
            return --mId;
        }
    }

    private final static char[] randomChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','_','.','|','-'};

    /**
     * 判断是否是空串<br>
     * @param str 待判断的字符串
     * @return boolean-true:空串;false:非空.
     */
    public static boolean isEmpty(String str) {
        if(str == null)
            return true;
        if(str.isEmpty())
            return true;
        if(str.trim().isEmpty())
            return true;
        return false;
    }

    /**
     * 判断文件路径是否存在
     * @param str
     * @return
     */
    public static boolean isFileExists(String str)
    {
        return new File(str).exists();
    }

    /**
     * 取出字符串，当遇到空指针时，返回空串.<br>
     * @param str 字符串
     * @return String-返回字符串
     */
    public static String getStringNotNull(String str) {
        if(str == null)
            return "";
        return str;
    }

    /**
     * 整形转为String类型
     * @param a 整形
     * @return String
     */
    public static String intToString(int a) {
        return String.valueOf(a);
    }

    /**
     * String转为Int型
     * @param str
     * @return int
     */
    public static int stringToInt(String str) {
        int a = 0;
        try {
            a = Integer.valueOf(str);
        } catch (Exception e) {
        }
        return a;
    }

    /**
     * 将长整型转换成String类型数据
     * @param a 长整型值
     * @return String
     */
    public static String longToString(long a) {
        return String.valueOf(a);
    }

    public static long stringToLong(String str) {
        long a = 0;
        try {
            a = Long.valueOf(str);
        } catch (Exception e) {
        }
        return a;
    }

    /**
     * 浮点型转换成String类型数据
     * @param a 浮点型
     * @return str
     */
    public static String floatToString(float a) { return String.valueOf(a); }

    /**
     * String类型数据转换成浮点类型
     * @param str
     * @return float
     */
    public static float stringToFloat(String str) {
        float a = 0;
        try {
            a = Float.valueOf(str);
        } catch (Exception e){
        }
        return a;
    }

    /**
     * Double类型转换成String类型
     * @param a
     * @return str
     */
    public static String doubleToString(double a) {
        return String.valueOf(a);
    }

    /**
     * String类型转换成Double类型
     * @param str
     * @return double
     */
    public static double stringToDouble(String str) {
        double a = 0;
        try {
            a = Double.valueOf(str);
        } catch (Exception e) {
        }
        return a;
    }

    /**
     * Double类型转Int类型
     * @param a
     * @return int
     */
    public static int doubleToInt(double a){
        return (int)a;
    }

    public static int longToInt(long a) {
        return (int)a;
    }

    /**
     * 整形转换成String类型（时间）
     * @param a
     * @return
     */
    public static String intToStringTime(int a) {
        if(a < 10)
            return "0"+String.valueOf(a);
        else
            return String.valueOf(a);
    }

    /**
     * 获取HTTP地址上的图片
     * @param url 图片的HTTP地址
     * @return Bitmap
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap = null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 2;   // width，hight设为原来的一半
            bitmap = BitmapFactory.decodeStream(is, null, options);
            //关闭数据流
            is.close();
        }catch(Exception e){
            //e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 从文件流中获取图片
     * @param filePath
     * @return bitMap
     */
    public static Bitmap getFileBitmap(String filePath) {
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            //解析得到图片
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            bitmap = BitmapFactory.decodeStream(fis, null, opt);
            fis.close();
        } catch (Exception e) {
        }
        return bitmap;
    }

    /**
     * 根据经纬度计算两点间的距离(m)
     * @param lat1 纬度1
     * @param lng1 经度1
     * @param lat2 纬度2
     * @param lng2 经度2
     * @return double-距离(单位米)
     */
    public static double calcuChinaDis(double lat1, double lng1, double lat2, double lng2) {
        //地球平均半径R=6371.004千米
        int EARTH_R = 6371004;
        double c = Math.sin(lat2)*Math.sin(lat1)*Math.cos(lng2-lng1) + Math.cos(lat2)*Math.cos(lat1);
        double distance = EARTH_R*Math.acos(c)*Math.PI/180d;
        return distance;
    }

    /**
     * 帮用户打开GPS定位
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings","com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            //Log.e("Tools",e.getMessage());
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOpenGPS(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 解析Base64编码数据<br>
     * @param decodeContent 待解析内容
     * @return String-解析后的结果数据
     */
    public static String decodeBase64(String decodeContent) {
        String decodeStr = "";
        try {
            byte[] decodeBytes = Base64.decode(decodeContent, Base64.URL_SAFE | Base64.NO_WRAP);
            decodeStr = new String(decodeBytes);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return decodeStr;
    }

    /**
     * 解析Base64编码数据<br>
     * @param decodeContent 待解析内容
     * @param coder 字符串编码格式
     * @return String-解析后的结果数据
     */
    public static String decodeBase64(String decodeContent, String coder) {
        String decodeStr = "";
        try {
            byte[] decodeBytes = Base64.decode(decodeContent, Base64.URL_SAFE | Base64.NO_WRAP);
            decodeStr = new String(decodeBytes, coder);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return decodeStr;
    }

    /**
     * Base64编码数据<br>
     * @param encodeContent 待编码内容
     * @return String-编码后的结果数据
     */
    public static String encodeBase64(String encodeContent) {
        String encodeStr = "";
        try {
            byte[] encodeBytes = Base64.encode(encodeContent.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
            encodeStr = new String(encodeBytes, 0, encodeBytes.length);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 创建一个全局唯一的用户ID
     * @return String
     */
    public static String createUUid() {
        StringBuilder uuid = new StringBuilder();
        long times = System.currentTimeMillis();
        uuid.append(times);
        for(int i=0; i<10; i++) {
            Random random = new Random();
            int position = Math.abs(random.nextInt()) % randomChars.length;
            uuid.append(randomChars[position]);
        }
        return uuid.toString();
    }

    /**
     * 随机一个字符串
     * @return String
     */
    public static String randomWords() {
        StringBuilder words = new StringBuilder();
        for(int i=0; i<10; i++) {
            Random random = new Random();
            int position = Math.abs(random.nextInt()) % randomChars.length;
            words.append(randomChars[position]);
        }
        return words.toString();
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * @param context
     * @param pxValue
     * @return
     */
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值
     * @param context
     * @param dipValue
     * @return
     */
    public static float dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值
     * @param context
     * @param pxValue
     * @return
     */
    public static float px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     * @param context
     * @param spValue
     * @return
     */
    public static float sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }

    public static String getTimeString(long value) {
        if(value < 10)
            return "0"+value;
        return ""+value;
    }

    public static double getJsonValueDouble(JSONObject json, String key) {
        try {
            return json.getDouble(key);
        } catch (Exception e) {
        }
        return 0;
    }

    public static int getJsonValueInt(JSONObject json, String key) {
        try {
            return json.getInt(key);
        } catch (Exception e) {
        }
        return 0;
    }

    public static long getJsonValueLong(JSONObject json, String key) {
        try {
            return json.getLong(key);
        } catch (Exception e) {
        }
        return 0;
    }

    public static String getJsonValueString(JSONObject json, String key) {
        try {
            return json.getString(key);
        } catch (Exception e) {
        }
        return "";
    }

    public static JSONObject getJsonValueObject(JSONObject json, String key) {
        try {
            return json.getJSONObject(key);
        } catch (Exception e) {
        }
        return null;
    }

    public static JSONArray getJsonValueArray(JSONObject json, String key) {
        try {
            return json.getJSONArray(key);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 将String类型的JSONArray转换成List
     * @param array
     * @return List
     */
    public static List<String> arrayToList(JSONArray array) {
        List<String> list = new ArrayList<String>();
        try {
            for(int i=0; array!=null&&i<array.length(); i++) {
                String str = array.getString(i);
                if(!Tools.isEmpty(str))
                    list.add(str);
            }
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * 将map数据转换成json格式的字符串
     * @param
     * @return
     */
//    public static String getJson(Map map) {
//        Gson gson = new Gson();
//        return gson.toJson(map);
//    }

    public static void main(String[] args) {
        System.out.println(createUUid());
    }

    public static Activity getActivity(View view)
    {
        return  (Activity)view.getContext();
    }

    public static Activity getActivity(Context context)
    {
        return (Activity)context;
    }

    /**
     * 动态测量listview-Item的高度
     * @param listView
     */
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        View listItem = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            if (i == 0)
                listItem = listAdapter.getView(i, null, listView);
            else
                listItem = listAdapter.getView(i, listItem, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
