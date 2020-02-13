package com.example.qq.bihu;

import android.graphics.Bitmap;

public class Question {
    private int id;
    private String title, content, images, date, authorName, authorAvatar;
    private boolean is_exciting, is_favorite;
    private Bitmap bitmap_head, bitmap_content;

    public Bitmap getBitmap_head() {
        return bitmap_head;
    }

    public void setBitmap_head(Bitmap bitmap_head) {
        this.bitmap_head = bitmap_head;
    }

    public Bitmap getBitmap_content() {
        return bitmap_content;
    }

    public void setBitmap_content(Bitmap bitmap_content) {
        this.bitmap_content = bitmap_content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public boolean isIs_exciting() {
        return is_exciting;
    }

    public void setIs_exciting(boolean is_exciting) {
        this.is_exciting = is_exciting;
    }

    public boolean isIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    public Question(int id, String title, String content, String images, String date, String authorName, String authorAvatar, boolean is_exciting, boolean is_favorite, Bitmap bitmap_head, Bitmap bitmap_content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.images = images;
        this.date = date;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.is_exciting = is_exciting;
        this.is_favorite = is_favorite;
        this.bitmap_head = bitmap_head;
        this.bitmap_content = bitmap_content;
    }
}
