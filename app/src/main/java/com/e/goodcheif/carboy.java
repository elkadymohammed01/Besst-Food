package com.e.goodcheif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.e.goodcheif.adapter.CartAdapter;
import com.e.goodcheif.adapter.ClintAdapter;
import com.e.goodcheif.adapter.LoadingAdapter;
import com.e.goodcheif.data.Delivery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class carboy extends AppCompatActivity {

    private RecyclerView list;
    private List<Delivery> Items;
    private List<String> id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carboy);
        setId();
        setList();
        on_loading();
    }
    private void setId(){
        Items=new ArrayList<>();
        id=new ArrayList<>();
        list=findViewById(R.id.list_recycler);
    }
    private void setList(){
        FirebaseDatabase.getInstance().getReference().child("Delivery").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    id.add(dataSnapshot.getKey());
                    Delivery delivery=dataSnapshot.getValue(Delivery.class);
                    Items.add(delivery);
                }
                setRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setRecycler() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        list.setLayoutManager(layoutManager);

        ClintAdapter clintAdapter = new ClintAdapter(getContext(),Items,id);
        list.setAdapter(clintAdapter);

    }
    private void on_loading(){
        RecyclerView Recycler = findViewById(R.id.list_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                RecyclerView.VERTICAL, false);
        Recycler.setLayoutManager(layoutManager);

        LoadingAdapter load = new LoadingAdapter(getApplicationContext(),1,1);
        Recycler.setAdapter(load);
    }
    private Context getContext() {
        return this;
    }
}
