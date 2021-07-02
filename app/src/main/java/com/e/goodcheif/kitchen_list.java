package com.e.goodcheif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.goodcheif.adapter.KitchenAdapter;
import com.e.goodcheif.adapter.LoadingAdapter;
import com.e.goodcheif.data.Kitchen_Item;
import com.e.goodcheif.shop.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class kitchen_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_list);
        identity();
        get_data();
        getDetails();
        setAdapter();
    }

    private void setAdapter() {
        final List <Kitchen_Item>list= new ArrayList<>();
        FirebaseDatabase.getInstance().getReference()
                .child("Kitchen").orderByChild("type").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Kitchen_Item item=new Kitchen_Item();
                    item.setUrl(dataSnapshot.getKey());
                    item.setTitle(dataSnapshot.child("title").getValue().toString());
                    item.setOffer(dataSnapshot.child("offer").getValue().toString());
                    item.setPrice(dataSnapshot.child("price").getValue().toString());
                    item.setType(dataSnapshot.child("type").getValue().toString());
                    list.add(item);
                }

                setRecycler(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    String id,details;
    TextView title ,about;
    ImageView imageView;
    private void identity(){
        title=findViewById(R.id.textView17);
        about=findViewById(R.id.textView18);
        imageView=findViewById(R.id.imageView);
        on_loading();
    }
    private void getDetails(){
        title.setText(id);
        about.setText(details);
        List<Item>data=  Arrays.asList(
                new Item(7, "Vegetables", "", R.drawable.shop8)
                , new Item(8, "Cake", "", R.drawable.shop6),
                new Item(4, "Cheese", "", R.drawable.shop2),
                new Item(5, "The bread", "", R.drawable.shop3),
                new Item(1, "Kitchen  tools", "", R.drawable.shop4),
                new Item(2, "Seasoning", "", R.drawable.shop5),
                new Item(6, "Fruits", "", R.drawable.shop7));
        for(Item item :data)
            if(id.equals(item.getName()))
                imageView.setImageResource(item.getImage());
    }
    private void get_data(){
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        details = intent.getStringExtra("about");
    }
    private void setRecycler(List<Kitchen_Item> FoodList) {

        RecyclerView asiaRecycler = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                RecyclerView.VERTICAL, false);
        asiaRecycler.setLayoutManager(layoutManager);
        KitchenAdapter Adapter = new KitchenAdapter(getApplicationContext(), FoodList);
        asiaRecycler.setAdapter(Adapter);

    }
    private void on_loading(){
        RecyclerView Recycler = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                RecyclerView.VERTICAL, false);
        Recycler.setLayoutManager(layoutManager);

        LoadingAdapter load = new LoadingAdapter(getApplicationContext(),1,1);
        Recycler.setAdapter(load);
    }
}
