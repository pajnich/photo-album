package com.example.photoalbum.models;

public class Photo {
    private int id;
    private int albumId;
    private String title;
    private String url;
    private String thumbnailUrl;

    public int getAlbumId() {
        return albumId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
