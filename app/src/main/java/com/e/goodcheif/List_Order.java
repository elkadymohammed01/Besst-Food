package com.e.goodcheif;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ActionMode;

import com.e.goodcheif.adapter.OrderAdapter;
import com.e.goodcheif.adapter.PopularFoodAdapter;
import com.e.goodcheif.data.Item_Cart;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class
List_Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__order);
        api_adapter();
    }
    List<String> id=new ArrayList<>();
    List<List<Item_Cart>>lists=new ArrayList<>();
    private void setRecyclerAdapter(){
        RecyclerView list =findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        OrderAdapter orderAdapter = new OrderAdapter(getApplicationContext(),lists, id);
        list.setAdapter(orderAdapter);
    }
    private void api_adapter(){

        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("Order");
        query=dbr;
        listener= query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                id.add(snapshot.getKey());
                List<Item_Cart>item=new ArrayList<>();
                for(DataSnapshot ds:snapshot.getChildren()){
                    Item_Cart it= ds.getValue(Item_Cart.class);
                    item.add(it);
                }
                lists.add(item);
                setRecyclerAdapter();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    ChildEventListener listener;
    Query query;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        query.removeEventListener(listener);
    }
}
