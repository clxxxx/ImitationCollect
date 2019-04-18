package com.zxx.lib_common.base;

/**
 * @author : zdy
 * @date : 2019/3/14 0014.
 * @company: 华梦
 * @description :
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionOwner;
import com.gyf.barlibrary.ImmersionProxy;

/**
 * https://juejin.im/post/5adcb0e36fb9a07aa7673fbc
 * BaseLazyFragment 单fragment懒加载
 *
 *  * 生命周期执行的方法 如下：
 * 第一次生成页面-->可见
 * setUserVisibleHint: ----->false
 * setUserVisibleHint: ----->true
 * onCreateView: -----> onCreateView
 * onStart: -----> onStart
 * onFragmentFirst: 首次可见
 * onFragmentFirst: -----> 子fragment进行初始化操作
 * onResume: -----> onResume
 *
 * 可见-->第一次隐藏：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 *
 * 未销毁且不可见-->重新可见：
 * onStart: -----> onStart
 * onFragmentVisble: 可见
 * onFragmentVisble: -----> 子fragment每次可见时的操作
 * onResume: -----> onResume
 *
 * 可见-->销毁：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 * onDestroyView: -----> onDestroyView
 *
 * 我们可以更具以上生命周期来操作不同的业务逻辑，
 * 请务必运行此module demo，观看打印日志来自定义逻辑。
 */

public abstract class BaseFragment extends Fragment implements ImmersionOwner {

    public Context mContext;

    /**
     * ImmersionBar代理类
     */
    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mContext = getActivity();
        init();
        return view;
    }

    //由子类指定具体类型
    public abstract int getLayoutId();
    public abstract void init();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
