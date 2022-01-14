package com.example.vt6002cem_assignment2.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vt6002cem_assignment2.R;
import com.example.vt6002cem_assignment2.product.Productdetail.ShoppingCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ShoppingcartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartListAdapter adapter;
    private FirebaseUser user;
    private String userid;
    private DatabaseReference data,data2,del;
    private ArrayList<com.example.vt6002cem_assignment2.product.Productdetail.ShoppingCart> cartadd =new ArrayList<>();
    private ArrayList<com.example.vt6002cem_assignment2.product.Productdetail.ShoppingCart>  order=new ArrayList<>();
    private double total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        cartRecyclerView = findViewById(R.id.cartListview);

        //Set the firebase set data local
        data = FirebaseDatabase.getInstance().getReference().child("ShoppingCart").child(userid);
        getdata();

    }
        //Get the data display the shopping cart layout
    private void getdata() {
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartadd.clear();
                for(DataSnapshot datas: snapshot.getChildren()){
                    for(DataSnapshot ds : datas.getChildren()){
                        ShoppingCart cart = ds.getValue(ShoppingCart.class);
                        cart.setKey(datas.getKey());
                        cart.setSubkey(ds.getKey());
                        cartadd.add(cart);


                    }




                }
                cartRecyclerView.setHasFixedSize(true);
                cartRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                Collections.reverse(cartadd);
                adapter = new CartListAdapter(cartadd,getApplicationContext());
                cartRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // back button
    public void backbutton(View view) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void order(View view) {

        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //reset the total
                total=0;
                order.clear();

                //get data
                for(DataSnapshot datas: snapshot.getChildren()){
                    for(DataSnapshot ds : datas.getChildren()){
                        ShoppingCart cart = ds.getValue(ShoppingCart.class);
                        cart.setKey(datas.getKey());
                        cart.setSubkey(ds.getKey());
                        order.add(cart);
                        total += (Integer.parseInt(cart.getQuantity()) * Double.parseDouble(cart.getProductprice()));
                    }




                }
                //AlerDialog display message
                AlertDialog.Builder dialog = new AlertDialog.Builder(ShoppingcartActivity.this);
                dialog.setTitle("Add Item");
                dialog.setMessage("Total Amount"+total+"  Order the product?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (order.size()>0){
                            orderadd();
                        }else {
                            Toast.makeText(getApplicationContext(),"the ShoppingCart not item",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create();
                dialog.show();







            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
    //Add to orderlist to database
    private void orderadd() {
        data2 = FirebaseDatabase.getInstance().getReference().child("Order").child(userid);

        for(int i=0;i<order.size();i++){
            ShoppingCart cart = order.get(i);
            Order addToorderlist = new Order(cart.getColor(),cart.getImg(),cart.getProductname(),cart.getProductprice(),cart.getProducttype(),cart.getQuantity(),cart.getSize(),cart.getKey());
            SimpleDateFormat dataformat = new SimpleDateFormat();
            dataformat.applyPattern("yyyyMMdd_HH:mm:ss");
            data2.child(dataformat.format(new Date())).child(order.get(i).getSubkey()).setValue(addToorderlist);






        }

        deletecart();


    }
    //clear the shopping cart
    private void deletecart() {
        del = FirebaseDatabase.getInstance().getReference().child("ShoppingCart").child(userid);
        del.removeValue();
        cartadd.clear();
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new CartListAdapter(cartadd,getApplicationContext());
        cartRecyclerView.setAdapter(adapter);

        Toast.makeText(this,"Order Successful",Toast.LENGTH_LONG).show();
    }
}