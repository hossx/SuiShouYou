package com.wml.suishouyou.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FramedImageView extends ImageView {
  private Uri mUri;
  
  public FramedImageView (Context context) {
    super(context);
  }

  public FramedImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FramedImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setUri(Uri uri) {
    mUri = uri;
  }
  
  public Uri getUri() {
    return mUri;
  }
}
