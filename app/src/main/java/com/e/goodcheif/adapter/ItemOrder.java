package com.e.goodcheif.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.goodcheif.R;
import com.e.goodcheif.data.Item_Cart;

import java.util.List;

public class ItemOrder extends RecyclerView.Adapter<Item> {

    List<Item_Cart> list;
    Context context;

    public ItemOrder(List<Item_Cart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new Item(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Item holder, final int position) {
        holder.name.setText(list.get(position).getTitle());
        double x=list.get(position).getItems();
        holder.number.setText(x+"");
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeAt(holder.view);
                return false;
            }
        });
    }

    public void removeAt(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class Item extends RecyclerView.ViewHolder{
    TextView name,number,price;
    View view;
    public Item(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.name);
        number=itemView.findViewById(R.id.number);
        price=itemView.findViewById(R.id.price);
        view=itemView;
    }
}