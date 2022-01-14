package com.example.vt6002cem_assignment2.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vt6002cem_assignment2.R;
import com.example.vt6002cem_assignment2.product.Productdetail.ProductdetailActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.newsviewHolder> implements Filterable {
    private Context context;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Product> products2 = new ArrayList<>();


    public ProductAdapter(ArrayList<Product> products, Context context){
        this.products = products;
        this.products2 = products;

        //set context
        this.context = context;

    }

    @NonNull
    @Override
    public newsviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Add the layout to the listview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_design,parent,false);
        newsviewHolder newsviewHolder = new newsviewHolder(view);
        return newsviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull newsviewHolder holder, int position) {
        Product product = products.get(position);

            //Add the Text
            holder.productname.setText(product.getProductname());
            holder.productprice.setText("$"+product.getProductprice());
            holder.des.setText(product.getDescription());

            //Add Star
            holder.rating.setRating(Float.parseFloat(product.getStar()));

            //Add Image
            Picasso.with(context).load(product.getImg()).into(holder.img);

        if(product.getAr().matches("N")){
            //Set Visibility ar
            holder.ar.setVisibility(View.INVISIBLE);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to the Productdetail page
                Intent intent = new Intent(context, ProductdetailActivity.class);
                //Set the Product
                intent.putExtra("Product", (Serializable) product);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    public class newsviewHolder extends RecyclerView.ViewHolder {
        //Image
        ImageView img, ar;
        //Text
        TextView productname, productprice, des;
        //Rating Bar
        RatingBar rating;
        //Layout
        LinearLayout layout;

        public newsviewHolder(@NonNull View itemView) {
            super(itemView);

            //set the id
            img = itemView.findViewById(R.id.img);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.productprice);
            des = itemView.findViewById(R.id.des);
            rating = itemView.findViewById(R.id.rating);
            ar = itemView.findViewById(R.id.ar);
            layout = itemView.findViewById(R.id.layout);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();

                if(key.isEmpty()){
                    products = products2;
                }else{
                    ArrayList<Product> products3 = new ArrayList<>();
                    for(Product row: products2){
                        if(row.getProductname().toLowerCase().contains(key.toLowerCase())){
                            products3.add(row);
                        }
                    }
                    products = products3;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values= products;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                products = (ArrayList<Product>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
