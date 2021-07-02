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
import com.e.goodcheif.data.Kitchen_Item;
import com.e.goodcheif.details;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class
KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.CardViewHolder> {

    Context context;
    List<Kitchen_Item> Items;



    public KitchenAdapter(Context context, List<Kitchen_Item> Items) {
        this.context = context;
        this.Items = Items;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.kitchen_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(Items.get(position).getUrl());
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(context).load(uri).into(holder.image);
            }
        });
        holder.name.setText(Items.get(position).getTitle());
        String price=Items.get(position).getPrice();
        price=price.substring(0,Math.min(price.length(),6));
        holder.price.setText("$"+price);
        holder.rating.setText("%"+Items.get(position).getOffer()+" ");
        holder.restorantName.setText(Items.get(position).getType());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        details.class);
                intent.putExtra("id", Items.get(position).getUrl());
                intent.putExtra("child", "Kitchen");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }


    public static final class CardViewHolder extends RecyclerView.ViewHolder{


        ImageView image;
        TextView price, name, rating, restorantName;
        View view ;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView17);
            price = itemView.findViewById(R.id.textView20);
            name = itemView.findViewById(R.id.textView21);
            rating = itemView.findViewById(R.id.textView);
            restorantName = itemView.findViewById(R.id.textView19);
            view=itemView;


        }
    }

}
