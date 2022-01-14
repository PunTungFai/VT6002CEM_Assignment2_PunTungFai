package com.example.vt6002cem_assignment2.order_record;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.vt6002cem_assignment2.R;
import com.example.vt6002cem_assignment2.shoppingcart.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecordFragment extends Fragment {
    //Get user id
    private FirebaseUser user;

    //Save userid
    private String userid;

    //Save data
    private DatabaseReference data;

    //Arraylist save productorder
    private ArrayList<Record> productListorder = new ArrayList<>();
    private ArrayList<Record> productListorder2 = new ArrayList<>();

    //Order Amount
    List<String> orderdate = new ArrayList<>();
    List<String> orderAmount = new ArrayList<>();

    //Listview
    ExpandableListView ListView;
    ExpandableListAdapter ListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=  inflater.inflate(R.layout.fragment_record,container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        data = FirebaseDatabase.getInstance().getReference().child("Order").child(userid);

        ListView= view.findViewById(R.id.expandableListView);


        orderrecord();

        return view;
    }
    //get the orderrecord
    private void orderrecord() {
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productListorder.clear();
                productListorder2.clear();
                orderdate.clear();
                orderAmount.clear();
                for(DataSnapshot datas: snapshot.getChildren()){

                    Record adddata = new Record();
                    adddata.setDatekey(datas.getKey());
                    for (DataSnapshot ds : datas.getChildren()){
                        Order adddata2 = ds.getValue(Order.class);
                        adddata2.setDatekey(ds.getKey());
                        adddata.order.add(adddata2);
                    }
                    productListorder.add(adddata);




                }

                for(int i=productListorder.size()-1;i>=0;--i){

                    productListorder2.add(productListorder.get(i));

                }

                for(int i=0;i<productListorder2.size();i++){
                    orderdate.add(productListorder2.get(i).getDatekey());
                    orderAmount.add(Double.toString(productListorder2.get(i).getamount()));
                }




                ListAdapter = new RecordAdapter(getContext(),orderdate,orderAmount,productListorder2);
                ListView.setAdapter(ListAdapter);








            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}