package com.zxx.lib_common.base;

import android.app.Application;

import com.zxx.lib_common.utils.ConstantUtils;
import com.zxx.lib_common.utils.LogUtils;
import com.zxx.lib_common.utils.PreferenceTool;
import com.zxx.lib_common.utils.ToastUtil;

/**
 * @author : zdy
 * @date : 2019/3/14 0014.
 * @company: 华梦
 * @description :
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件模块中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件模块的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件模块中获取Context的方法必须为:Util.getAPPContext()，不允许其他写法；
 */

public class BaseApplication extends Application{

    private Application mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        //全局Utils
        ConstantUtils.init(this);
        //Log日志
        initLog();
        //Preference参数
        PreferenceTool.init(this);
        //吐司初始化
        ToastUtil.init(this);
    }

    private void initLog() {
        LogUtils.setLogEnable(ConstantUtils.isAppDebug());
    }

}
