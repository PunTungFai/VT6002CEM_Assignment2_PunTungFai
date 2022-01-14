package com.example.vt6002cem_assignment2.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vt6002cem_assignment2.R;

import java.util.ArrayList;

public class HomepageFragment extends Fragment {

    private RecyclerView eventRecycler, newRecycler;
    private RecyclerView.Adapter adapter, adapter2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=  inflater.inflate(R.layout.fragment_homepage,container, false);
        newRecycler  = view.findViewById(R.id.news_recycler);
        eventRecycler = view.findViewById(R.id.event_recycler);
        //Call Adapter News product
        NewRecycler();

        //Call Adapter Event
        EventRecycler();



        return view;
    }

    private void EventRecycler() {
        eventRecycler.setHasFixedSize(true);
        eventRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        ArrayList<NewInformation> events = new ArrayList<>();
        //Add data
        events.add(new NewInformation(R.drawable.happynewyear));
        events.add(new NewInformation(R.drawable.discount));
        events.add(new NewInformation(R.drawable.buy1get1));

        adapter2 = new NewInformationAdapter(events);
        eventRecycler.setAdapter(adapter2);



    }

    private void NewRecycler() {
        newRecycler.setHasFixedSize(true);
        newRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        ArrayList<NewProduct> news = new ArrayList<>();
        //Add data
        news.add(new NewProduct(R.drawable.menclothes,getResources().getString(R.string.Men_clothes),getResources().getString(R.string.Good)));
        news.add(new NewProduct(R.drawable.womenclothes,getResources().getString(R.string.Women_clothes),getResources().getString(R.string.Good)));
        news.add(new NewProduct(R.drawable.kidclothes,getResources().getString(R.string.Kid_clothes),getResources().getString(R.string.Good)));

        adapter = new NewProductAdapter(news);
        newRecycler.setAdapter(adapter);




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}