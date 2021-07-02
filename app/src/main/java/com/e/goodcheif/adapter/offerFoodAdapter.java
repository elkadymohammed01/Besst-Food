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
import com.e.goodcheif.details;
import com.e.goodcheif.model.AsiaFood;
import com.e.goodcheif.model.PopularFood;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class
offerFoodAdapter extends RecyclerView.Adapter<offerFoodAdapter.AsiaFoodViewHolder> {

    Context context;
    List<PopularFood> offerFoodList;



    public offerFoodAdapter(Context context, List<PopularFood> offerFood) {
        this.context = context;
        this.offerFoodList = offerFood;
    }

    @NonNull
    @Override
    public AsiaFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.asia_food_row_item, parent, false);
        return new AsiaFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AsiaFoodViewHolder holder, final int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(offerFoodList.get(position).getImageUrl().toString());
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(context).load(uri).into(holder.foodImage);
            }
        });
        holder.name.setText(offerFoodList.get(position).getName());
        String price=String.valueOf(Double.parseDouble(offerFoodList.get(position).getPrice())*
                (100.0-Double.parseDouble(offerFoodList.get(position).getOffer()))/100);
        holder.price.setText("$"+price);
        holder.rating.setText("%"+offerFoodList.get(position).getOffer());
        holder.restorantName.setText("Atlop");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        details.class);
                intent.putExtra("id", offerFoodList.get(position).getImageUrl().toString());
                intent.putExtra("child", "Food");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return offerFoodList.size();
    }


    public static final class AsiaFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView price, name, rating, restorantName;

        View view ;
        public AsiaFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            restorantName = itemView.findViewById(R.id.restorant_name);
            view=itemView;


        }
    }

}
