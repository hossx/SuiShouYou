package com.wml.suishouyou.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.wml.suishouyou.R;
import com.wml.suishouyou.util.Util;
import com.wml.suishouyou.view.FramedImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ImageAdapter extends BaseAdapter {

  private LayoutInflater mInflater;
  /*
   * private static final int[] ids = { R.drawable.cupcake, R.drawable.donut,
   * R.drawable.eclair, R.drawable.froyo, R.drawable.gingerbread,
   * R.drawable.honeycomb, R.drawable.icecream };
   */
  private ArrayList<String> ids;

  public ImageAdapter(Context context) {
    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  public ImageAdapter setIds(ArrayList<String> ids) {
    this.ids = ids;
    return this;
  }

  @Override
  public int getCount() {
    return ids.size();
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
      convertView = mInflater.inflate(R.layout.image_item, null);
    }
    FramedImageView framedImageView = (FramedImageView) convertView.findViewById(R.id.imgView);
    String videoPath = Util.getApplicationRoot() + ids.get(position);
    String thumbPath = videoPath + ".thumb";
    File thumbFile = new File(thumbPath);
    if (thumbFile.exists()) {
      framedImageView.setImageBitmap(Util.getBitmapFromFile(thumbPath));
    } else {
      Bitmap thumb = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
      framedImageView.setImageBitmap(thumb);
      // TODO(chaoma): opt here (make this async)
      try {
        thumbFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(thumbFile);
        thumb.compress(CompressFormat.JPEG, 50, fos);
        fos.flush();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    framedImageView.setUri(Uri.parse(videoPath));
    framedImageView.setTag("image");
    return convertView;
  }
}