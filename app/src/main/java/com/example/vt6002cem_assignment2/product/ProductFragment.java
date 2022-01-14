package com.example.vt6002cem_assignment2.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vt6002cem_assignment2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    //RecyclerView
    private RecyclerView productRecycler;
    private ProductAdapter adapter;

    //Model Product Arraylist
    private ArrayList<Product> listproduct = new ArrayList<>();
    private ArrayList<Product> listproduct2 = new ArrayList<>();

    //Firebase Database Reference
    private DatabaseReference reference;

    //Spinner choose item
    private Spinner spinner;

    //Array Adapter
    private ArrayAdapter<CharSequence> spad;

    //Search item
    private SearchView searchView;

    //Search item
    private ImageView mic;

    private int SPEECH_REQUEST_CODE = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=  inflater.inflate(R.layout.fragment_product,container, false);
        productRecycler = view.findViewById(R.id.product_Listview);
        searchView = view.findViewById(R.id.search_bar);
        mic = view.findViewById(R.id.mic);


        //set spinner item
        spinner = view.findViewById(R.id.spinner);
        spad = ArrayAdapter.createFromResource(getContext(),R.array.item,android.R.layout.simple_spinner_item);
        spad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spad);
        spinner.setOnItemSelectedListener(this);



        ProductRecycler();


        Search();

        //speech recongnition
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something");
                try {
                    startActivityForResult(intent, SPEECH_REQUEST_CODE);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });










        return view;
    }


    private void Search() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //call the filter function for user input productname
                adapter.getFilter().filter(s.toString());
                return true;
            }
        });
    }

    private void ProductRecycler() {
        productRecycler.setHasFixedSize(true);
        productRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        //Call firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Product");

        //Get the firebase data
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listproduct.clear();
                listproduct2.clear();
                for(DataSnapshot datas: snapshot.getChildren()){
                    Product product = datas.getValue(Product.class);
                    product.setKey(datas.getKey());
                    listproduct.add(product);
                }

                String spinnertext = spinner.getSelectedItem().toString();

                //Select the product type
                if("All".toLowerCase().matches(spinnertext.toLowerCase())){
                    //set adapter
                    adapter = new ProductAdapter(listproduct, getContext());
                    productRecycler.setAdapter(adapter);
                }else{

                    for(Product object: listproduct){
                        if(object.getProducttype().toLowerCase().matches(spinnertext.toLowerCase())){
                            listproduct2.add(object);

                        }
                    }
                    //set adapter
                    adapter = new ProductAdapter(listproduct2, getContext());
                    productRecycler.setAdapter(adapter);

                }


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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //get the spinner item
        String spinnertext = adapterView.getItemAtPosition(i).toString();
        System.out.println(spinnertext);
        listproduct2.clear();

        //Select the product type
        if("All".toLowerCase().matches(spinnertext.toLowerCase())){
            adapter = new ProductAdapter(listproduct,getContext());
            productRecycler.setAdapter(adapter);

        }else{
            //Get the product type
            for(Product object:listproduct){
                if(object.getProducttype().toLowerCase().matches(spinnertext.toLowerCase())){
                    listproduct2.add(object);
                }
            }
            adapter = new ProductAdapter(listproduct2, getContext());
            productRecycler.setAdapter(adapter);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && null != data) {
                //
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                searchView.setQuery(result.get(0), false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}