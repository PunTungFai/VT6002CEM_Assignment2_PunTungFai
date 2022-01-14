package com.example.vt6002cem_assignment2.Sign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vt6002cem_assignment2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    //Firbase base
    private FirebaseAuth mAuth;

    //Set user input
    private EditText email,name,phone,password,confirmpassword;

    //image back
    private ImageView back_icon;

    //Button signin
    private Button button_signin;

    //Progress Bar
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        back_icon = findViewById(R.id.back_icon);
        back_icon.setOnClickListener(this);

        button_signin = findViewById(R.id.button_signin);
        button_signin.setOnClickListener(this);






        email = findViewById(R.id.fg_email);
        name = findViewById(R.id.et_username);
        phone = findViewById(R.id.et_phone);
        password = findViewById(R.id.et_password);
        confirmpassword = findViewById(R.id.et_confirm_password);
        progressBar = findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //back button
            case R.id.back_icon:
                finish();
                break;
            //Registration button
            case R.id.button_signin:
                registerUser();
                break;

        }

    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void registerUser() {
        String ipemail = email.getText().toString().trim();
        String ipname = name.getText().toString().trim();
        String ipphone = phone.getText().toString().trim();
        String ippassword = password.getText().toString().trim();
        String ipconfirmpassword = confirmpassword.getText().toString().trim();

        //check email
        if(ipemail.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        //check email pattern
        if(!Patterns.EMAIL_ADDRESS.matcher(ipemail).matches()){
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }
        // check name
        if(ipname.isEmpty()){
            name.setError("Name is required!");
            name.requestFocus();
            return;
        }
        //check iphone
        if(ipphone.isEmpty()){
            password.setError("Full name is required!");
            password.requestFocus();
            return;
        }
        //check password
        if(ippassword.isEmpty()){
            password.setError("password is required!");
            password.requestFocus();
            return;
        }
        //check confirmpassword
        if(ipconfirmpassword.isEmpty()){
            confirmpassword.setError("Confirmpassword is required!");
            confirmpassword.requestFocus();
            return;
        }
        //check password is equals confirmpassword
        if(!ippassword.equals(ipconfirmpassword)){
            password.setError("password not match!");
            password.requestFocus();
            System.out.println(ippassword);
            System.out.println(ipconfirmpassword);
            return;
        }
        if(ippassword.length()<6){
            password.setError("Min password length should be 6 character!");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        //Call Api add the email and passsword to firebase
        mAuth.createUserWithEmailAndPassword(ipemail,ippassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(ipname,ipphone);
                            //Add data to the realtime database
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(SignupActivity.this,"Successfully and check email",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        finish();



                                    }else{
                                        Toast.makeText(SignupActivity.this,"Failed to register!!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);



                                    }
                                }
                            });


                        }else{
                            Toast.makeText(SignupActivity.this,"Failed to register!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }

                    }
                });




    }



}