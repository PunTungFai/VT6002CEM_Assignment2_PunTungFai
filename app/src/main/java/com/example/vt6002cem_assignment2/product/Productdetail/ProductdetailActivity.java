package com.example.vt6002cem_assignment2.product.Productdetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.vt6002cem_assignment2.ArCore.ArcoreActivity;
import com.example.vt6002cem_assignment2.R;
import com.example.vt6002cem_assignment2.product.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductdetailActivity extends AppCompatActivity {
    private Product product;


    //Save size item
    ArrayList<String> addsize = new ArrayList<String>();



    //Add product to the user shopping Carts
    ArrayList<ShoppingCart> shoppingCarts = new ArrayList<>();
    ArrayList<ShoppingCart> shoppingCarts2 = new ArrayList<>();

    //Set Text
    private TextView productname,productprice,color,Material,Description,star;

    //Set Image
    private ImageView img,ar;

    //Set spinner text
    private Spinner size;

    //Set Userid
    private String userid="";

    //Firebase
    private DatabaseReference databaseReference;

    //Button Add
    private Button button;

    // Firebase  user
    private FirebaseUser user;

    // Product Quantity
    private ElegantNumberButton num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);

        productname = findViewById(R.id.productname);
        productprice = findViewById(R.id.productprice);
        color = findViewById(R.id.productcolor);
        Material = findViewById(R.id.productmaterial);
        Description = findViewById(R.id.productdescription);
        img = findViewById(R.id.product_image);
        size =findViewById(R.id.productsize);
        ar = findViewById(R.id.productar);
        star = findViewById(R.id.star);
        button = findViewById(R.id.btn);
        num = findViewById(R.id.txt_count);

        Intent i = getIntent();
        product = (Product) i.getSerializableExtra("Product");

        addsize=getsize(product.getSize());
        productname.setText(product.getProductname());
        productprice.setText("$"+product.getProductprice());
        color.setText(product.getColor());
        Material.setText(product.getMaterial());
        Description.setText(product.getDescription());
        star.setText(product.getStar());
        Picasso.with(this).load(product.getImg()).into(img);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user !=null){
            userid = user.getUid();
        }else{
            userid ="";
        }


        //check ar
        if(product.getAr().matches("Y")){
            ar.setVisibility(View.VISIBLE);
            ar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ArcoreActivity.class);
                    intent.putExtra("ProductAR", (Serializable) product);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            });
        }else{
            ar.setVisibility(View.INVISIBLE);
        }

        //Add the size to the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, addsize);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        size.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ShoppingCart").child(userid).child(product.getKey());


        productdata();


        //Add Onclick
        Add();








    }

    private void Add() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userid == ""){
                    Toast.makeText(getApplicationContext(),"Login First",Toast.LENGTH_LONG).show();
                }else{
                    productdata();
                    //Message box ask the user
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ProductdetailActivity.this);
                    dialog.setTitle("Add Item");
                    dialog.setMessage("Do you want add the Item to the Shopping Cart?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            orderadd();
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
            }
        });
    }
    //Get the product information
    private void productdata() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shoppingCarts.clear();
                for(DataSnapshot datas: snapshot.getChildren()){

                    ShoppingCart detail = datas.getValue(ShoppingCart.class);
                    detail.setKey(datas.getKey());
                    shoppingCarts.add(detail);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //Add the product to the shopping cart
    private void orderadd() {

        for(int i=0;i<shoppingCarts.size();i++){
            if(shoppingCarts.get(i).getSize().matches(size.getSelectedItem().toString())){
                shoppingCarts2.clear();
                shoppingCarts2.add(shoppingCarts.get(i));
            }

        }
        if(shoppingCarts2.size()>0){
            int Quantity=Integer.parseInt(shoppingCarts2.get(0).getQuantity());
            int B=Integer.parseInt(num.getNumber());
            int C=B+Quantity;

            databaseReference.child(shoppingCarts2.get(0).getKey()).child("quantity").setValue(String.valueOf(C));
            System.out.println("AAA");
            shoppingCarts2.clear();
            num.setNumber("1");
            Toast.makeText(this,"Add Successful",Toast.LENGTH_LONG).show();
        }else{
            String key = databaseReference.push().getKey();

            ShoppingCart cart = new ShoppingCart(product.getColor(),product.getImg(),product.getProductname(),product.getProductprice(),product.getProducttype(),num.getNumber(),size.getSelectedItem().toString());
            databaseReference.child(key).setValue(cart);
            num.setNumber("1");
            Toast.makeText(this,"Add Successful",Toast.LENGTH_LONG).show();

        }


    }

    private ArrayList<String> getsize(String size) {
        ArrayList<String> sizelist = new ArrayList<String>();

        for (String retval: size.split(",")){
            sizelist.add(retval);
        }
        return sizelist;
    }

    //OnClick button back page
    public void backbutton(View view) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}