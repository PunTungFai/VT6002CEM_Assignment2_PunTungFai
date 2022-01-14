package com.example.vt6002cem_assignment2.Sign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vt6002cem_assignment2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetpasswordActivity extends AppCompatActivity implements View.OnClickListener{
    //reture page button
    private ImageView back_icon;

    //Enter forget password button
    private Button Enter;

    // laoding bar
    private ProgressBar progressBar;

    //user input email
    private TextView Email;

    //Firebase
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        back_icon = findViewById(R.id.back_icon);
        back_icon.setOnClickListener(this);

        Enter = findViewById(R.id.button_Enter);
        Enter.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        Email=findViewById(R.id.fg_email);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //back button
            case R.id.back_icon:
                finish();
                break;
             //enter button
            case R.id.button_Enter:
                Forget();
                break;

        }
    }

    private void Forget() {
        String ipemail = Email.getText().toString().trim();

        // check email is not empty
        if(ipemail.isEmpty()){
            Email.setError("Email is required!");
            Email.requestFocus();
            return;
        }

        //call the Api forget password
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(ipemail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Try again! Something wrong happened!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}