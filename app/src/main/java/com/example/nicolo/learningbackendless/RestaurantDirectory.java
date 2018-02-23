package com.example.nicolo.learningbackendless;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

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

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
            }
        };
        adapter = new RestaurantAdapter(restaurantList, this, listener);
        recyclerView.setAdapter(adapter);
        testDataRetrieval();
    }

    private void testDataRetrieval() {
        Backendless.Persistence.of((Restaurant.class)).find(new AsyncCallback<List<Restaurant>>() {
            @Override
            public void handleResponse(List<Restaurant> response) {
                Log.d(TAG, "handleResponse: "+response);
                restaurantList.addAll(response);
                Log.d(TAG, "handleResponse: "+ restaurantList.get(0).getName());

                adapter.notifyDataSetChanged();
                Log.d(TAG, "handleResponse: "+ adapter.getRestaurantList().get(0).getName());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d("Look Here", "handleFault: "+fault.getMessage());

            }
        });
    }
}
