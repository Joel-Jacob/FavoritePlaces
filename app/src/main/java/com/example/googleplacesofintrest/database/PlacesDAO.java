package com.example.googleplacesofintrest.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlacesDAO {
    @Query("SELECT * FROM favorites")
    public List<FavoritesEnitity> getAllFavorites();

    @Insert
    void insertNewPlace(FavoritesEnitity favoritesEnitity);

    @Delete
    void deletePlace(FavoritesEnitity favoritesEnitity);
}
