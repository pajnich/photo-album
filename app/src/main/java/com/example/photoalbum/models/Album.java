package com.example.photoalbum.models;

import java.util.ArrayList;
import java.util.Random;

public class Album {
    private int userId;
    private int id;
    private String title;
    private ArrayList<Photo> photos;
    private int activePhotoPosition;

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
        if (photos == null) {
            photos = new ArrayList<>();
        }
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public Photo getPhoto(int position) {
        return photos.get(position);
    }

    private int getActivePhotoPosition() {
        return activePhotoPosition;
    }

    public void setActivePhotoPosition(int activePhotoPosition) {
        this.activePhotoPosition = activePhotoPosition;
    }

    public Photo getActivePhoto() {
        return photos.get(getActivePhotoPosition());
    }
}
