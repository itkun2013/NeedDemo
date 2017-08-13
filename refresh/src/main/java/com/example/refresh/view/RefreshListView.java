package com.example.refresh.view;

import java.text.SimpleDateFormat;
import java.util.Date;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.refresh.R;

/**
 * @项目名: Zhbj15
 * @包名: org.itheima15.zhbj.view
 * @类名: RefreshListView
 * @作者: 肖琦
 * @创建时间: 2015-11-18 上午8:37:07
 * @描述: TODO:
 * 
 * @更新时间: $Date: 2015-11-18 14:57:03 +0800 (Wed, 18 Nov 2015) $
 * @更新人: $Author: xq $
 * @版本: $Rev: 45 $
 * @更新内容: TODO:
 */
public class RefreshListView extends ListView implements OnScrollListener
{

	private static final String	TAG						= "RefreshListView";

	private static final int	STATE_PULL_DOWN			= 0;					// 下拉刷新状态
	private static final int	STATE_RELEASE_REFRESH	= 1;					// 释放刷新状态
	private static final int	STATE_REFRESHING		= 2;					// 正在刷新状态

	private float				mDownY;
	private int					mRefreshHeaderHeight;
	private View				mRefreshHeader;

	private int					mCurrentState			= STATE_PULL_DOWN;		// 用来记录当前刷新的状态

	private ProgressBar			mPbLoading;
	private ImageView			mIvArrow;
	private TextView			mTvState;
	private TextView			mTvTime;

	private RotateAnimation		mDown2upAnim;
	private RotateAnimation		mUp2DownAnim;

	private View				mFirstHeaderView;								// 用来记录第一个的view

	private int					mExtraY					= 0;

	private OnItemClickListener	mItemListener;									// 保存用户设置的item点击监听
	private boolean				isInterceptItemClick	= false;

	private Drawable			mItemSelector;

	private OnRefreshListener	mListener;

	private View				mFooterView;

	private int					mFooterViewHight;

	private boolean				isLoadMore				= false;				// 用来记录是否是加载更多的

	public RefreshListView(Context context) {
		this(context, null);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 添加自定义的刷新头
		initRefreshHeaderLayout();

		// 添加自定义的刷新底部
		initRefreshFooterLayout();

		// 初始化动画
		initAnimation();
	}

	private void initAnimation()
	{
		mDown2upAnim = new RotateAnimation(0, 180,
											Animation.RELATIVE_TO_SELF, 0.5f,
											Animation.RELATIVE_TO_SELF, 0.5f);
		mDown2upAnim.setDuration(400);
		mDown2upAnim.setFillAfter(true);// 动画完成时保持结束后的样式

		mUp2DownAnim = new RotateAnimation(-180, 0,
											Animation.RELATIVE_TO_SELF, 0.5f,
											Animation.RELATIVE_TO_SELF, 0.5f);
		mUp2DownAnim.setDuration(400);
		mUp2DownAnim.setFillAfter(true);// 动画完成时保持结束后的样式
	}

	private void initRefreshHeaderLayout()
	{
		mRefreshHeader = View.inflate(getContext(), R.layout.refresh_header, null);

		mPbLoading = (ProgressBar) mRefreshHeader.findViewById(R.id.refresh_pb_loading);
		mIvArrow = (ImageView) mRefreshHeader.findViewById(R.id.refresh_iv_arrow);
		mTvState = (TextView) mRefreshHeader.findViewById(R.id.refresh_tv_state);
		mTvTime = (TextView) mRefreshHeader.findViewById(R.id.refresh_tv_time);

		// 添加刷新头
		addHeaderView(mRefreshHeader);

		// 隐藏头
		// 没有走view的绘制流程中onMeasure()
		// 手动测量
		mRefreshHeader.measure(0, 0);
		mRefreshHeaderHeight = mRefreshHeader.getMeasuredHeight();
		Log.d(TAG, "refreshHeaderHeight : " + mRefreshHeaderHeight);
		int top = -mRefreshHeaderHeight;
		mRefreshHeader.setPadding(0, top, 0, 0);
	}

	private void initRefreshFooterLayout()
	{
		mFooterView = View.inflate(getContext(), R.layout.refresh_footer, null);

		// 添加底部
		addFooterView(mFooterView);

		// 隐藏footer
		mFooterView.measure(0, 0);
		mFooterViewHight = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterViewHight, 0, 0);

