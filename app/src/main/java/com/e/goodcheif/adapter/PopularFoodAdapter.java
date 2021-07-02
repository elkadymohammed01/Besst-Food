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
import com.e.goodcheif.model.PopularFood;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder> {

    Context context;
    List<PopularFood> popularFoodList;



    public PopularFoodAdapter(Context context, List<PopularFood> popularFoodList) {
        this.context = context;
        this.popularFoodList = popularFoodList;
    }

    @NonNull
    @Override
    public PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.popular_food_row_item, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PopularFoodViewHolder holder, final int position) {


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(popularFoodList.get(position).getImageUrl().toString());
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(context).load(uri).into(holder.foodImage);
            }
        });

        if(!popularFoodList.get(position).getOffer().equals("0"))
            holder.offer.setVisibility(View.VISIBLE);
        String price=String.valueOf(Double.parseDouble(popularFoodList.get(position).getPrice())*
                (100.0-Double.parseDouble(popularFoodList.get(position).getOffer()))/100);
        holder.name.setText(popularFoodList.get(position).getName());
        holder.price.setText("$"+price);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        details.class);
                intent.putExtra("id", popularFoodList.get(position).getImageUrl().toString());
                intent.putExtra("child", "Food");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularFoodList.size();
    }


    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder{


        View view;
        ImageView foodImage,offer;
        TextView price, name;

        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            offer= itemView.findViewById(R.id.imageView16);
            view=itemView;

        }
    }

}
