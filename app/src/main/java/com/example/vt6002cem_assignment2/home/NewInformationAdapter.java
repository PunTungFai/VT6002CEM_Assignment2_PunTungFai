package com.example.vt6002cem_assignment2.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vt6002cem_assignment2.R;

import java.util.ArrayList;

public class NewInformationAdapter extends RecyclerView.Adapter<NewInformationAdapter.newsviewHolder> {

    ArrayList<NewInformation> events;


    public NewInformationAdapter(ArrayList<NewInformation> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public newsviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set the layout id
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_events_card_design,parent,false);
        newsviewHolder newsviewholder = new newsviewHolder(view);

        return newsviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull newsviewHolder holder, int position) {
        NewInformation newsHelperClass = events.get(position);

        //set the homepage-news-card image
        holder.image.setImageResource(newsHelperClass.getImage());





    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    public static class newsviewHolder extends RecyclerView.ViewHolder{

        ImageView image;


        public newsviewHolder(@NonNull View itemView) {
            super(itemView);


            //find the item id
            image = itemView.findViewById(R.id.new_image);



        }
    }

}
