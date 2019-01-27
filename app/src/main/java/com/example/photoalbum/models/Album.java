package com.example.photoalbum.models;

import java.util.ArrayList;
import java.util.Random;

public class Album {
    private int userId;
    private int id;
    private String title;
    private ArrayList<Photo> photos;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRandomThumbnailUrl() {
        if (photos != null) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(photos.size());
            return photos.get(randomIndex).getThumbnailUrl();
        }
        return null;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
