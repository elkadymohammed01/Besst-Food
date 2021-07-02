package com.e.goodcheif.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.goodcheif.R;
import com.e.goodcheif.User;
import com.e.goodcheif.data.Delivery;
import com.e.goodcheif.data.Item_Cart;
import com.e.goodcheif.data.Save;
import com.e.goodcheif.data.myDbAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class
ClintAdapter extends RecyclerView.Adapter<ClintAdapter.CardViewHolder> {

    Context context;
    List<Delivery> Items;
    List<String>id;
    public ClintAdapter(Context context, List<Delivery> Items, List<String>id) {
        this.context = context;
        this.Items = Items;
        get_email();
        this.id=id;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_food_row_item, parent, false);
        return new CardViewHolder(view);
    }

    public void gtUserName(String pos, final TextView name) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(pos).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
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

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {

        gtUserName(Items.get(position).getClint(),holder.name);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(Items.get(position).getClint()+".com");
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(context).load(uri).into(holder.foodImage);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, User.class);
                intent.putExtra("user",Items.get(position).getClint());
                context.startActivity(intent);
            }
        });

        holder.restorantName.setText(Items.get(position).getClint());
        holder.price.setText("$"+String.valueOf(
                Items.get(position).getPrice())
                .substring(0,Math.min(String.valueOf(Items.get(position).getPrice()).length(),6)));
        holder.del.setImageResource(R.drawable.order);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        width-=35;
        holder.cardView.setMinimumWidth(width);
        holder.delete.setBackgroundResource(R.color.green);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_details(Items.get(position).getDetails(),position);
            }
        });

    }
    String email;
    private void get_email(){
        myDbAdapter DB=new myDbAdapter(context);
        email=DB.getData_inf()[1];
    }
    private void get_details(String val, final int pos){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.order_details);
        dialog.show();
        ((TextView)dialog.findViewById(R.id.details)).setText(val+"");
        ((TextView)dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(pos);
                dialog.cancel(); }});
        ((TextView)dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel(); }});
    }
    public void removeAt(int position) {

        FirebaseDatabase.getInstance().getReference().child("Delivery")
                .child(id.get(position)).removeValue();
        Items.remove(position);
        id.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, Items.size());
    }
    @Override
    public int getItemCount() {
        return Items.size();
    }


    public static final class CardViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage,del;
        TextView price, name, rating, restorantName;
        CardView cardView;
        View view ;
        LinearLayout delete;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            restorantName = itemView.findViewById(R.id.restorant_name);
            view=itemView;
            cardView=itemView.findViewById(R.id.card);
            delete=itemView.findViewById(R.id.delete);
            del=itemView.findViewById(R.id.delete_image);

        }
    }

}
