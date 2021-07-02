package com.e.goodcheif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.e.goodcheif.adapter.CartAdapter;
import com.e.goodcheif.adapter.LoadingAdapter;
import com.e.goodcheif.adapter.NoteAdapter;
import com.e.goodcheif.data.Item_Cart;
import com.e.goodcheif.data.Note;
import com.e.goodcheif.data.myDbAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {
    ArrayList<Note>noteList=new ArrayList<>();
    ArrayList<String>id=new ArrayList<>();
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setId();
        on_loading();
        get_email();
        setNoteList();
    }
    private void setId(){
        list=findViewById(R.id.list_recycler);
    }
    private void setNoteList(){
        FirebaseDatabase.getInstance().getReference().child("Note")
                .child(email.substring(0,email.length()-4)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    id.add(dataSnapshot.getKey());
                    noteList.add(dataSnapshot.getValue(Note.class));
                }
                setRecycler(noteList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    String email;
    private void get_email(){
        myDbAdapter DB=new myDbAdapter(getApplicationContext());
        email=DB.getData_inf()[1];
    }
    private void setRecycler(List<Note> lis) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                RecyclerView.VERTICAL, false);
        list.setLayoutManager(layoutManager);

        NoteAdapter cartadpter = new NoteAdapter(getApplicationContext(), lis,id);
        list.setAdapter(cartadpter);

    }
    private void on_loading(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                RecyclerView.VERTICAL, false);
        list.setLayoutManager(layoutManager);

        LoadingAdapter load = new LoadingAdapter(getApplicationContext(),1,1);
        list.setAdapter(load);
    }
}
