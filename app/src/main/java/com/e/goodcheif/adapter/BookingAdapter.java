package com.e.goodcheif.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.goodcheif.R;
import com.e.goodcheif.User;
import com.e.goodcheif.data.Note;
import com.e.goodcheif.data.book;
import com.e.goodcheif.data.myDbAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.CardViewHolder> {

    Context context;
    List<book> Items;
    List<String>id,type;
    public BookingAdapter(Context context, List<book> Items, List<String>id) {
        this.context = context;
        this.Items = Items;
        get_email();
        this.id=id;
        type= new ArrayList<>();
        type.add("Personal");
        type.add("Couple");
        type.add("Family");
        type.add("Vip");
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.booking_user, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        gtUserName(Items.get(position).getUser_email(),holder.name);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(Items.get(position).getUser_email());
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.Image);
            }
        });
        holder.type.setText(type.get(Items.get(position).getType())+" Booking");
        holder.time.setText(Items.get(position).getTime()+"\n   "+getDate(Items.get(position).getAppoint()));
        holder.denial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });
        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, User.class);
                intent.putExtra("user",
                        Items.get(position).getUser_email().substring(0,Items.get(position).getUser_email().length()-4));
                context.startActivity(intent);
            }
        });
    }
    public void removeAt(int position) {

        FirebaseDatabase.getInstance().getReference().child("Appointment")
                .child(id.get(position)).removeValue();
        Items.remove(position);
        id.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, Items.size());
    }
    String email;
    private void get_email(){
        myDbAdapter DB=new myDbAdapter(context);
        email=DB.getData_inf()[1];
    }
    private String getDate(long time){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new Date(time));
        return selectedDate;
    }
    @Override
    public int getItemCount() {
        return Items.size();
    }
    public void gtUserName(String pos, final TextView name) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(pos.substring(0,pos.length()-4)).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    name.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static final class CardViewHolder extends RecyclerView.ViewHolder{


        ImageView Image;
        TextView name,time,profile,denial,type;
        View view ;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            Image=itemView.findViewById(R.id.me);
            name=itemView.findViewById(R.id.textView34);
            time=itemView.findViewById(R.id.textView33);
            type=itemView.findViewById(R.id.textView35);
            profile=itemView.findViewById(R.id.profile);
            denial=itemView.findViewById(R.id.denial);
            view=itemView;
        }
    }

}