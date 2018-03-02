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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by per6 on 2/22/18.
 */

public class RestaurantDirectory extends AppCompatActivity {
    private static final int RESTAURANT_MODIFIER_REQUEST_NEW = 47;
    private static final int RESTAURANT_MODIFIER_REQUEST_UPDATE = 48;

    public List<Restaurant> restaurantList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;
    public static final String TAG= "RestaurantDirectory";
    private String userId;
    private FloatingActionButton newRestaurantButton;
    private int pos;


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
                Intent i= new Intent(RestaurantDirectory.this, RestaurantModifier.class);
                i.putExtra("restaurant_name", restaurantList.get(position).getName());
                i.putExtra("restaurant_rating", restaurantList.get(position).getRating()+"");
                i.putExtra("restaurant_address", restaurantList.get(position).getAddress());
                pos=position;
                startActivityForResult(i, RESTAURANT_MODIFIER_REQUEST_UPDATE);

            }

        };
        newRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anotherIntentThing();


            }
        });
                adapter = new RestaurantAdapter(restaurantList, this, listener);
        recyclerView.setAdapter(adapter);

        //testDataManipulation();
        retrieveData();

    }

    private void anotherIntentThing() {
        Intent i= new Intent(this, RestaurantModifier.class);
        startActivityForResult(i, RESTAURANT_MODIFIER_REQUEST_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode== RESTAURANT_MODIFIER_REQUEST_NEW){
            retrieveData();

            adapter.notifyDataSetChanged();
        }
        if(resultCode==RESULT_OK && requestCode==RESTAURANT_MODIFIER_REQUEST_UPDATE){
            Backendless.Persistence.of( Restaurant.class).remove( restaurantList.get(pos), new AsyncCallback<Long>(){

                @Override
                public void handleResponse(Long response) {
                    retrieveData();
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(RestaurantDirectory.this, fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    private void retrieveData() {
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
