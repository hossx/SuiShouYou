package com.wml.suishouyou.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public final class Util {
  
  public static enum ScreenDirection {
    NORMAL, INVERT, LEFT_UP, RIGHT_UP;
  }
  
  private static BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();

  static {
    mBitmapOptions.inSampleSize = 2;
  }

  public static String getApplicationRoot() {
    return Environment.getExternalStorageDirectory() + "/evergame/";
  }

  public static Bitmap getBitmapFromFile(String path) {
    return BitmapFactory.decodeFile(path, mBitmapOptions);
  }
}