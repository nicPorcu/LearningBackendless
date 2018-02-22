package com.example.nicolo.learningbackendless;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by per6 on 2/22/18.
 */

public class RestaurantDirectory extends AppCompatActivity {
    public List<Restaurant> restaurantList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;
    public static final String TAG= "RestaurantDirectoryActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        restaurantList = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_directory);
        recyclerView = findViewById(R.id.recycle);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
