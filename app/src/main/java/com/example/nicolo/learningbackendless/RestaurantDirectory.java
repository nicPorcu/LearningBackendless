package com.example.nicolo.learningbackendless;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.social.SocialLoginDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by per6 on 2/22/18.
 */

public class RestaurantDirectory extends AppCompatActivity {
    private static final int REGISTRATION_REQUEST = 47;
    public List<Restaurant> restaurantList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;
    public static final String TAG= "RestaurantDirectory";
    private String userId;
    private FloatingActionButton newRestaurantButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        restaurantList = new ArrayList<>();

        Intent i=getIntent();
        userId=i.getStringExtra("user_id");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_directory);
        recyclerView = findViewById(R.id.recycle);
        newRestaurantButton=findViewById(R.id.new_restaurant_button);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

        };
        newRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RestaurantDirectory.this, "todo", Toast.LENGTH_SHORT).show();
                anotherIntentThing();


            }
        });
                adapter = new RestaurantAdapter(restaurantList, this, listener);
        recyclerView.setAdapter(adapter);

        //testDataManipulation();
        testDataRetrieval();

    }

    private void anotherIntentThing() {
        Intent i= new Intent(this, RestaurantModifier.class);
        startActivityForResult(i, REGISTRATION_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode==REGISTRATION_REQUEST){
            restaurantList.add(new Restaurant(data.getStringExtra("restaurantNameText"),Integer.parseInt(data.getStringExtra("ratingText")),data.getStringExtra("restaurantAddressText")));
            adapter.notifyDataSetChanged();
        }
    }

    private void testDataRetrieval() {
        String whereClause="ownerId = '"+userId+"'";
        DataQueryBuilder queryBuilder=DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        Backendless.Data.of((Restaurant.class)).find(queryBuilder, new AsyncCallback<List<Restaurant>>() {
            @Override
            public void handleResponse(List<Restaurant> response) {
                Log.d(TAG, "handleResponse: "+response);
                restaurantList.clear();
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


    public void testDataManipulation(){
        Restaurant r =new Restaurant("Hi-Life", 3, "Garfield?");
        Backendless.Persistence.save(r, new AsyncCallback<Restaurant>() {
            @Override
            public void handleResponse(Restaurant response) {
                Toast.makeText(RestaurantDirectory.this, "yay", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(RestaurantDirectory.this, fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //Create
        //Update
        //Destroy
    }
}
