package com.wml.suishouyou.adapter;

import java.util.ArrayList;

import com.wml.suishouyou.activity.DetailActivity;
import com.wml.suishouyou.obj.GameItem;
import com.wml.suishouyou.util.Util;
import com.wml.suishouyou.view.FramedImageView;
import com.wml.suishouyou.view.FramedVideoView;
// import com.wml.suishouyou.CircleFlowIndicator;
import com.wml.suishouyou.widget.CircleFlowIndicator;
import com.wml.suishouyou.widget.ViewFlow;
import com.wml.suishouyou.widget.ViewFlow.ViewSwitchListener;

import com.wml.suishouyou.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GameListAdapter extends BaseAdapter {

  private LayoutInflater      mInflater;
  private FramedVideoView     mVideoView;
  private ViewGroup           mCurrentLayout      = null;

  private Handler             mAttachVideoHandler = new Handler();
  private Runnable            mAttachVideoRunnable;

  private int                 mScreenCenterHeight;
  private ArrayList<GameItem> mGameList;
  private Context             mContext;

  public GameListAdapter(Context context, FramedVideoView video) {
    mContext = context;

    mInflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mCurrentLayout = (ViewGroup) video.getParent();

    mVideoView = video;

    mAttachVideoRunnable = new Runnable() {

      @Override
      public void run() {
        internelAttachVideo();
      }
    };

  }

  public void pauseVideo() {
    mVideoView.pause();
  }

  public GameListAdapter setScreenCenterHeight(int height) {
    mScreenCenterHeight = height;
    return this;
  }

  public GameListAdapter setData(ArrayList<GameItem> gameList) {
    this.mGameList = gameList;
    return this;
  }

  private void internelAttachVideo() {
    if (mCurrentLayout == null) {
      return;
    }
    FramedImageView image = (FramedImageView) mCurrentLayout
        .findViewWithTag("image");
    mCurrentLayout.addView(mVideoView);
    // show videoView under the image at the beginning to avoid the wired
    // videoview size shown to the user.
    // the precondition is the background of the videoview is the same with the
    // gallery's background(black).
    mCurrentLayout.bringChildToFront(image);
    mVideoView.setBlack();
    mVideoView.setVideoURI(image.getUri());
    // 褰撹棰戝浜庝笉鑷姩鎾斁鐨勬椂鍊欓渶鑷充簬鍓嶇(鍙偣鍑�骞朵笖鎶婅儗鏅鎴愬拰鍥剧墖涓�牱銆� //
    // 褰撶劧锛屼篃鍙互璁╁浘鐗囧彉寰楀彲浠ョ偣鍑伙紝涓嶈繃鎰熻鏇撮夯鐑︼紝浠ュ悗鍐嶇湅鏄惁闇�鏀硅繃鏉ャ�
    mVideoView.setThumb((BitmapDrawable) image.getDrawable());
    mVideoView.setOnPreparedListener(new OnPreparedListener() {
      @Override
      public void onPrepared(MediaPlayer mp) {
        mVideoView.setThumbBackground();
        mCurrentLayout.bringChildToFront(mVideoView);
        if (mVideoView.isAutoPlay()) {
          mVideoView.start();
        }
      }
    });
  }

  public void detachVideo() {
    detachVideo(mCurrentLayout);
  }

  public void detachVideo(ViewGroup layout) {
    if (mCurrentLayout == null || mCurrentLayout != layout) {
      return;
    }
    mVideoView.stopPlayback();
    mCurrentLayout.removeView(mVideoView);
    mCurrentLayout = null;
  }

  public void attachVideo(ViewGroup layout) {
    if (mCurrentLayout == layout) {
      if (mVideoView.isPaused() && mVideoView.isAutoPlay()) {
        mVideoView.start();
      }
      return;
    }

    if (mCurrentLayout != null) {
      mVideoView.stopPlayback();
      mCurrentLayout.removeView(mVideoView);
    }

    mCurrentLayout = layout;
    mAttachVideoHandler.removeCallbacks(mAttachVideoRunnable);
    mAttachVideoHandler.postDelayed(mAttachVideoRunnable, 1000);
  }

  @Override
  public int getCount() {
    return mGameList.size();
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.game_item, null);
    }

    GameItem gameItem = mGameList.get(position);

    ImageView image = (ImageView) convertView.findViewById(R.id.poster);
    image.setImageBitmap(Util.getBitmapFromFile(Util.getApplicationRoot()
        + gameItem.getPoster()));
    image.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent toDetail = new Intent();
        toDetail.setClass(mContext, DetailActivity.class);
        mContext.startActivity(toDetail);
      }
    });

    TextView nameText = (TextView) convertView.findViewById(R.id.name);
    nameText.setText(gameItem.getName());
    nameText.setTextSize(25);

    /*
     * TextView typeText = (TextView) convertView.findViewById(R.id.type);
     * typeText.setText(gameItem.getType()); typeText.setTextSize(20);
     * 
     * TextView descriptionText = (TextView)
     * convertView.findViewById(R.id.description);
     * descriptionText.setText(gameItem.getDescription());
     * descriptionText.setTextSize(15);
     */

    ViewFlow viewFlow = (ViewFlow) convertView.findViewById(R.id.viewflow);
    if (gameItem.getIsVertical()) {
      LayoutParams params = viewFlow.getLayoutParams();
      params.height = 640;
      viewFlow.setLayoutParams(params);
    }
    viewFlow.setTag("viewflow");
    viewFlow.setAdapter(
        new ImageAdapter(mContext).setIds(gameItem.getVideos()), 0);
    viewFlow.setFlowIndicator((CircleFlowIndicator) convertView
        .findViewById(R.id.viewflowindic));

    viewFlow.setOnViewSwitchListener(new ViewSwitchListener() {
      @Override
      public void onSwitched(View view, int position) {
        ViewGroup layout = (ViewGroup) view.findViewById(R.id.gallerylayout);
        final int[] location = new int[2];
        layout.getLocationOnScreen(location);
        if (mScreenCenterHeight > location[1]
            && mScreenCenterHeight < location[1] + layout.getHeight()) {
          attachVideo(layout);
        } else {
          detachVideo();
        }
      }
    });
    return convertView;
  }
}
