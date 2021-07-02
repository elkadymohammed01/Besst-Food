package com.e.goodcheif.shop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.goodcheif.R;
import com.e.goodcheif.details;
import com.e.goodcheif.kitchen_list;

import java.util.Arrays;
import java.util.List;
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<Item> data;
    private Context context;
    private List<String>about;
    public ShopAdapter(List<Item> data, Context context) {
        this.data = data;
        this.context=context;
        about= Arrays.asList(
                "Buy Products from Prequalified Suppliers at Factory Price. Get Live Quotes Now! Logistics Service." +
                        " Trade Assurance. Most Popular. Production Monitoring. Types: Machinery, Home & Kitchen, Consumer Electronics, Packaging & Printing, Lights & Lighting, Apparel",
                "Send Cake online from best cake shop in India. Ferns N Petals offers online cake order at no extra cost with same day & midnight cake delivery. Free Shipping",
                "Visit our cheese counter to order high quality cheese favorites. Choose from a wide selection of best-selling brands and favorites from around the world",
                "Make the Bread, Buy the Butter: What You Should and Shouldn't Cook from Scratch -- Over 120 Recipes for the Best Homemade Foods ",
                "High-Quality Juicer Blender With Competitive Price. Get The App. Join For Free. View Solutions. Get Mobile Apps. Get Quotations. Tailored Services. Download Free Mobile App. Highlights: App Available, Quotes Available, New User Guide Available.",
                "The Spice House online store features exquisite spices, herbs and seasonings. Browse our fabulous selection of spices and recipes and bring your dishes",
                "Modular Kitchen Designs. Beautiful Kitchen Designs, Customized For Your Needs. Book Free Online Consultation. Kitchens to suit your floor plan.",
                "Buy Fresh fruits and vegetables online at the best prices. Order your favorite fruits and vegetables from India's best Online Super Market");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.popular_food_row_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Item itemDatos = data.get(position);
        holder.image.setImageResource(itemDatos.getImage());
        holder.tv_titulo.setText(itemDatos.getName());
        holder.tv_cantidad_cursos.setText(itemDatos.getPrice());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, kitchen_list.class);
                intent.putExtra("id",itemDatos.getName());
                intent.putExtra("about",about.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        public TextView tv_titulo;
        public TextView tv_cantidad_cursos;
        public View view ;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.food_image);
            tv_titulo = itemView.findViewById(R.id.name);
            tv_cantidad_cursos = itemView.findViewById(R.id.price);
            view=itemView;
        }
    }
}
