package com.example.googleplacesofintrest.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.googleplacesofintrest.R;
import com.example.googleplacesofintrest.adapter.PlacesAdapter;
import com.example.googleplacesofintrest.database.FavoritesEnitity;
import com.example.googleplacesofintrest.viewmodel.PlacesViewModel;

import java.util.List;

public class PlacesFragment extends Fragment implements PlacesAdapter.PlacesDelegate {
    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayout;
    private Button deleteButton;
    private ImageView closeButton;

    private PlacesViewModel placesViewModel;
    private FavoritesEnitity favoritesEnitity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        constraintLayout = view.findViewById(R.id.delete_cl);
        deleteButton = view.findViewById(R.id.delete_bt);
        closeButton = view.findViewById(R.id.close_bt);

        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
        setRV(placesViewModel.getFavorites());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFavoriteButton();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeLayout();
            }
        });
    }

    private void setRV(List<FavoritesEnitity> favoritesEnitities){
        PlacesAdapter adapter = new PlacesAdapter(placesViewModel.getFavorites(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL, false));
    }

    @Override
    public void deleteFavorite(FavoritesEnitity favoritesEnitity) {
        constraintLayout.setVisibility(View.VISIBLE);
        this.favoritesEnitity = favoritesEnitity;
    }

    public void deleteFavoriteButton(){
        placesViewModel.deleteFavorite(favoritesEnitity);
        constraintLayout.setVisibility(View.GONE);
        setRV(placesViewModel.getFavorites());
    }

    public void closeLayout(){
        constraintLayout.setVisibility(View.GONE);
    }

}
