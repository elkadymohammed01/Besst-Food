package com.e.goodcheif.adapter;

import android.app.Activity;
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
import com.e.goodcheif.data.Item_Cart;
import com.e.goodcheif.data.Save;
import com.e.goodcheif.data.myDbAdapter;
import com.e.goodcheif.details;
import com.e.goodcheif.shop.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class
CartAdapter extends RecyclerView.Adapter<CartAdapter.CardViewHolder> {

    Context context;
    List<Item_Cart> Items;
    ArrayList<String>id;
    public CartAdapter(Context context, List<Item_Cart> Items, ArrayList<String>id) {
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

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(Items.get(position).getId());
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(context).load(uri).into(holder.foodImage);
            }
        });
        holder.name.setText(Items.get(position).getTitle());
        String price=String.valueOf(Double.parseDouble(Items.get(position).getPrice())*Items.get(position).getItems());
        holder.price.setText("$"+price.substring(0,Math.min(price.length(),6)));
        holder.rating.setText(Items.get(position).getItems()+" ");
        holder.restorantName.setText(Items.get(position).getType());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        width-=35;
        holder.cardView.setMinimumWidth(width);
        final String finalPrice = price;
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save.textView.setText(""+(Double.parseDouble(Save.textView.getText().toString())-
                        Double.parseDouble(finalPrice)));
                if((Double.parseDouble(Save.textView.getText().toString())<0))
                    Save.textView.setText("0.00");
                FirebaseDatabase.getInstance().getReference().child("Cart")
                        .child(email.substring(0,email.length()-4))
                        .child(id.get(position)).removeValue();
                removeAt(position);
            }
        });

    }
    String email;
    private void get_email(){
        myDbAdapter DB=new myDbAdapter(context);
        email=DB.getData_inf()[1];
    }

    public void removeAt(int position) {

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


        ImageView foodImage;
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

        }
    }

}
