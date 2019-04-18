package com.zxx.lib_common.widget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import static android.widget.LinearLayout.VERTICAL;

public class SpaceDecoration extends RecyclerView.ItemDecoration {

	private int space;
	private int headerCount = -1;
	private int footerCount = Integer.MAX_VALUE;
	//是否开启左右边距
	private boolean mPaddingEdgeSide = true;
	//是否开启间距
	private boolean mPaddingStart = true;
	//是否开启header和footer间距
	private boolean mPaddingHeaderFooter = false;


	public SpaceDecoration(int space) {
		this.space = space;
	}

	/**
	 * 设置开启左右内边距,默认开启
	 *
	 * @param mPaddingEdgeSide
	 */
	public void setPaddingEdgeSide(boolean mPaddingEdgeSide) {
		this.mPaddingEdgeSide = mPaddingEdgeSide;
	}

	/**
	 * 设置开启间距,默认开启
	 *
	 * @param mPaddingStart
	 */
	public void setPaddingStart(boolean mPaddingStart) {
		this.mPaddingStart = mPaddingStart;
	}

	/**
	 * 设置开启头尾部间距，默认关闭
	 *
	 * @param mPaddingHeaderFooter
	 */
	public void setPaddingHeaderFooter(boolean mPaddingHeaderFooter) {
		this.mPaddingHeaderFooter = mPaddingHeaderFooter;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		int position = parent.getChildAdapterPosition(view);
		int spanCount = 0;
		int orientation = 0;
		int spanIndex = 0;
		int headerCount = 0, footerCount = 0;
		//首行或者首列的item数
		int firstCount = 0;
		//每行的item总数
		int positionForCount = 0;
//		if (parent.getAdapter() instanceof BaseRecyclerAdapter) {
//			headerCount = ((BaseRecyclerAdapter) parent.getAdapter()).getHeaderCount();
//			footerCount = ((BaseRecyclerAdapter) parent.getAdapter()).getFooterCount();
//		}

		RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
		if (layoutManager instanceof StaggeredGridLayoutManager) {
			orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
			spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
			spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
			firstCount = spanCount;
			positionForCount = spanCount;
		} else if (layoutManager instanceof GridLayoutManager) {
			orientation = ((GridLayoutManager) layoutManager).getOrientation();
			spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
			spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
			//首行item数
			firstCount = spanCount - ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanSize(0) + 1;
			positionForCount = spanCount - ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanSize(position) + 1;
		} else if (layoutManager instanceof LinearLayoutManager) {
			orientation = ((LinearLayoutManager) layoutManager).getOrientation();
			spanCount = 1;
			spanIndex = 0;
			firstCount = spanCount;
			positionForCount = spanCount;
		}

		/**
		 * 普通Item的尺寸
		 */
		if ((position >= headerCount && position < parent.getAdapter().getItemCount() - footerCount)) {

			if (orientation == VERTICAL) {
				//期望宽度
				float expectedWidth = (float) (parent.getWidth() - space * (positionForCount + (mPaddingEdgeSide ? 1 : -1))) / positionForCount;
				//原始宽度
				float originWidth = (float) parent.getWidth() / positionForCount;
				//预计X位置
				float expectedX = (mPaddingEdgeSide ? space : 0) + (expectedWidth + space) * spanIndex;
				float originX = originWidth * spanIndex;
//                System.out.println("span 参数expectedWidth："+expectedWidth+"/originWidth:"+originWidth+"/expectedX:"+expectedX+"/originX:"+originX+"/spanIndex:"+spanIndex);
				outRect.left = (int) (expectedX - originX);

				outRect.right = (int) (originWidth - outRect.left - expectedWidth);
				//首行添加top边距
				if (position - headerCount < firstCount && mPaddingStart) {
					outRect.top = space;
				}
				outRect.bottom = space;
//                System.out.println(firstCount+"偏移量："+outRect.left+"/"+outRect.top+"/"+outRect.right+"/"+outRect.bottom);
			} else {
				float expectedHeight = (float) (parent.getHeight() - space * (positionForCount + (mPaddingEdgeSide ? 1 : -1))) / positionForCount;
				float originHeight = (float) parent.getHeight() / positionForCount;
				float expectedY = (mPaddingEdgeSide ? space : 0) + (expectedHeight + space) * spanIndex;
				float originY = originHeight * spanIndex;
				outRect.bottom = (int) (expectedY - originY);
				outRect.top = (int) (originHeight - outRect.bottom - expectedHeight);
				if (position - headerCount < firstCount && mPaddingStart) {
					outRect.left = space;
				}
				outRect.right = space;
			}
		} else if (mPaddingHeaderFooter) {
			if (orientation == VERTICAL) {
				outRect.right = outRect.left = mPaddingEdgeSide ? space : 0;
				outRect.top = mPaddingStart ? space : 0;
			} else {
				outRect.top = outRect.bottom = mPaddingEdgeSide ? space : 0;
				outRect.left = mPaddingStart ? space : 0;
			}
		}

	}

}