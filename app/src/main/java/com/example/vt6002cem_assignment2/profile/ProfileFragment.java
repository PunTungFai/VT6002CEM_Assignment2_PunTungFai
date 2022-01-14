package com.example.vt6002cem_assignment2.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vt6002cem_assignment2.R;
import com.example.vt6002cem_assignment2.Sign.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    //firebase user
    private FirebaseUser user;
    //connect firebase and get data
    private DatabaseReference reference;
    private String userID;
    //connect firebase and set data
    private DatabaseReference mDatabase;
    //get and set text
    private TextView username, phone;
    //confirm button
    private Button btConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //set id
        username = view.findViewById(R.id.et_username);
        phone = view.findViewById(R.id.et_phone);
        btConfirm = view.findViewById(R.id.button_Confirm);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile !=null){
                    String fullname = userProfile.getName();
                    String phoneNumber = userProfile.getPhone();

                    username.setText(fullname);
                    phone.setText(phoneNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update the profile data
                confirm();
            }
        });

        return view;
    }

    private void confirm() {
        String ipusername = username.getText().toString().trim();
        String ipphone = phone.getText().toString().trim();

        if(ipusername.isEmpty()){
            username.setError("Name is required!");
            username.requestFocus();
            return;
        }
        if(ipphone.isEmpty()){
            phone.setError("Phone is required!");
            phone.requestFocus();
            return;
        }
        mDatabase.child("users").child(userID).child("name").setValue(ipusername);
        mDatabase.child("users").child(userID).child("phone").setValue(ipphone);
        Toast.makeText(getContext(),"Update successful^^",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}