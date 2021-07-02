package com.e.goodcheif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.e.goodcheif.adapter.BookingAdapter;
import com.e.goodcheif.adapter.CartAdapter;
import com.e.goodcheif.adapter.LoadingAdapter;
import com.e.goodcheif.data.Item_Cart;
import com.e.goodcheif.data.book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingMangment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_mangment);
        getRecyclerData();
        on_loading();
    }
    private void getRecyclerData(){
        long appoint=(System.currentTimeMillis()/86400000)*86400000;
        FirebaseDatabase.getInstance().getReference().child("Appointment").orderByChild("appoint").equalTo(appoint).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String>ids=new ArrayList<>();
                ArrayList<book>list=new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                book booking =dataSnapshot.getValue(book.class);
                String id=dataSnapshot.getKey();
                ids.add(id);list.add(booking); }
                setRecycler(list,ids);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setRecycler(List<book> list, List<String>id) {

        RecyclerView RecyclerTime = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        RecyclerTime.setLayoutManager(layoutManager);

        BookingAdapter adpter = new BookingAdapter(this, list,id);
        RecyclerTime.setAdapter(adpter);

    }
    private void on_loading(){
        RecyclerView Recyclerloding = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                RecyclerView.VERTICAL, false);
        Recyclerloding.setLayoutManager(layoutManager);

        LoadingAdapter load = new LoadingAdapter(getApplicationContext(),1,1);
        Recyclerloding.setAdapter(load);
    }
}
