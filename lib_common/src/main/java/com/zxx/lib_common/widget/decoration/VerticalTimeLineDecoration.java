package com.zxx.lib_common.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zxx.lib_common.utils.DisplayUtil;


/**
 * @author Z.D.Y
 * @date 2018/08/08 下午 13:55
 * @Description 竖向时间轴装饰器
 */

public class VerticalTimeLineDecoration extends RecyclerView.ItemDecoration {
	private ColorDrawable mColorDrawable;
	private int mHeight;
	private Paint mPaint;
	private int mLeftOffset;
	private float mRadius;
	private int mLightColor;
	private int mColor;
	private float mLineW;
	private float mCircleW;

	/**
	 * @param color      颜色
	 * @param dividerH   分割线高度
	 * @param leftOffset 左侧偏移量
	 * @param lightColor 第一个圆圈高亮颜色
	 * @param lineW      时间轴宽度
	 * @param circleW    圆圈宽度
	 * @param radius     圆圈半径
	 */
	public VerticalTimeLineDecoration(int color, int dividerH, int leftOffset, int lightColor, float lineW, float circleW, float radius) {
		this.mColorDrawable = new ColorDrawable(color);
		this.mHeight = dividerH;
		this.mLeftOffset = leftOffset;
		this.mRadius = radius;
		this.mLightColor = lightColor;
		this.mColor = color;
		this.mLineW = lineW;
		this.mCircleW = circleW;

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(mColor);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setStrokeWidth(mLineW);
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		int position = parent.getChildAdapterPosition(view);
//		if (position >= parent.getAdapter().getItemCount() - 1) {
//			return;
//		}
		if (position != 0) {
			outRect.top = mHeight;
		}
		outRect.left = mLeftOffset;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		int count = parent.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = parent.getChildAt(i);
			int position = parent.getChildAdapterPosition(view);
			int dividerTop = view.getTop() - mHeight;
			//第一个item没有水平分割线
			if (position == 0) {
				dividerTop = view.getTop();
			}
			int dividerLeft = parent.getPaddingLeft();
			int dividerBottom = view.getBottom();
			int divierRight = parent.getWidth() - parent.getPaddingRight();
			//圆圈的圆心位置
			int centerX = dividerLeft + mLeftOffset / 2;
			//-14sp是tv的字体大小，3dp是两个tv之间的间隔
			int centerY = dividerTop + (dividerBottom - dividerTop) / 2 - DisplayUtil.sp2px(14) / 2 - DisplayUtil.dip2px(3) / 2;
			//上半部分竖直线
			float uplineTopX = centerX;
			float uplineTopY = dividerTop;
			float uplineBottomX = centerX;
			float uplineBottomY = centerY - mRadius;
			//下半部分竖直线
			float downlineTopX = centerX;
			float downlineTopY = centerY + mRadius;
			float downlineBottomX = centerX;
			float downlineBottomY = dividerBottom;

			if (position != 0) {
				//分割线
				mColorDrawable.setBounds(mLeftOffset, dividerTop, divierRight, dividerTop + mHeight);
				mColorDrawable.draw(c);
				//绘制上部分竖直线
				c.drawLine(uplineTopX, uplineTopY, uplineBottomX, uplineBottomY, mPaint);
			}
			if (position != count - 1) {
				//绘制下部分竖直线
				c.drawLine(downlineTopX, downlineTopY, downlineBottomX, downlineBottomY, mPaint);
			}
			//绘制圆圈
			if (position == 0) {
				//第一个为高亮颜色
				mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
				mPaint.setStrokeWidth(mCircleW);
				mPaint.setColor(mColor);
				c.drawCircle(centerX, centerY, mRadius, mPaint);

				float innerRadius = mRadius - (mCircleW - DisplayUtil.dip2px(0.3F));
				if (innerRadius > 0) {
					mPaint.setColor(mLightColor);
					mPaint.setStyle(Paint.Style.FILL);
					mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
					c.drawCircle(centerX, centerY, innerRadius, mPaint);
				}
			} else {
				mPaint.setStyle(Paint.Style.STROKE);
				mPaint.setStrokeWidth(mCircleW);
				c.drawCircle(centerX, centerY, mRadius, mPaint);
			}
			//恢复
			mPaint.reset();
			mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			mPaint.setColor(mColor);
			mPaint.setStrokeWidth(mLineW);
		}
	}
}
