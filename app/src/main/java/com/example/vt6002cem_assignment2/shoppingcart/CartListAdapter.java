package com.example.vt6002cem_assignment2.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.vt6002cem_assignment2.R;
import com.example.vt6002cem_assignment2.product.Productdetail.ShoppingCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.cartviewHolder> {

    //Get context
    private Context context;

    //Shopping Cart item
    private ArrayList<ShoppingCart> cartHelperClasses = new ArrayList<>();

    //Firesebase user
    private FirebaseUser user;
    private String userid;

    //Database get data
    private DatabaseReference data;

    public CartListAdapter(ArrayList<ShoppingCart> cartHelperClasses, Context context){
        this.cartHelperClasses=cartHelperClasses;
        this.context= context;



    }

    @NonNull
    @Override
    public cartviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppingcart_card_design,parent,false);
        cartviewHolder cartviewHolder = new cartviewHolder(view);

        return cartviewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull cartviewHolder holder, int position) {
        ShoppingCart cart = cartHelperClasses.get(position);

        //set text
        holder.name.setText(cart.getProductname());
        holder.price.setText("$"+cart.getProductprice());
        holder.size.setText(cart.getSize());

        //set image
        Picasso.with(context).load(cart.getImg()).into(holder.img);

        //set qunantity
        holder.num.setNumber(cart.getQuantity());
        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        data = FirebaseDatabase.getInstance().getReference().child("ShoppingCart").child(userid);

        //Add new quantity
        holder.num.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                System.out.println(cart.getKey());
                System.out.println(cart.getSubkey());
                data.child(cart.getKey()).child(cart.getSubkey()).child("quantity").setValue(holder.num.getNumber());




            }
        });
        //Delete the item
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("ShoppingCart").child(userid).child(cart.getKey()).child(cart.getSubkey());
                reference.removeValue();
                notifyItemRangeChanged(position, cartHelperClasses.size());
                cartHelperClasses.remove(position);
                notifyItemRemoved(position);





            }
        });




    }

    @Override
    public int getItemCount() {
        return cartHelperClasses.size();
    }

    public class cartviewHolder extends RecyclerView.ViewHolder {
        //Set image var
        ImageView del;
        ImageView img;
        //Set text var
        TextView name,price,size;
        //Set button num
        ElegantNumberButton num;

        public cartviewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.product_image);
            del=itemView.findViewById(R.id.delete);
            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            size=itemView.findViewById(R.id.product_size);
            num=itemView.findViewById(R.id.txt_count);

        }
    }
}
