package com.wml.suishouyou.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.VideoView;

public class FramedVideoView extends VideoView {
  private float mPreviousX;
  private float mPreviousY;
  private int mTouchSlop;
  // private static int WIDTH;
  // private static int HEIGHT;

  private boolean mIsAutoPlay = true;
  private boolean mIsPaused = false;

  private OnPreparedListener mOnPreparedListener = null;
  private BitmapDrawable mThumb;

  public FramedVideoView(Context context) {
    super(context);
    init();
  }

  public FramedVideoView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public FramedVideoView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public boolean isPaused() {
    return mIsPaused;
  }

  public void setThumb(BitmapDrawable thumb) {
    mThumb = thumb;
  }

  // TODO(chaoma) fix deperecate
  @SuppressWarnings("deprecation")
  public void setThumbBackground() {
    setBackgroundDrawable(mThumb);
  }

  public FramedVideoView setAutoPlay(boolean isAutoPlay) {
    mIsAutoPlay = isAutoPlay;
    return this;
  }

  public boolean isAutoPlay() {
    return mIsAutoPlay;
  }

  public void setBlack() {
    setBackgroundColor(Color.rgb(218, 218, 218));
  }

  @Override
  public void setOnPreparedListener(OnPreparedListener l) {
    mOnPreparedListener = l;
  }

  @Override
  public void pause() {
    super.pause();
    mIsPaused = true;
  }

  // TODO(chaoma): replace the deprecated technology.
  @Override
  public void setVideoURI(Uri uri) {
    super.setVideoURI(uri);
  }

  @SuppressWarnings("deprecation")
  @Override
  public void start() {
    // Since the background is transparent, need set some color(black) here to
    // show the video
    setBlack();
    setBackgroundDrawable(null);
    super.start();
    mIsPaused = false;
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    final int action = ev.getAction();
    final float x = ev.getX();
    final float y = ev.getY();
    switch (action) {
    case MotionEvent.ACTION_DOWN:
      mPreviousX = x;
      mPreviousY = y;
      break;
    case MotionEvent.ACTION_UP:
      final float deltaX = Math.abs(x - mPreviousX);
      final float deltaY = Math.abs(y - mPreviousY);
      if (deltaX < mTouchSlop && deltaY < mTouchSlop) {
        if (isPlaying()) {
          setAutoPlay(false);
          pause();
        } else {
          setAutoPlay(true);
          start();
        }
      } else {
        stopPlayback();
        setVisibility(View.GONE);
      }
      break;
    default:
      break;
    }
    // if return false, won't trigger the ACTION_UP event.
    return true;
  }

  /*
   * @Override protected void onMeasure(int widthMeasureSpec, int
   * heightMeasureSpec) { int width = getDefaultSize(WIDTH, widthMeasureSpec);
   * int height = getDefaultSize(HEIGHT, heightMeasureSpec);
   * setMeasuredDimension(width, height); }
   */

  public void onPrepared(MediaPlayer mp) {
    mp.seekTo(1);
    mp.setLooping(true);
  }

  private void init() {
    final ViewConfiguration configuration = ViewConfiguration.get(getContext());
    mTouchSlop = configuration.getScaledTouchSlop() * 3;

    super.setOnPreparedListener(new OnPreparedListener() {
      @Override
      public void onPrepared(MediaPlayer mp) {
        FramedVideoView.this.onPrepared(mp);
        if (mOnPreparedListener != null) {
          mOnPreparedListener.onPrepared(mp);
        }
      }
    });
  }
}
