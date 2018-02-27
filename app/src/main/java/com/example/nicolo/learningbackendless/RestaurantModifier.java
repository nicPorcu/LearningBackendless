package com.example.nicolo.learningbackendless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

public class RestaurantModifier extends AppCompatActivity {

    private EditText restaurantNameText,restaurantAddressText, ratingText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_modifier);
        wireWidgets();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Restaurant restaurant=new Restaurant(restaurantNameText.getText().toString(),Integer.parseInt(ratingText.getText().toString()), restaurantAddressText.getText().toString());
                Backendless.Persistence.save(restaurant, new AsyncCallback<Restaurant>() {
                    @Override
                    public void handleResponse(Restaurant response) {
                        Intent i=new Intent();
                        i.putExtra("restaurantNameText",restaurantNameText.getText().toString());
                        i.putExtra("restaurantAddressText",restaurantAddressText.getText().toString());
                        i.putExtra("ratingText",ratingText.getText().toString());
                        setResult(RESULT_OK, i);
                        finish();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(RestaurantModifier.this, fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }

    private void wireWidgets() {
        restaurantNameText=findViewById(R.id.restaurant_name_text);
        restaurantAddressText=findViewById(R.id.address_text);
        ratingText=findViewById(R.id.rating_text);
        saveButton=findViewById(R.id.save_button);
    }
}
