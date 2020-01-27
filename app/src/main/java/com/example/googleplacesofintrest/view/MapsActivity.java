package com.example.googleplacesofintrest.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.googleplacesofintrest.R;
import com.example.googleplacesofintrest.database.FavoritesEnitity;
import com.example.googleplacesofintrest.model.PlacesPojo;
import com.example.googleplacesofintrest.model.Result;
import com.example.googleplacesofintrest.viewmodel.PlacesViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, PopupMenu.OnMenuItemClickListener {
    private static final int REQUEST_CODE = 777;

    private GoogleMap map;
    private LocationManager locationManager;
    private LatLng currentLatLng;
    private float zoomLevel = 15f;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Result> placesList = null;
    private PlacesFragment placesFragment = new PlacesFragment();
    private PlacesViewModel placesViewModel;

    private String type;
    private String title;
    private String photoUrl;
    private Double lat;
    private Double lng;

    ImageView typeMenu;
    Button favoriteButton;

    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeMenu = findViewById(R.id.type_menu);
        favoriteButton = findViewById(R.id.favorite_button);

        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);


        setUp();

    }

    public void setUp(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            setUpLocation();
        else
            requestPermissions();
    }

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.type_menu, menu);

        return true;
    }

    public void addFavorite(View view){
        //Log.d("TAG_X", "addFavorite: "+type);
        placesViewModel.insertFavorite(new FavoritesEnitity(title, type, photoUrl, lat, lng));
        favoriteButton.setVisibility(View.GONE);
    }

    public void onFragmentClick(View view) {
//        Bundle placesBundle = new Bundle();
//
//        for(int i = 0; i<placesList.size();i++){
//            placesBundle.putParcelable("places_"+i,placesList.get(i));
//        }
//
//        placesBundle.putInt("numList", placesList.size());
//        placesFragment.setArguments(placesBundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, placesFragment)
                .addToBackStack(placesFragment.getTag())
                .commit();

    }

    public void onMenuClick(View view){
        PopupMenu menu = new PopupMenu(this, view);
        MenuInflater menuInflater = menu.getMenuInflater();
        menuInflater.inflate(R.menu.type_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(this);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        String stringLocation = currentLatLng.latitude + "," + currentLatLng.longitude;
        switch (item.getItemId()){
            case R.id.park:
                Log.d("TAG_X", "park");
                type = "park";
                map.clear();
                placesList.clear();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel));
                map.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                selectType(stringLocation, "park");
                break;
            case R.id.restaurant:
                Log.d("TAG_X", "restaurant");
                type = "restaurant";
                map.clear();
                placesList.clear();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel));
                map.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                selectType(stringLocation, "restaurant");
                break;
            case R.id.library:
                Log.d("TAG_X", "library");
                type = "library";
                map.clear();
                placesList.clear();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel));
                map.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                selectType(stringLocation, "library");
                break;
            case R.id.bar:
                Log.d("TAG_X", "bar");
                type = "bar";
                map.clear();
                placesList.clear();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel));
                map.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                selectType(stringLocation, "bar");
                break;
            case R.id.movie_theater:
                Log.d("TAG_X", "movie_theater");
                type = "movie_theater";
                map.clear();
                placesList.clear();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel));
                map.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                selectType(stringLocation, "movie_theater");
                break;
            case R.id.gas_station:
                Log.d("TAG_X", "gas_station");
                type = "gas_station";
                map.clear();
                placesList.clear();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel));
                map.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                selectType(stringLocation, "gas_station");
                break;
            case R.id.store:
                Log.d("TAG_X", "store");
                type = "store";
                map.clear();
                placesList.clear();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel));
                map.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                selectType(stringLocation, "store");
                break;
        }

        return true;
    }

    private void selectType(String stringLocation, String type) {
        compositeDisposable.add(
                placesViewModel.getType(stringLocation,type)
                        .subscribe(
                                new Consumer<PlacesPojo>() {
                                    @Override
                                    public void accept(PlacesPojo placesPojo) throws Exception {
                                        //Log.d("TAG_X", "size: " + placesPojo.getResults().size());
                                        //placesPojo.getResults().get(0).getGeometry().getLocation().
                                        placesList = placesPojo.getResults();
                                        addMarkers(placesPojo.getResults());
                                    }
                                }
                        )
        );
    }
    //MENU

    //LOCATION

    private void addMarkers(List<Result> resultsList){
        for(int i =0; i< resultsList.size()-1; i++){
            LatLng placeLatLng = new LatLng(resultsList.get(i).getGeometry().getLocation().getLat(), resultsList.get(i).getGeometry().getLocation().getLng());
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(placeLatLng)
                    .title(resultsList.get(i).getName()))
                    ;

            //marker.setIcon(BitmapDescriptorFactory.fromResource(R.color.colorPrimaryDark));
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(200f));
            //Log.d("TAG_X", resultsList.get(i).getName()+": "+ resultsList.get(i).getPhotos().get(0).getPhotoReference()+"");
            if(!type.equals("store"))
                marker.setTag(resultsList.get(i).getPhotos().get(0).getPhotoReference());
        }
    }

    @SuppressLint("MissingPermission")
    private void setUpLocation(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 5, (LocationListener) this);
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){
            if(permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                requestPermissions();
            }
            else if(permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                setUpLocation();
            }
        }
    }
    //LOCATION

    //MAPS
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                //Log.d("TAG_X", "onMarkerClick: "+marker.getTag());
                if(!marker.getTitle().equals("Current Location")) {
                    LatLng position = (LatLng)(marker.getPosition());
                    title = marker.getTitle();
                    lat = position.latitude;
                    lng = position.longitude;
                    if(!type.equals("store"))
                        photoUrl = marker.getTag().toString();
                    //Toast.makeText(MapsActivity.this, title, Toast.LENGTH_SHORT).show();

                    favoriteButton.setText("Add " + title + " to favorites");
                    favoriteButton.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

    }
    //MAPS

    //LOCATION
    @Override
    public void onLocationChanged(Location location) {
        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        //Log.d("TAG_X","location changed");

        String stringLocation = currentLatLng.latitude + "," + currentLatLng.longitude;
        selectType(stringLocation, "restaurant");
        type = "restaurant";

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel));
        map.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
        compositeDisposable.clear();
    }

    //LOCATION

}