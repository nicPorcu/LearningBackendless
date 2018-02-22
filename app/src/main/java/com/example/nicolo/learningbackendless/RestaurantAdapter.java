package com.example.nicolo.learningbackendless;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class RestaurantAdapter extends AppCompatActivity {

    private List<Restaurant> restaurantList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;



    public RestaurantAdapter(List<Restaurant> restaurantList, Context context, RecyclerViewClickListener listener) {
        this.restaurantList = restaurantList;
        this.context = context;
        recyclerViewClickListener=listener;
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.MyViewHolder holder, int position) {
        String text=restaurantlist.get(position).getQuestion();
        text = fixQuestion(text);


        holder.questionText.setText(text);
        Log.d(TAG, "onBindViewHolder: ques"+questionlist.get(position).getCorrectAnswer());
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView questionText;
        public Button trueButton, falseButton;
        private RecyclerViewClickListener recyclerViewClickListener;


        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {

            super(itemView);
            questionText= itemView.findViewById(R.id.questionTextView);
            trueButton=itemView.findViewById(R.id.true_button);
            falseButton=itemView.findViewById(R.id.false_button);
            trueButton.setOnClickListener(this);
            falseButton.setOnClickListener(this);
            recyclerViewClickListener = listener;

        }
        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(view, getAdapterPosition());
        }
    }


}
