package com.e.goodcheif;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.e.goodcheif.data.myDbAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Main2Activity extends AppCompatActivity {

    FloatingActionButton main,food,kit,offer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        identity();
    }

    boolean show=false;
    private void identity(){
        if(getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION )==
                PackageManager.PERMISSION_GRANTED){

        }else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        main=findViewById(R.id.floatingActionButton);
        food=findViewById(R.id.add_food);
        kit=findViewById(R.id.add_kit);
        offer=findViewById(R.id.add_offer);
       on_add();
    }
    private void on_add(){
        myDbAdapter DB =new myDbAdapter(getApplicationContext());
        if(!DB.getData_inf()[1].toLowerCase().equals("elkadymohammed01@gmail.com")&&
                !DB.getData_inf()[1].toLowerCase().equals("elkadymohammed00@gmail.com")&&
                !DB.getData_inf()[1].toLowerCase().equals("elkadymohammed02@gmail.com")){
            main.setVisibility(View.INVISIBLE);
            food.setVisibility(View.INVISIBLE);
            kit.setVisibility(View.INVISIBLE);
            offer.setVisibility(View.INVISIBLE);
        }
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show){
                    ObjectAnimator.ofFloat(v, "rotation", 90f, 675f).start();
                    food.animate().translationY(-100).translationX(-100).setDuration(500).start();
                    kit.animate().translationY(-100).translationX(100).setDuration(500).start();
                    offer.animate().translationY(-130).setDuration(500).start();
                }
                else{

                    ObjectAnimator.ofFloat(v, "rotation", 90f, 1080f).start();
                    food.animate().translationY(0).translationX(0).setDuration(500).start();
                    kit.animate().translationY(0).translationX(0).setDuration(500).start();
                    offer.animate().translationY(0).setDuration(500).start();
                }
                show=!show;
            }
        });
    }

    public void Kit(View view) {
        startActivity(new Intent(getApplicationContext(), Add_Kitchen.class));
    }

    public void Food(View view) {
        startActivity(new Intent(getApplicationContext(), AddProdactions.class));
    }

    public void offer(View view) {
        startActivity(new Intent(getApplicationContext(), setoffer.class));
    }
}
