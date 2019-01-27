package com.example.photoalbum.models;

import java.util.ArrayList;

public class User {
    private String name;
    private String username;
    private int id;
    private int activeAlbumPosition;
    private ArrayList<Album> albums;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Album> getAlbums() {
        if (albums == null) {
            albums = new ArrayList<>();
        }
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public int getActiveAlbumPosition() {
        return activeAlbumPosition;
    }

    public void setActiveAlbumPosition(int activeAlbumPosition) {
        this.activeAlbumPosition = activeAlbumPosition;
    }

    public Album getActiveAlbum() {
        return albums.get(getActiveAlbumPosition());
    }
}
