package com.zxx.lib_common.base;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.SimpleImmersionOwner;
import com.gyf.barlibrary.SimpleImmersionProxy;
import com.zxx.lib_common.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : zdy
 * @date : 2019/3/14 0014.
 * @company: 华梦
 * @description :
 */

public abstract class BaseLazyFragment extends Fragment implements SimpleImmersionOwner {

    public Context mContext;
    protected Activity mActivity;
    private Unbinder mUnbinder;
    private View rootView;
    private boolean mIsFirstVisible = true;/*当前Fragment是否首次可见，默认是首次可见**/
    private boolean isViewCreated = false;/*当前Fragment的View是否已经创建**/
    private boolean currentVisibleState = false;/*当前Fragment的可见状态，一种当前可见，一种当前不可见**/

    /**
     * ImmersionBar代理类
     */
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e("-----> onCreateView");
        if(rootView == null){
            rootView = inflater.inflate(getLayoutId(), container, false);
            mUnbinder = ButterKnife.bind(this, rootView);
            mContext = getActivity();
            init(rootView);
        }
        //在onCreateView执行完毕，将isViewCreated改为true;
        isViewCreated=true;
        return rootView;
    }

    //由子类指定具体类型
    public abstract int getLayoutId();
    public abstract void init(View rootView);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("----->"+isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
        if (isViewCreated) {
            //Fragment可见且状态不是可见(从一个Fragment切换到另外一个Fragment,后一个设置状态为可见)
            if (isVisibleToUser && !currentVisibleState) {
                disPatchFragment(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                //Fragment不可见且状态是可见(从一个Fragment切换到另外一个Fragment,前一个更改状态为不可见)
                disPatchFragment(false);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e("-----> onStart");
        //isHidden()是Fragment是否处于隐藏状态和isVisible()有区别
        //getUserVisibleHint(),Fragement是否可见
        if(!isHidden()&& getUserVisibleHint()){//如果Fragment没有隐藏且可见
            //执行分发的方法,三种结果对应自Fragment的三个回调，对应的操作，Fragment首次加载，可见，不可见
            disPatchFragment(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("-----> onResume");
        if(!mIsFirstVisible){
            //表示点击home键又返回操作,设置可见状态为ture
            if(!isHidden()&& !getUserVisibleHint() && currentVisibleState){
                disPatchFragment(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e("-----> onPause");
        //表示点击home键,原来可见的Fragment要走该方法，更改Fragment的状态为不可见
        if(!isHidden()&& getUserVisibleHint()){
            disPatchFragment(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e("-----> onDestroyView");
        //当 View 被销毁的时候我们需要重新设置 isViewCreated mIsFirstVisible 的状态
        isViewCreated = false;
        mIsFirstVisible = true;

        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    /**
     * @param visible Fragment当前是否可见，然后调用相关方法
     */
    public void disPatchFragment(boolean visible){
        currentVisibleState=visible;
        if(visible){//Fragment可见
            if(mIsFirstVisible){//可见又是第一次
                mIsFirstVisible=false;//改变首次可见的状态
                onFragmentFirst();
            }else{//可见但不是第一次
                onFragmentVisble();
            }
        }else {//不可见
            onFragmentInVisible();
        }
    }

    //Fragemnet首次可见的方法
    public void onFragmentFirst(){
        LogUtils.e("首次可见");
    }
    //Fragemnet可见的方法
    public void onFragmentVisble(){//子Fragment调用次方法，执行可见操作
        LogUtils.e("可见");
    }
    //Fragemnet不可见的方法
    public void onFragmentInVisible(){
        LogUtils.e("不可见");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init();
    }

}
