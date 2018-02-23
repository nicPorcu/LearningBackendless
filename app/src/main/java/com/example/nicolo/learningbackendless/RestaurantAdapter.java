package com.example.nicolo.learningbackendless;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static com.example.nicolo.learningbackendless.RestaurantDirectory.TAG;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>{

    private List<Restaurant> restaurantList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;



    public RestaurantAdapter(List<Restaurant> restaurantList, Context context, RecyclerViewClickListener listener) {
        this.restaurantList = restaurantList;
        this.context = context;
        recyclerViewClickListener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item,parent,false);
        return new MyViewHolder(itemView,recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.MyViewHolder holder, int position) {
        String text=restaurantList.get(position).getName();
        holder.restaurantNameTextView.setText(text);
        holder.addressTextView.setText(restaurantList.get(position).getAddress());
        holder.ratingTextView.setText(restaurantList.get(position).getRating()+"");
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView restaurantNameTextView, addressTextView, ratingTextView;

        private RecyclerViewClickListener recyclerViewClickListener;


        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {

            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.restaurant_name);
            addressTextView=itemView.findViewById(R.id.address);
            ratingTextView=itemView.findViewById(R.id.rating_textview);

            recyclerViewClickListener = listener;

        }
        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(view, getAdapterPosition());
        }
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }
}
