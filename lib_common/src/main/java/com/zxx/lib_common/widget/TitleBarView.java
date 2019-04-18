package com.zxx.lib_common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxx.lib_common.R;

/**
 * Created by Administrator on 2016-11-14.
 */

public class TitleBarView extends LinearLayout implements View.OnClickListener {

	protected RelativeLayout leftLayout;
	protected ImageView leftImage;
	protected RelativeLayout rightLayout;
	//	protected RelativeLayout rightLayout2;
	protected ImageView rightImage;
	//	protected ImageView rightImage2;
	protected TextView titleView;
	protected TextView rightText;
	//	protected TextView rightText2;
	protected LinearLayout titleLayout;
	protected OnLeftClickListener leftClick;

	public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public TitleBarView(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.view_titlebar, this);
		leftLayout = findViewById(R.id.left_layout);
		leftImage = findViewById(R.id.titlebar_left_img);
		rightLayout = findViewById(R.id.right_layout);
		rightImage = findViewById(R.id.titlebar_right_img);
//		rightLayout2 = (RelativeLayout) findViewById(R.id.right_layout2);
//		rightImage2 = (ImageView) findViewById(R.id.titlebar_right_img2);
		titleView = findViewById(R.id.titlebar_title);
		rightText = findViewById(R.id.titlebar_right_text);
//		rightText2 = (TextView) findViewById(R.id.titlebar_right_text2);
		titleLayout = findViewById(R.id.root);

		parseStyle(context, attrs);

		leftLayout.setOnClickListener(this);
	}

	private void parseStyle(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);
			String title = ta.getString(R.styleable.TitleBarView_titleBarTitle);
			titleView.setText(title);
			int titleColor = ta.getColor(R.styleable.TitleBarView_titleTvColor, ContextCompat.getColor(getContext(),R.color.white));
			titleView.setTextColor(titleColor);
			int rightColor = ta.getColor(R.styleable.TitleBarView_rightTvColor, ContextCompat.getColor(getContext(),R.color.white));
			rightText.setTextColor(rightColor);

			boolean leftVisiable = ta.getBoolean(R.styleable.TitleBarView_leftVisiable, true);
			boolean rightVisiable = ta.getBoolean(R.styleable.TitleBarView_rightVisiable, false);

			leftLayout.setVisibility(leftVisiable ? VISIBLE : GONE);
			rightLayout.setVisibility(rightVisiable ? VISIBLE : GONE);
			Drawable leftDrawable = ta.getDrawable(R.styleable.TitleBarView_titleBarLeftImage);
			if (null != leftDrawable) {
				leftImage.setImageDrawable(leftDrawable);
			}
			Drawable rightDrawable = ta.getDrawable(R.styleable.TitleBarView_titleBarRightImage);
			if (null != rightDrawable) {
				rightImage.setImageDrawable(rightDrawable);
			}

			String righttext = ta.getString(R.styleable.TitleBarView_titleBarRightText);
			rightText.setText(righttext);

			int bgColor = ta.getColor(R.styleable.TitleBarView_titleBarBackground, ContextCompat.getColor(context, R.color.app_color));
			titleLayout.setBackgroundColor(bgColor);
			setBackgroundColor(bgColor);
//			Drawable background = ta.getDrawable(R.styleable.TitleBarView_titleBarBackground);
//			if (null != background) {
//				titleLayout.setBackgroundDrawable(background);
//			}

			ta.recycle();
		}
	}

	public TextView getRightText() {
		return rightText;
	}

	public void setRightText(TextView rightText) {
		this.rightText = rightText;
	}

	public void setRightText(String righttext) {
		rightText.setText(righttext);
	}

	public void setRightTextSize(int size) {
		this.rightText.setTextSize(size);
	}

//	public TextView getRightText2() {
//		return rightText2;
//	}

//	public void setRightText2(String righttext) {
//		rightText2.setText(righttext);
//	}

//	public void setRightText2(TextView rightText) {
//		this.rightText2 = rightText;
//	}

	public void setLeftImageResource(int resId) {
		leftImage.setImageResource(resId);
	}

	public void setRightImageResource(int resId) {
		rightImage.setImageResource(resId);
	}

//	public void setRightImage2Resource(int resId) {
//		rightImage2.setImageResource(resId);
//	}

	public void setOnLeftClickListener(OnLeftClickListener leftClick) {
		this.leftClick = leftClick;
	}

	public void setOnRightClickListener(OnClickListener listener) {
		rightLayout.setOnClickListener(listener);
	}

	public void setLeftLayoutVisibility(int visibility) {
		leftLayout.setVisibility(visibility);
	}

	public void setRightLayoutVisibility(int visibility) {
		rightLayout.setVisibility(visibility);
	}

//	public void setRightLayout2Visibility(int visibility) {
//		rightLayout2.setVisibility(visibility);
//	}

	public void setRightTextVisibility(int visibility) {
		rightText.setVisibility(visibility);
	}

	public void setRightImageVisibility(int visibility) {
		rightImage.setVisibility(visibility);
	}

	public void setTextColor(int color) {
		titleView.setTextColor(color);
	}

	public CharSequence getTitle() {
		return titleView.getText();
	}

	public void setTitle(String title) {
		titleView.setText(title);
	}

//	@Override
//	public void setBackgroundColor(int color) {
//		titleLayout.setBackgroundColor(color);
//	}

	public RelativeLayout getLeftLayout() {
		return leftLayout;
	}

	public RelativeLayout getRightLayout() {
		return rightLayout;
	}

//	public RelativeLayout getRightLayout2() {
//		return rightLayout2;
//	}

	public ImageView getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(ImageView leftImage) {
		this.leftImage = leftImage;
	}

	public ImageView getRightImage() {
		return rightImage;
	}

	public void setRightImage(ImageView rightImage) {
		this.rightImage = rightImage;
	}

	@Override
	public void onClick(View v) {
		if (v == leftLayout) {
			if (null != leftClick) {
				leftClick.onLeftClickListener();
			} else {
				if (getContext() instanceof Activity) {
					((Activity) getContext()).finish();
				}
			}
		}
	}

	public interface OnLeftClickListener {
		void onLeftClickListener();
	}
}
