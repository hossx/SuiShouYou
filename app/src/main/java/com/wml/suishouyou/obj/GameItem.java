package com.wml.suishouyou.obj;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameItem {
  private String name;
  private String type;
  private String description;
  private String poster;
  private boolean isVertical;
  private ArrayList<String> videos = new ArrayList<String>();

  public static class GameItemBuilder {
    public static GameItem generateFromJson(JSONObject json) {
      GameItem item = new GameItem();
      try {
        item.setName(json.getString("name")).setType(json.getString("type"))
            .setDescription(json.getString("description")).setPoster(json.getString("poster"))
            .setIsVertical(json.getString("vertical"));
        JSONArray videoArray = json.getJSONArray("videos");
        for (int i = 0; i < videoArray.length(); ++i) {
          item.addVideo(videoArray.getString(i));
        }
        return item;
      } catch (JSONException e) {
        e.printStackTrace();
        return null;
      }
    }

  }

  public GameItem setIsVertical(String isVertical) {
    this.isVertical = (isVertical.equals("true"));
    return this;
  }

  public boolean getIsVertical() {
    return isVertical;
  }

  public GameItem setName(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return this.name;
  }

  public GameItem setType(String type) {
    this.type = type;
    return this;
  }

  public String getType() {
    return this.type;
  }

  public GameItem setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getDescription() {
    return this.description;
  }

  public GameItem setPoster(String poster) {
    this.poster = poster;
    return this;
  }

  public String getPoster() {
    return this.poster;
  }

  public GameItem addVideo(String video) {
    videos.add(video);
    return this;
  }

  public ArrayList<String> getVideos() {
    return this.videos;
  }
}