		// 设置listView滑动的监听
		setOnScrollListener(this);
	}

	@Override
	public void addHeaderView(View v)
	{
		if (v != mRefreshHeader && mFirstHeaderView == null)
		{
			mFirstHeaderView = v;
		}
		super.addHeaderView(v);
	}

	@Override
	public void addHeaderView(View v, Object data, boolean isSelectable)
	{
		if (v != mRefreshHeader && mFirstHeaderView == null)
		{
			mFirstHeaderView = v;
		}
		super.addHeaderView(v, data, isSelectable);
	}

	@Override
	public void setSelector(Drawable sel)
	{
		this.mItemSelector = sel;
		super.setSelector(sel);
	}

	private int getFirstLoactionY()
	{
		if (mFirstHeaderView != null)
		{
			int[] fLoc = new int[2];
			mFirstHeaderView.getLocationOnScreen(fLoc);

			return fLoc[1] - getDividerHeight();
		}
		else
		{
			// 没有
			int count = getChildCount();
			if (count > 0)
			{
				View itemView = getChildAt(0);

				int[] fLoc = new int[2];
				itemView.getLocationOnScreen(fLoc);

				return fLoc[1] - getDividerHeight();
			}
			else
			{
				int[] lsLoc = new int[2];
				getLocationOnScreen(lsLoc);
				return lsLoc[1];
			}
		}
	}

	@Override
	public void setOnItemClickListener(OnItemClickListener listener)
	{
		this.mItemListener = listener;
		super.setOnItemClickListener(listener);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		switch (ev.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				mDownY = ev.getY();

				break;
			case MotionEvent.ACTION_MOVE:
				float moveY = ev.getY();

				// 如果当前处于正在刷新的状态,不可以拖动
				if (mCurrentState == STATE_REFRESHING)
				{
					break;
				}

				// 获得当前listView左上角的坐标
				int[] lsLoc = new int[2];
				getLocationOnScreen(lsLoc);
				// Log.d(TAG, "listView : " + lsLoc[0] + "  " + lsLoc[1]);

				// // 获得第一个view(除去刷新的头)的左上角的坐标
				// int[] fLoc = new int[2];
				// mFirstHeaderView.getLocationOnScreen(fLoc);
				// // Log.d(TAG, "firstView : " + fLoc[0] + "  " + fLoc[1]);

				int firstLoactionY = getFirstLoactionY();

				Log.d(TAG, firstLoactionY + " : " + lsLoc[1]);

				if (firstLoactionY < lsLoc[1] && mExtraY == 0)
				{
					// 如果露一半时，计算出会多加的距离
					Log.d(TAG, "露一半");
					mExtraY = lsLoc[1] - firstLoactionY;

				}
				else if (firstLoactionY == lsLoc[1])
				{
					// 如果露一半的时，如果触摸item条目时，不会触发itemClick事件
					// 全部显示时 ，会触发itemClick事件

					Log.d(TAG, "全部露出时");

					isInterceptItemClick = true;

					// 消除掉用户设置的item点击事件
					super.setOnItemClickListener(null);
					super.setSelector(new ColorDrawable(Color.TRANSPARENT));
				}

				float diffY = moveY - mDownY;
				// 从上往下,并且第一个item可见时
				int firstVisiblePosition = getFirstVisiblePosition();
				if (moveY > mDownY && firstVisiblePosition == 0)
				{
					// 从上往下拖动时需要看到刷新的头
					int paddingTop = (int) (diffY - mRefreshHeaderHeight - mExtraY + 0.5f);

					// 设置刷新头的top
					mRefreshHeader.setPadding(0, paddingTop, 0, 0);

					if (paddingTop >= 0 && mCurrentState != STATE_RELEASE_REFRESH)
					{
						Log.d(TAG, "state : 释放刷新");
						// 当刷新头全部显示时候是释放刷新
						mCurrentState = STATE_RELEASE_REFRESH;

						// UI改变
						refreshUI();
					}
					else if (paddingTop < 0 && mCurrentState != STATE_PULL_DOWN)
					{
						Log.d(TAG, "state : 下拉刷新");
						// 当刷新头没有全部显示时候是下拉刷新
						mCurrentState = STATE_PULL_DOWN;

						// UI改变
						refreshUI();
					}

					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:

				// 重置数据
				mExtraY = 0;
				isInterceptItemClick = false;

				// 如果是下拉刷新状态时松开
				if (mCurrentState == STATE_PULL_DOWN)
				{
					Log.d(TAG, "下拉刷新时松开");

					// 看不到下拉刷新的头,刷新状态STATE_PULL_DOWN
					// mRefreshHeader.setPadding(0, -mRefreshHeaderHeight, 0,
					// 0);
					int start = mRefreshHeader.getPaddingTop();//
					int end = -mRefreshHeaderHeight;
					doHeaderAnimation(start, end, false);
				}
				else if (mCurrentState == STATE_RELEASE_REFRESH)
				{
					Log.d(TAG, "释放刷新时松开");
					// 如果是释放刷新时松开，状态改变为STATE_REFRESHING,全部显示刷新的头
					mCurrentState = STATE_REFRESHING;
					// 刷新ui
					refreshUI();

					// mRefreshHeader.setPadding(0, 0, 0, 0);

					int start = mRefreshHeader.getPaddingTop();//
					int end = 0;
					doHeaderAnimation(start, end, true);
				}
				break;
			default:
				break;
		}

		// super.onTouchEvent()---> AbsListView
		boolean flag = super.onTouchEvent(ev);

		if (!isInterceptItemClick)
		{
			super.setOnItemClickListener(mItemListener);
			super.setSelector(mItemSelector);
		}

		return flag;
	}

	private void doHeaderAnimation(int start, int end, final boolean callback)
	{
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator)
			{
				int value = (Integer) animator.getAnimatedValue();
				mRefreshHeader.setPadding(0, value, 0, 0);
			}
		});
		animator.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator arg0)
			{
				if (callback)
				{
					// 通知接口回调
					if (mListener != null)
					{
						mListener.onRefreshing();
					}
				}
			}

			@Override
			public void onAnimationCancel(Animator arg0)
			{
				// TODO Auto-generated method stub
			}
		});
		long duration = Math.abs(end - start) * 10;
		if (duration > 400)
		{
			duration = 400;
		}
		animator.setDuration(duration);
		animator.start();
	}

	private void refreshUI()
	{
		switch (mCurrentState)
		{
			case STATE_PULL_DOWN:
				// 下拉刷新状态

				// 状态文本改变
				mTvState.setText("下拉刷新");

				// 箭头要做动画:箭头由下向上旋转
				mPbLoading.setVisibility(View.INVISIBLE);
				mIvArrow.setVisibility(View.VISIBLE);
				mIvArrow.startAnimation(mUp2DownAnim);

				break;
			case STATE_RELEASE_REFRESH:
				// 释放刷新状态

				// 状态文本改变
				mTvState.setText("释放刷新");

				// 箭头要做动画:箭头由下向上旋转
				mPbLoading.setVisibility(View.INVISIBLE);
				mIvArrow.setVisibility(View.VISIBLE);
				mIvArrow.startAnimation(mDown2upAnim);

				break;
			case STATE_REFRESHING:
				// 正在刷新状态
				mIvArrow.clearAnimation();

				// 状态文本改变
				mTvState.setText("正在刷新");

				// 显示进度圈
				mPbLoading.setVisibility(View.VISIBLE);
				mIvArrow.setVisibility(View.INVISIBLE);

				break;

			default:
				break;
		}
	}

	public void setOnRefreshListener(OnRefreshListener listener)
	{
		this.mListener = listener;
	}

	public interface OnRefreshListener
	{
		/**
		 * 当正在刷新时的回调
		 */
		void onRefreshing();

		/**
		 * 当加载更多时的回调
		 */
		void onLoadMore();
	}

	/**
	 * 通知刷新完成
	 */
	public void setRefreshFinish()
	{
		// 上拉加载更多
		if (isLoadMore)
		{
			// 状态
			isLoadMore = false;
			// 隐藏
			mFooterView.setPadding(0, -mFooterViewHight, 0, 0);
		}
		else
		{
			// 下拉刷新
			// 1. 状态改变
			mCurrentState = STATE_PULL_DOWN;
			// 2. ui改变
			refreshUI();
			// 3. 时间
			mTvTime.setText(toDateString(System.currentTimeMillis()));
			// 4. 隐藏
			// mRefreshHeader.setPadding(0, -mRefreshHeaderHeight, 0, 0);
			int start = mRefreshHeader.getPaddingTop();
			int end = -mRefreshHeaderHeight;
			doHeaderAnimation(start, end, false);
		}
	}

	private String toDateString(long time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// 如果最后一个可见的是adapter的size-1,显示加载更多
		int lastVisiblePosition = getLastVisiblePosition();

		if (isLoadMore == true) { return; }

		if ((scrollState == OnScrollListener.SCROLL_STATE_IDLE
			|| scrollState == OnScrollListener.SCROLL_STATE_FLING)
			&& lastVisiblePosition == getAdapter().getCount() - 1)
		{
			isLoadMore = true;

			// 显示最后一个
			mFooterView.setPadding(0, 0, 0, 0);

			// 选中底部
			setSelection(getAdapter().getCount());

			// 加载更多
			Log.d(TAG, "加载更多...");

			// 暴露接口回调加载更多的数据
			if (mListener != null)
			{
				mListener.onLoadMore();
			}
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		// TODO Auto-generated method stub

	}
}
