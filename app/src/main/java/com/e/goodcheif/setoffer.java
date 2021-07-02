package com.e.goodcheif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.e.goodcheif.adapter.ClintAdapter;
import com.e.goodcheif.adapter.offerAdapter;
import com.e.goodcheif.data.Delivery;
import com.e.goodcheif.model.PopularFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class setoffer extends AppCompatActivity {

    private RecyclerView list;
    private List<PopularFood> Items;
    private List<String> id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setoffer);

        setId();
        setList();
    }
    private void setId(){
        Items=new ArrayList<>();
        id=new ArrayList<>();
        list=findViewById(R.id.list_recycler);
    }
    private void setList(){
        FirebaseDatabase.getInstance().getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    id.add(dataSnapshot.getKey());
                    PopularFood pp=new PopularFood();
                    pp.setName(dataSnapshot.child("name").getValue().toString());
                    pp.setPrice(dataSnapshot.child("price").getValue().toString());
                    pp.setOffer(dataSnapshot.child("offer").getValue().toString());
                    pp.setImageUrl(Integer.parseInt(dataSnapshot.child("imageUrl").getValue().toString()));
                    Items.add(pp);
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

        offerAdapter offadapter=new offerAdapter(getContext(),Items,id);
        list.setAdapter(offadapter);

    }

    private Context getContext() {
        return this;
    }
}
