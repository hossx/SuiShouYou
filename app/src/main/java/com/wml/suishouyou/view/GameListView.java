package com.wml.suishouyou.view;

import com.wml.suishouyou.R;
import com.wml.suishouyou.adapter.GameListAdapter;
import com.wml.suishouyou.widget.ViewFlow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

public class GameListView extends ListView {

  private float mPreviousX;
  private float mPreviousY;
  private int mTouchSlop;
  private OnScrollListener mOnScrollListener = null;
  private int mScreenCenterHeight;

  public GameListView(Context context) {
    super(context);
    init();
  }

  public GameListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public GameListView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public void pauseVideo() {
    ((GameListAdapter) getAdapter()).pauseVideo();
  }

  public void detachVideo() {
    ((GameListAdapter) getAdapter()).detachVideo();
  }

  public void setScreenCenterHeight(int height) {
    mScreenCenterHeight = height;
  }

  // Doesn't dispatch the touch event if the major moving direction is
  // horizontal. So that the list vertical scrolling
  // action won't interrupt the scrolling action of the viewflow component.
  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    boolean result = super.onInterceptTouchEvent(ev);

    final int action = ev.getAction();
    final float x = ev.getX();
    final float y = ev.getY();
    switch (action) {
    case MotionEvent.ACTION_DOWN:
      mPreviousX = x;
      mPreviousY = y;
      break;
    case MotionEvent.ACTION_MOVE:
      final float deltaX = Math.abs(x - mPreviousX);
      final float deltaY = Math.abs(y - mPreviousY);
      if (deltaX < mTouchSlop && deltaY < mTouchSlop) {
        return false;
      }
      result = (deltaY > (deltaX * 2));
      break;
    default:
      break;
    }
    return result;
  }

  @Override
  public void setOnScrollListener(OnScrollListener l) {
    mOnScrollListener = l;
  }

  private void init() {
    final ViewConfiguration configuration = ViewConfiguration.get(getContext());
    mTouchSlop = configuration.getScaledTouchSlop() * 3;
    super.setOnScrollListener(new OnScrollListener() {
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) {
          mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int startIndex = firstVisibleItem - view.getFirstVisiblePosition();
        int endIndex = startIndex + visibleItemCount;
        for (int i = startIndex; i < endIndex; ++i) {
          View itemView = view.getChildAt(i);
          if (itemView == null) {
            continue;
          }
          ViewFlow viewFlow = (ViewFlow) itemView.findViewWithTag("viewflow");
          ViewGroup layout = (ViewGroup) viewFlow.getSelectedView().findViewById(R.id.gallerylayout);
          final int[] location = new int[2];
          layout.getLocationOnScreen(location);
          GameListAdapter adapter = (GameListAdapter) GameListView.this.getAdapter();
          if (mScreenCenterHeight > location[1] && mScreenCenterHeight < location[1] + layout.getHeight()) {
            adapter.attachVideo(layout);
          } else {
            adapter.detachVideo(layout);
          }
        }

        if (mOnScrollListener != null) {
          mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
      }
    });
  }
}