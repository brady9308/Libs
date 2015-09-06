package silicar.brady.libs.view.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Fragment工具类
 * Created by Work on 2015/8/21.
 * @version 1.0
 * @since 2015/8/21
 * @author 图图
 */
public class FragmentUtil {

    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    //private int fragmentNum;
    //private int stackNum;
    //private SparseArray<Integer> stackArray;

    public FragmentUtil(FragmentManager fragmentManager, int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = containerViewId;
        //this.fragmentNum = -1;
        //this.stackNum = -1;
        //stackArray = new SparseArray<>();
    }

    /**
     * 获取FragmentManager
     * @return
     */
    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    /**
     * 获取FragmentTransaction
     * @return
     */
    public FragmentTransaction getFragmentTransaction()
    {
        return mFragmentManager.beginTransaction();
    }

    /**
     * 获取容器Id
     * @return
     */
    public int getViewId()
    {
        return mContainerViewId;
    }

    /**
     * 设置容器Id
     * @param containerViewId
     */
    public void setViewId(int containerViewId)
    {
        mContainerViewId = containerViewId;
    }

    /**
     * 通过Tag获取Fragment
     * @param tag
     * @return
     */
    public Fragment findFragmentByTag(String tag){
        return mFragmentManager.findFragmentByTag(tag);
    }

    /**
     * 获取栈数量
     * @return
     */
    public int getBackStackEntryCount()
    {
        return mFragmentManager.getBackStackEntryCount();
    }

    /**
     * 添加到栈
     * @param transaction
     */
    public void addToBackStack(FragmentTransaction transaction)
    {
        addToBackStack(transaction,null);
        //addToBackStack(transaction, String.valueOf(stackNum));
    }

    /**
     * 添加到栈
     * @param transaction
     * @param name
     */
    public void addToBackStack(FragmentTransaction transaction, String name)
    {
        //++stackNum;
        //Log.e("number", "" + fragmentNum);
        //stackArray.put(stackNum, fragmentNum);
        if (mFragmentManager.getFragments() != null )
            transaction.addToBackStack(name);
    }

    public void popBackStack()
    {
        mFragmentManager.popBackStack();
    }

//    /**
//     * 弹出栈
//     */
//    public void popBackStackForHide()
//    {
//        popBackStack();
//        --fragmentNum;
//        //Log.e("number", "" + fragmentNum);
//        FragmentTransaction transaction = getFragmentTransaction();
//        for (int i = stackArray.get(stackNum) - 1; i >= 0; i--)
//        {
//            //Log.e("hide", ""+ i);
//            hideFragment(transaction, mFragmentManager.findFragmentByTag(String.valueOf(i)));
//        }
//        transaction.commit();
//        --stackNum;
//    }

    /**
     * 弹出栈
     * @param name
     * @param flags 0不包含自己,1(FragmentManager.POP_BACK_STACK_INCLUSIVE)包含自己
     */
    public void popBackStack(String name, int flags)
    {
        mFragmentManager.popBackStack(name, flags);
    }

    /**
     * 添加Fragment
     * @param transaction
     * @param fragment
     */
    public void addFragment(FragmentTransaction transaction, Fragment fragment)
    {
        addFragment(transaction, mContainerViewId, fragment);
    }

    /**
     * 添加Fragment
     * @param transaction
     * @param containerViewId
     * @param fragment
     */
    public void addFragment(FragmentTransaction transaction, int containerViewId, Fragment fragment)
    {
        addFragment(transaction, containerViewId, fragment, null);
        //transaction.add(containerViewId, fragment);
    }

    /**
     * 添加Fragment
     * @param transaction
     * @param fragment
     * @param tag
     */
    public void addFragment(FragmentTransaction transaction, Fragment fragment, String tag)
    {
        addFragment(transaction, mContainerViewId, fragment, tag);
    }

    /**
     * 添加Fragment
     * @param transaction
     * @param containerViewId
     * @param fragment
     * @param tag
     */
    public void addFragment(FragmentTransaction transaction, int containerViewId, Fragment fragment, String tag)
    {
        transaction.add(containerViewId, fragment, tag);
    }

    /**
     * 替换Fragment
     * @param transaction
     * @param fragment
     */
    public void replaceFragment(FragmentTransaction transaction, Fragment fragment)
    {
        replaceFragment(transaction, mContainerViewId, fragment);
    }

    /**
     * 替换Fragment
     * @param transaction
     * @param containerViewId
     * @param fragment
     */
    public void replaceFragment(FragmentTransaction transaction, int containerViewId, Fragment fragment)
    {
        replaceFragment(transaction, containerViewId, fragment, null);
        //if (mFragmentManager.getFragments() != null)
        //    transaction.replace(containerViewId, fragment);
        //else
        //    transaction.add(containerViewId, fragment);
    }

    /**
     * 替换Fragment
     * @param transaction
     * @param fragment
     * @param tag
     */
    public void replaceFragment(FragmentTransaction transaction, Fragment fragment, String tag)
    {
        replaceFragment(transaction, mContainerViewId, fragment, tag);
    }

    /**
     * 替换Fragment
     * @param transaction
     * @param containerViewId
     * @param fragment
     * @param tag
     */
    public void replaceFragment(FragmentTransaction transaction, int containerViewId, Fragment fragment, String tag)
    {
        if (mFragmentManager.getFragments() != null)
            transaction.replace(containerViewId, fragment, tag);
        else
            transaction.add(containerViewId, fragment, tag);
    }

    /**
     * Fragment是否为空
     * @param fragment
     * @return
     */
    public static boolean isEmptyFragment(Fragment fragment)
    {
        if (fragment == null)
            return true;
        if (fragment.getId() == 0)
            return true;
        return false;
    }

    /**
     * 显示Fragment
     * @param transaction
     * @param fragment
     */
    public static void showFragment(FragmentTransaction transaction, Fragment fragment)
    {
        transaction.show(fragment);
    }

    /**
     * 隐藏Fragment
     * @param transaction
     * @param fragment
     */
    public static void hideFragment(FragmentTransaction transaction, Fragment fragment)
    {
        if(fragment != null)
            transaction.hide(fragment);
    }

    public static boolean isShow(Fragment fragment)
    {
        return (fragment != null && fragment.isVisible());
    }
}
