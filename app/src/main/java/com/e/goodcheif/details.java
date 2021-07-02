package com.e.goodcheif;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.e.goodcheif.data.Item_Cart;
import com.e.goodcheif.data.Kitchen_Item;
import com.e.goodcheif.data.myDbAdapter;
import com.e.goodcheif.model.PopularFood;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class details extends AppCompatActivity {

    String id=" ",ty;
    TextView title,type,details,price,rate,save,order,qunt;
    ImageView photo,offer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        get_email();
        get_data();
        identity();
        setDetails();
    }
    private void identity(){
        title=findViewById(R.id.textView11);
        type=findViewById(R.id.textView10);
        qunt=findViewById(R.id.textView13);
        rate=findViewById(R.id.textView3);
        save=findViewById(R.id.textView5);
        order=findViewById(R.id.textView6);
        details=findViewById(R.id.textView12);
        price=findViewById(R.id.textView14);
        photo=findViewById(R.id.imageView8);
        offer=findViewById(R.id.imageView9);
    }
    private void get_data(){
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        ty=  intent.getStringExtra("child");
    }

    int number=0;
    private void setDetails(){


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(id);
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getApplicationContext()).load(uri).into(photo);
            }
        });
        if(ty.equals("Food")){
        FirebaseDatabase.getInstance().getReference()
                .child("Food").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    PopularFood pp=new PopularFood();
                    DataSnapshot dataSnapshot=snapshot;
                    pp.setName(dataSnapshot.child("name").getValue().toString());
                    pp.setPrice(dataSnapshot.child("price").getValue().toString());
                    pp.setOffer(dataSnapshot.child("offer").getValue().toString());
                    pp.setImageUrl(Integer.parseInt(dataSnapshot.child("imageUrl").getValue().toString()));
                   title.setText(pp.getName());offer.setVisibility(!pp.getOffer().equals("0")? View.VISIBLE:View.INVISIBLE);
                   price.setText(String.valueOf(Double.parseDouble(pp.getPrice())*(100.0-Double.parseDouble(pp.getOffer()))/100.0));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}
        else{
            FirebaseDatabase.getInstance().getReference()
                    .child("Kitchen").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        Kitchen_Item pp=new Kitchen_Item();
                        DataSnapshot dataSnapshot=snapshot;
                        pp.setTitle(dataSnapshot.child("title").getValue().toString());
                        pp.setPrice(dataSnapshot.child("price").getValue().toString());
                        pp.setOffer(dataSnapshot.child("offer").getValue().toString());
                        pp.setUrl(""+Integer.parseInt(dataSnapshot.child("url").getValue().toString()));
                        title.setText(pp.getTitle());offer.setVisibility(!pp.getOffer().equals("0")? View.VISIBLE:View.INVISIBLE);
                        price.setText(String.valueOf(Double.parseDouble(pp.getPrice())*(100.0-Double.parseDouble(pp.getOffer()))/100.0));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        FirebaseDatabase.getInstance().getReference()
                .child("Food_Details").child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equals("details"))
                details.setText(snapshot.getValue().toString());
                else if(snapshot.getKey().equals("type"))
                type.setText(snapshot.getValue().toString());
                else if(snapshot.getKey().equals("rating"))
                rate.setText(snapshot.getValue().toString());
                else if(snapshot.getKey().equals("save"))
                save.setText(snapshot.getValue().toString());
                else if(snapshot.getKey().equals("order"))
                order.setText(snapshot.getValue().toString());
                else
                number=Integer.parseInt(snapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equals("rating"))
                    rate.setText(snapshot.getValue().toString());
                else if(snapshot.getKey().equals("save"))
                    save.setText(snapshot.getValue().toString());
                else if(snapshot.getKey().equals("order"))
                    order.setText(snapshot.getValue().toString());
                else
                    number=Integer.parseInt(snapshot.getValue().toString());
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

    int add=1;
    public void add(View view) {
        add++;
        qunt.setText(add>=10?""+add:"0"+add);
    }

    public void drop(View view) {
        if(add>1){ add--;qunt.setText(add>=10?""+add:"0"+add);}
    }

    public void add_to_cart(View view) {

        Item_Cart item=new Item_Cart(id,title.getText().toString(),price.getText().toString()
                ,type.getText().toString(),add);
        FirebaseDatabase.getInstance().getReference().child("Cart")
                .child(email.substring(0,email.length()-4)).push().setValue(item);

        FirebaseDatabase.getInstance().getReference()
                .child("Food_Details").child(id).child("order")
                .setValue(Integer.parseInt(order.getText().toString())+Integer.parseInt(qunt.getText().toString()));
        finish();
    }
    String email;
    private void get_email(){
        myDbAdapter DB=new myDbAdapter(getApplicationContext());
        email=DB.getData_inf()[1];
    }

    public void back(View view) {
        finish();
    }

    public void save(View view) {

        FirebaseDatabase.getInstance().getReference().child("Save")
                .child(email.substring(0,email.length()-4)).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    FirebaseDatabase.getInstance().getReference().child("Save")
                            .child(email.substring(0,email.length()-4)).child(id).setValue(id);
                    FirebaseDatabase.getInstance().getReference().child("SaveNote")
                            .child(id).child(email.substring(0,email.length()-4))
                            .setValue(email.substring(0,email.length()-4));
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(id).child("save").setValue(Integer.parseInt(save.getText().toString())+1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       }

    public void happy(View view) {
        FirebaseDatabase.getInstance().getReference().child("happy")
                .child(email.substring(0,email.length()-4)).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    FirebaseDatabase.getInstance().getReference().child("happy")
                            .child(email.substring(0,email.length()-4)).child(id).setValue(id);
                    double d=Double.parseDouble(rate.getText().toString());
                    d*=number;d+=5;d/=(number+1);
                    String rr=String.valueOf(d);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(id).child("rating").setValue(rr.substring(0, Math.min(4,rr.length())));
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(id).child("number").setValue(number+1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sad(View view) {
        FirebaseDatabase.getInstance().getReference().child("happy")
                .child(email.substring(0,email.length()-4)).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    FirebaseDatabase.getInstance().getReference().child("happy")
                            .child(email.substring(0,email.length()-4)).child(id).setValue(id);
                    double d=Double.parseDouble(rate.getText().toString());
                    d*=number;d/=(number+1);
                    String rr=String.valueOf(d);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(id).child("rating").setValue(rr.substring(0, Math.min(4,rr.length())));
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(id).child("number").setValue(number+1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
