package com.example.vt6002cem_assignment2.Sign;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vt6002cem_assignment2.HomeActivity;
import com.example.vt6002cem_assignment2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment implements View.OnClickListener{

    //Set onclik
    private Button button,signin,forgetpassword;

    //set user input
    private EditText Email,password;

    //progressBar for loding
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=  inflater.inflate(R.layout.fragment_login,container, false);

        button = view.findViewById(R.id.button_signup);
        button.setOnClickListener(this);

        signin = view.findViewById(R.id.button_signin);
        signin.setOnClickListener(this);

        forgetpassword = view.findViewById(R.id.button_forgot_password);
        forgetpassword.setOnClickListener(this);


        Email = view.findViewById(R.id.fg_email);
        password = view.findViewById(R.id.et_password);

        progressBar = view.findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Onclick signup
            case R.id.button_signup:
                signup();
                break;
            //Onclick signin
            case R.id.button_signin:
                Loginfunction();
                break;
            //Onclick forget password
            case R.id.button_forgot_password:
                Forgetpassword();
                break;


        }
    }

    private void signup() {
        Intent intent = new Intent(getContext(), SignupActivity.class);
        getContext().startActivity(intent);
        ((Activity)getContext()).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


    private void Loginfunction(){

        String ipemail = Email.getText().toString().trim();
        String ippassword = password.getText().toString().trim();

        //check email
        if(ipemail.isEmpty()){
            Email.setError("Email is required!");
            Email.requestFocus();
            return;
        }//check email pattern
        if(!Patterns.EMAIL_ADDRESS.matcher(ipemail).matches()){
            Email.setError("Please provide valid email!");
            Email.requestFocus();
            return;
        }//check password
        if(ippassword.isEmpty()){
            password.setError("password is required!");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        //firebase login
        mAuth.signInWithEmailAndPassword(ipemail,ippassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //user is true, would login successfully
                    if(user.isEmailVerified()) {
                        //Set sharedPreferences
                        SharedPreferences pref = getActivity().getSharedPreferences("data", MODE_PRIVATE);
                        pref.edit().putString("Uid",user.getUid()).commit();
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                        Toast.makeText(getContext(),"Login successfully",Toast.LENGTH_LONG).show();




                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(getContext(),"Check your email to verify you account!",Toast.LENGTH_LONG).show();
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        progressBar.setVisibility(View.GONE);
                    }


                }else{
                    //Check your email
                    Toast.makeText(getContext(),"Failed to login! Please check you credentials",Toast.LENGTH_LONG).show();
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void Forgetpassword(){
        Intent intent = new Intent(getContext(), ForgetpasswordActivity.class);
        getContext().startActivity(intent);
        ((Activity)getContext()).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


}