package com.wml.suishouyou.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wml.suishouyou.R;
import com.wml.suishouyou.adapter.GameListAdapter;
import com.wml.suishouyou.dialog.Menu;
import com.wml.suishouyou.obj.GameItem;
import com.wml.suishouyou.view.FramedVideoView;
import com.wml.suishouyou.view.GameListView;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
// import android.view.View;
import android.view.View.OnClickListener;
// import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
// import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {
  private GameListView mGameListView;
  private JSONArray jsonList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    this.getWindow().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.flow_page_bkcolor));

    setContentView(R.layout.activity_main);
    ArrayList<GameItem> gameList = new ArrayList<GameItem>();
    try {
      InputStream is = getAssets().open("recommendation_games.json");
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      String bufferString = new String(buffer);
      jsonList = new JSONArray(bufferString);
      for (int i = 0; i < jsonList.length(); ++i) {
        JSONObject jsonObj = jsonList.getJSONObject(i);
        gameList.add(GameItem.GameItemBuilder.generateFromJson(jsonObj));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
    }

    int screenCenterHeight = getResources().getDisplayMetrics().heightPixels / 2;
    mGameListView = (GameListView) findViewById(R.id.gamelist);
    mGameListView.setScreenCenterHeight(screenCenterHeight);
    mGameListView.setAdapter(new GameListAdapter(this, (FramedVideoView) findViewById(R.id.videoview))
        .setScreenCenterHeight(screenCenterHeight).setData(gameList));

    ImageView imageView = (ImageView) findViewById(R.id.home_btn);
    imageView.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        new Menu(MainActivity.this).show();
      }
    });
  }

  /*
   * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
   * menu; this adds items to the action bar if it is present.
   * getMenuInflater().inflate(R.menu.main, menu); return true; }
   */

  @Override
  protected void onPause() {
    super.onPause();
    // mGameListView.detachVideo();
    mGameListView.pauseVideo();
  }
}
