package com.e.goodcheif.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.goodcheif.R;
import com.e.goodcheif.User;
import com.e.goodcheif.adapter.CartAdapter;
import com.e.goodcheif.adapter.LoadingAdapter;
import com.e.goodcheif.data.Item_Cart;
import com.e.goodcheif.data.Note;
import com.e.goodcheif.data.Save;
import com.e.goodcheif.data.myDbAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {


    private List<Item_Cart> Items;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        on_loading(root);
        set_click(root);
        setPopularRecyclerAdapter(root);

        return root;
    }
    private void on_loading(View v){
        RecyclerView CartRecycler = v.findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        CartRecycler.setLayoutManager(layoutManager);

        LoadingAdapter load = new LoadingAdapter(getContext(),1,1);
        CartRecycler.setAdapter(load);
    }
    double price=0.0;
    ArrayList<String>id;
    private void set_click(final View view){
        TextView submit=view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(view);
            }
        });
    }
    private void setPopularRecyclerAdapter(final View v){
        get_email();
        Items=new ArrayList<>();
        id=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference()
                .child("Cart").child(email.substring(0,email.length()-4))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Item_Cart pp = dataSnapshot.getValue(Item_Cart.class);
                    Items.add(pp);
                    id.add(dataSnapshot.getKey());
                    price+=(Double.parseDouble(pp.getPrice())*pp.getItems());
                }
                setAsiaRecycler(Items,v);
                TextView textView=v.findViewById(R.id.total);
                textView.setText(price+"");

                Save.textView=textView;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setAsiaRecycler(List<Item_Cart> FoodList, View v) {

        RecyclerView CartRecycler = v.findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        CartRecycler.setLayoutManager(layoutManager);

        CartAdapter cartadpter = new CartAdapter(getContext(), FoodList,id);
        CartRecycler.setAdapter(cartadpter);

    }
    String email;
    private void get_email(){
        myDbAdapter DB=new myDbAdapter(getContext());
        email=DB.getData_inf()[1];
    }


    public void submit(View view) {

        String Message="";
                            for(int i=0;i<id.size();i++){
                                FirebaseDatabase.getInstance().getReference().child("Cart")
                                        .child(email.substring(0,email.length()-4))
                                        .child(id.get(i)).removeValue();
                                FirebaseDatabase.getInstance().getReference()
                                        .child("Order").child(email.substring(0,email.length()-4))
                                        .push().setValue(Items.get(i)); }
                            Toast.makeText(getContext(),"Done !", Toast.LENGTH_LONG).show();
                     location();
                     Message=Save.textView.getText().toString().substring(0,Math.min(6,Save.textView.getText().toString().length()));
        Note note=new Note();
        note.setType("Order");
        note.setName("Fast Offer");
        note.setMessage(Message);
        FirebaseDatabase.getInstance().getReference()
                .child("Note").child(email.substring(0,email.length()-4))
                .child(String.valueOf(Long.MAX_VALUE-System.currentTimeMillis())).setValue(note);
                     id=new ArrayList<>();
                     Items=new ArrayList<>();
                     setAsiaRecycler(Items,view);

    }
 private void location(){

     final Dialog dod = new Dialog(getActivity());
     dod.setContentView(R.layout.edtext);
     dod.show();
     TextView ok = dod.findViewById(R.id.ok), cancel = dod.findViewById(R.id.cancel);
     final EditText text = dod.findViewById(R.id.editText6);
     ok.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             FirebaseDatabase.getInstance().getReference().child("Location").child(email.substring(0, email.length() - 4))
                     .setValue(text.getText().toString());
             dod.cancel();
         }
     });
     cancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             dod.cancel();
         }
     });
 }
}
