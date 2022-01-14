package com.example.vt6002cem_assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vt6002cem_assignment2.Sign.User;
import com.example.vt6002cem_assignment2.Sign.LoginFragment;
import com.example.vt6002cem_assignment2.home.HomepageFragment;
import com.example.vt6002cem_assignment2.order_record.RecordFragment;
import com.example.vt6002cem_assignment2.product.ProductFragment;
import com.example.vt6002cem_assignment2.profile.ProfileFragment;
import com.example.vt6002cem_assignment2.shoppingcart.ShoppingcartActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //Variables
    static final float END_SCALE = 0.7f;

    //Items
    ImageView menuIcon;
    LinearLayout contentView;
    TextView title;

    //Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    //firebase
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID= "";
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadLocale();
        setContentView(R.layout.activity_home);


        //Item Hooks
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        title = findViewById(R.id.title);




        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.menu_slogan);


        //Hide or show items
        Menu menu = navigationView.getMenu();


        //Uses the shared Preferences
        userID = getSharedPreferences("data",MODE_PRIVATE).getString("Uid","");

        if(userID != ""){
            menu.findItem(R.id.nav_login).setVisible(false);
            reference = FirebaseDatabase.getInstance().getReference("users");

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if(userProfile !=null){
                        String fullname = userProfile.getName();

                        name.setText(fullname);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
        }


        naviationDrawer();
        defaulthome();



    }

    private void defaulthome() {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragment = new HomepageFragment();
        ft.replace(R.id.flContent, fragment);
        ft.commit();

    }

    //Navigation Drawer Functions
    private void naviationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNaviationDrawer();
    }
    //Navigation Drawer Animation
    private void animateNaviationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.yellow));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                final float diffScaledOffset = slideOffset*(1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final float xoffset = drawerView.getWidth()*slideOffset;
                final float xoffsetDiff = contentView.getWidth()*diffScaledOffset/2;
                final float xTranslation = xoffset - xoffsetDiff;
                contentView.setTranslationX(xTranslation);


            }
        });


    }

    @Override
    public void onBackPressed() {
        //Navigation Drawer close
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.nav_home:
                fragment = new HomepageFragment();
                title.setText(R.string.home);
                break;
            case R.id.nav_product:
                fragment = new ProductFragment();
                title.setText(R.string.product);
                break;
            case R.id.nav_record:
                if(userID=="") {
                    fragment = new LoginFragment();
                    title.setText(R.string.login);
                    Toast.makeText(this,"Login First",Toast.LENGTH_LONG).show();
                    break;
                }else{
                    fragment = new RecordFragment();
                    title.setText(R.string.Record);
                    break;
                }
            case R.id.nav_login:
                fragment = new LoginFragment();
                title.setText(R.string.login);
                break;
            case R.id.nav_logout:
                SharedPreferences settings = this.getSharedPreferences("data", Context.MODE_PRIVATE);
                settings.edit().remove("Uid").commit();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                Toast.makeText(this,"Logout successfully",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                title.setText(R.string.Profile);
                break;
            case R.id.nav_setting:
                showStartDialog();
                break;

        }

        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }


    //Set the langauge
    private void showStartDialog() {
        final String[] listItems = {"中文", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("選擇語言 Choose Language");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("zh");
                    finish();
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));

                }
                if (which == 1) {
                    setLocale("en");
                    finish();
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));

                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Onclick goto the cart
    public void cart(View view) {

        if(userID==""){
            title.setText(R.string.Login);
            Fragment fragment = null;
            fragment = new LoginFragment();
            Toast.makeText(this,"Login First",Toast.LENGTH_LONG).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


        }else {
            Intent intent = new Intent(this, ShoppingcartActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }

    }

    // set  the setlocale
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Setting", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }
    //when you open the app, it will be use your choose language before.
    public void LoadLocale() {
        SharedPreferences preferences = getSharedPreferences("Setting", MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
    }



}