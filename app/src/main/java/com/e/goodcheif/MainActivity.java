package com.e.goodcheif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.e.goodcheif.data.StringModePrefManager;
import com.e.goodcheif.data.myDbAdapter;
public class MainActivity extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StringModePrefManager ModeManager = new StringModePrefManager(this);
        ModeManager.setDarkMode(!ModeManager.isNightMode());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recreate();
        myDbAdapter DB =new myDbAdapter(getApplicationContext());
        if(DB.getData().length()>10){

            finish();
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
            
        }
        else{

            finish();
            startActivity(new Intent(MainActivity.this,Log_page.class));}
        image=findViewById(R.id.mainImage);
        image.animate().translationY(-700).alpha(1).setDuration(0).setStartDelay(0).start();
        image.animate().translationY(0).alpha(2).setDuration(1500).setStartDelay(500).start();


    }

}
