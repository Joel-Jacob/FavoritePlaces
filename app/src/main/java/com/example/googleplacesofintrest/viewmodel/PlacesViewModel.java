package com.example.googleplacesofintrest.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import com.example.googleplacesofintrest.database.FavoriteDatabase;
import com.example.googleplacesofintrest.database.FavoritesEnitity;
import com.example.googleplacesofintrest.model.PlacesPojo;
import com.example.googleplacesofintrest.network.PlacesRetrofit;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlacesViewModel extends AndroidViewModel {
    private PlacesRetrofit placesRetrofit = new PlacesRetrofit();
    private FavoriteDatabase favoriteDatabase;

    private List<FavoritesEnitity> favorites;

    public PlacesViewModel(@NonNull Application application) {
        super(application);
        try{
            favoriteDatabase = Room.databaseBuilder(
                    this.getApplication(),
                    FavoriteDatabase.class,
                    "Favorites.db")
                    .allowMainThreadQueries()
                    .build();
            //Log.d("TAG_X", "buildDatabase: "+(favoriteDatabase==null));
        }catch (Exception e){
            Log.d("TAG_X", "Error: "+ e);
        }
    }

    public Observable<PlacesPojo> getType(String stringLocation, String type){
        return placesRetrofit.getNearbyPlaces(stringLocation, "1500", type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public List<FavoritesEnitity> getFavorites(){
        //Log.d("TAG_X", "getFavorites: "+(favoriteDatabase==null));
        favorites = favoriteDatabase.placesDAO().getAllFavorites();
        if(favorites.isEmpty())
            return null;
        else return favorites;
//        return  new ArrayList<FavoritesEnitity>();
    }

    public void insertFavorite(FavoritesEnitity favoritesEnitity){
        try {
            favoriteDatabase.placesDAO().insertNewPlace(favoritesEnitity);
            //Log.d("TAG_X", "inserted");
        }catch (Exception e){
            Log.d("TAG_X", "Error: "+ e);
        }
    }

    public void deleteFavorite(FavoritesEnitity favoritesEnitity){
        try {
            favoriteDatabase.placesDAO().deletePlace(favoritesEnitity);
        }catch (Exception e){
            Log.d("TAG_X", "Error: "+ e);
        }
    }

}
