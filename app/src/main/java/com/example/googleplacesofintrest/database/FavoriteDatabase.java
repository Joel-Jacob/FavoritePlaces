package com.example.googleplacesofintrest.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {FavoritesEnitity.class})
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract PlacesDAO placesDAO();
}
