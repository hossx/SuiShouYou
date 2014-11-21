package com.wml.suishouyou.dialog;

import com.wml.suishouyou.R;
import com.wml.suishouyou.activity.ExplorerActivity;
import com.wml.suishouyou.activity.ProfileActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
// import android.util.Log;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View;
// import android.view.Gravity;
// import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

// import android.widget.ImageView;

public class Menu {

  // private ImageView image;
  private Dialog  mDialog;
  private Context mContext;

  public Menu(Context context) {
    mContext = context;
    mDialog = new Dialog(context, R.style.dialog);
    Window window = mDialog.getWindow();
    WindowManager.LayoutParams wl = window.getAttributes();
    wl.x = 0;
    wl.y = 85;
    window.setAttributes(wl);
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    window.setGravity(Gravity.TOP);
    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    mDialog.setContentView(R.layout.menu);
    mDialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
    LinearLayout homeItem = (LinearLayout) mDialog
        .findViewById(R.id.upContent1);
    homeItem.setClickable(true);
    homeItem.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        mDialog.dismiss();
      }

    });
    LinearLayout profileItem = (LinearLayout) mDialog
        .findViewById(R.id.upContent2);
    profileItem.setClickable(true);
    profileItem.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent toProfile = new Intent();
        toProfile.setClass(mContext, ProfileActivity.class);
        mContext.startActivity(toProfile);
        mDialog.dismiss();
      }

    });
    LinearLayout explorerItem = (LinearLayout) mDialog
        .findViewById(R.id.upContent3);
    explorerItem.setClickable(true);
    explorerItem.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent toExplorer = new Intent();
        toExplorer.setClass(mContext, ExplorerActivity.class);
        mContext.startActivity(toExplorer);
        mDialog.dismiss();
      }

    });
    LinearLayout settingItem = (LinearLayout) mDialog
        .findViewById(R.id.upContent4);
    settingItem.setClickable(true);
    settingItem.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent toProfile = new Intent();
        toProfile.setClass(mContext, ProfileActivity.class);
        mContext.startActivity(toProfile);
        mDialog.dismiss();
      }

    });
    /*
     * image = (ImageView) mDialog.findViewById(R.id.image);
     * image.setOnClickListener(new ImageView.OnClickListener() {
     * 
     * @Override public void onClick(View arg0) { mDialog.dismiss(); } });
     */
  }

  public void show() {
    mDialog.show();
  }

}