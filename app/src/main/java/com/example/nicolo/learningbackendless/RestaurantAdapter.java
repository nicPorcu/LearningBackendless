package com.example.nicolo.learningbackendless;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

import static com.example.nicolo.learningbackendless.RestaurantDirectory.TAG;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

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


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
        private TextView restaurantNameTextView, addressTextView, ratingTextView;

        private RecyclerViewClickListener recyclerViewClickListener;


        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {

            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.restaurant_name);
            addressTextView=itemView.findViewById(R.id.address);
            ratingTextView=itemView.findViewById(R.id.rating_textview);

            recyclerViewClickListener = listener;
            itemView.setOnCreateContextMenuListener(this);

        }
        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(view, getAdapterPosition());
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(context, "onclick", Toast.LENGTH_SHORT).show();
                    deleteObject(getAdapterPosition());
                    return false;
                }
            });


        }
    }

    private void deleteObject(final int adapterPosition) {
        Backendless.Persistence.of( Restaurant.class).remove( restaurantList.get(adapterPosition), new AsyncCallback<Long>(){

            @Override
            public void handleResponse(Long response) {
                restaurantList.remove(adapterPosition);
                //notifyItemRemoved(adapterPosition);
                notifyDataSetChanged();
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }
}
