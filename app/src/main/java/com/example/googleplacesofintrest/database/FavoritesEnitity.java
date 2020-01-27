package com.example.googleplacesofintrest.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favorites")
public class FavoritesEnitity {
    @PrimaryKey(autoGenerate = true)
    private int placeId;

    @ColumnInfo(name = "title")
    private String placeTitle;

    @ColumnInfo(name = "type")
    private String placeType;

    @ColumnInfo(name = "photoUrl")
    private String photoUrl;

    @ColumnInfo(name = "Lat")
    private double placeLat;

    @ColumnInfo(name = "Lng")
    private double placeLng;

    public FavoritesEnitity(int placeId, String placeTitle, String placeType, String photoUrl, double placeLat, double placeLng) {
        this.placeId = placeId;
        this.placeTitle = placeTitle;
        this.placeType = placeType;
        this.photoUrl = photoUrl;
        this.placeLat = placeLat;
        this.placeLng = placeLng;
    }

    @Ignore
    public FavoritesEnitity(String placeTitle, String placeType, String photoUrl, double placeLat, double placeLng) {
        this.placeTitle = placeTitle;
        this.placeType = placeType;
        this.photoUrl = photoUrl;
        this.placeLat = placeLat;
        this.placeLng = placeLng;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public double getPlaceLat() {
        return placeLat;
    }

    public void setPlaceLat(double placeLat) {
        this.placeLat = placeLat;
    }

    public double getPlaceLng() {
        return placeLng;
    }

    public void setPlaceLng(double placeLng) {
        this.placeLng = placeLng;
    }
}
