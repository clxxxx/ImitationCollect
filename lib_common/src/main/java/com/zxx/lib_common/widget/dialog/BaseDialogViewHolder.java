package com.zxx.lib_common.widget.dialog;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class BaseDialogViewHolder {

    private final SparseArray<View> mViews;
    private View mDialogView;

    private BaseDialogViewHolder(Context context, int layoutId) {
        this.mViews = new SparseArray<View>();
        mDialogView = View.inflate(context, layoutId, null);
    }

    public static BaseDialogViewHolder get(Context context, int layoutId) {
        return new BaseDialogViewHolder(context, layoutId);
    }

    public View getConvertView() {
        return mDialogView;
    }

    /**
     * Set the string for TextView
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseDialogViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * Set the string for TextView
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseDialogViewHolder setText(int viewId, @StringRes int text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * set view visible
     *
     * @param viewId
     * @return
     */
    public BaseDialogViewHolder setViewInViSible(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.INVISIBLE);
        return this;
    }

    /**
     * set view visible
     *
     * @param viewId
     * @return
     */
    public BaseDialogViewHolder setViewViSible(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * set view gone
     *
     * @param viewId
     * @return
     */
    public BaseDialogViewHolder setViewGone(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.GONE);
        return this;
    }

    /**
     * set onClick
     *
     * @param viewId
     * @param onClick
     * @return
     */
    public BaseDialogViewHolder setOnClick(int viewId, OnClickListener onClick) {
        View view = getView(viewId);
        view.setOnClickListener(onClick);
        return this;
    }

    /**
     * Through control the Id of the access to control, if not join views
     *
     * @param viewId
     * @return
     */

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mDialogView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
