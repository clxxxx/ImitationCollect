package com.zxx.lib_common.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zxx.lib_common.R;
import com.zxx.lib_common.utils.ConstantUtils;


/**
 * @author : zdy
 * @date : 2018/4/23 0023.
 * @company: 华梦
 * @description :
 */

public abstract class BaseDialog {

    private Context context;
    private Dialog mDialog;
    private Window mDialogWindow;
    private BaseDialogViewHolder dilaogVh;
    private View mRootView;
    private boolean isBack = false;

    public BaseDialog(Context context, int layoutId) {
        dilaogVh = BaseDialogViewHolder.get(context, layoutId);
        this.context = context;
        mRootView = dilaogVh.getConvertView();
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(mRootView);
        mDialogWindow = mDialog.getWindow();
        convert(dilaogVh);
    }


    /**
     * 把弹出框view holder传出去
     */
    public abstract void convert(BaseDialogViewHolder holder);

    public static AlertDialog.Builder creatNormalDialogBuilder(Context context, String title, String message) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
    }

    /**
     * 显示dialog
     */
    public BaseDialog showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
        return this;
    }


    /**
     * @param light 弹出时背景亮度 值为0.0~1.0    1.0表示全黑  0.0表示全白
     * @return
     */
    public BaseDialog backgroundLight(double light) {
        if (light < 0.0 || light > 1.0) {
            return this;
        }
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        lp.dimAmount = (float) light;
        mDialogWindow.setAttributes(lp);
        return this;
    }


    /**
     * @param style 显示一个Dialog自定义一个弹出方式  具体怎么写 可以模仿上面的
     * @return
     */
    public BaseDialog showDialog(@StyleRes int style) {
        mDialogWindow.setWindowAnimations(style);
        mDialog.show();
        return this;
    }

    /**
     * @param isAnimation 如果为true 就显示默认的一个缩放动画
     * @return
     */
    public BaseDialog showDialog(boolean isAnimation) {
        mDialog.show();
        return this;
    }

    /**
     * 设置从底部显示
     */
    public BaseDialog setGravity(int g) {
        mDialogWindow.setGravity(g);
        return this;
    }

    /**
     * 全屏显示
     */
    public BaseDialog fullScreen() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }


    public BaseDialog setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        mDialog.setOnKeyListener(onKeyListener);
        return this;
    }

    /**
     * 全屏宽度
     */
    public BaseDialog fullWidth() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * 全屏百分之80宽度
     */
    public BaseDialog full80Width() {
        DisplayMetrics dm = ConstantUtils.getAPPContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        lp.width = (int) (dm.widthPixels * 0.8);
        mDialog.getWindow().setAttributes(lp);
        return this;
    }

    /**
     * 设置最大高度
     */
    public BaseDialog setHeight(float wf) {
        DisplayMetrics dm = ConstantUtils.getAPPContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        if (wf == 0) {
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            lp.height = (int) (dm.heightPixels * wf);
        }
        mDialog.getWindow().setAttributes(lp);
        return this;
    }

    /**
     * 全屏百分之80宽度
     */
    public BaseDialog setWidth(float wf) {
        DisplayMetrics dm = ConstantUtils.getAPPContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        lp.width = (int) (dm.widthPixels * wf);
        mDialog.getWindow().setAttributes(lp);
        return this;
    }

    /**
     * 全屏高度
     */
    public BaseDialog fullHeight() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * @param width  自定义的宽度
     * @param height 自定义的高度
     * @return
     */
    public BaseDialog setWidthAndHeight(int width, int height) {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = width;
        wl.height = height;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * cancel dialog
     */
    public void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            dismiss();
        }
    }

    /**
     * cancel dialog
     */
    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 设置监听
     */
    public BaseDialog setDialogDismissListener(DialogInterface.OnDismissListener listener) {
        mDialog.setOnDismissListener(listener);
        return this;
    }

    /**
     * 设置监听
     */
    public BaseDialog setOnCancelListener(DialogInterface.OnCancelListener listener) {
        mDialog.setOnCancelListener(listener);
        return this;
    }

    /**
     * 设置是否能取消
     */
    public BaseDialog setCancelAble(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    /**
     * 设置触摸其他地方是否能取消
     */
    public BaseDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public BaseDialog setDialogIsBack(boolean isBack) {
        this.isBack = isBack;
        mDialog.setOnKeyListener(keylistener);
        return this;
    }

    /**
     * 获取根视图
     */
    public View getRootView() {
        return mRootView;
    }

    public View getViewById(int viewId) {
        return dilaogVh.getView(viewId);
    }


    private DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return isBack;
            } else {
                return false;
            }
        }
    };


}
