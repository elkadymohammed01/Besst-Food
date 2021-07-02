package com.e.goodcheif.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.goodcheif.R;
import com.e.goodcheif.data.Delivery;
import com.e.goodcheif.data.Item_Cart;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

    Context context;
    List<List<Item_Cart>> order;
    List<String> id;

    public OrderAdapter(Context context, List<List<Item_Cart>> order, List<String> id) {
        this.context = context;
        this.order = order;
        this.id = id;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHolder holder, final int position) {
        holder.name.setText(id.get(position));
        holder.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeAt(position);
                return false;
            }
        });
        final boolean[] show = {false};
        holder.listmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show[0]){
                ObjectAnimator.ofFloat(v, "rotation", 90f, 90f).start();

                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    holder.list.setLayoutManager(layoutManager);
                    ItemOrder orderAdapter = new ItemOrder(order.get(position),context);
                    holder.list.setAdapter(orderAdapter);}
                else{
                ObjectAnimator.ofFloat(v, "rotation", 90f, 0f).start();

                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                    holder.list.setLayoutManager(layoutManager);
                    ItemOrder orderAdapter = new ItemOrder(new ArrayList<Item_Cart>(),context);
                    holder.list.swapAdapter(orderAdapter,false);
                }
                show[0] =!show[0];
            }
        });

    }

    public void removeAt(int position) {
        double ans=0.0;
        String details="";
        for(Item_Cart itemCart:order.get(position)){
            details+=itemCart.getItems()+" "+itemCart.getTitle()+"   ";
            double pos=itemCart.getItems()*Double.parseDouble(itemCart.getPrice());
            details+=pos+"\n";
            ans+=pos; }
        Delivery delivery=new Delivery(id.get(position),details,ans);
        FirebaseDatabase.getInstance().getReference().child("Delivery").push().setValue(delivery);
        FirebaseDatabase.getInstance().getReference().child("Order").child(id.get(position)).removeValue();
        id.remove(position);
        order.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, order.size());
    }
    @Override
    public int getItemCount() {
        return id.size();
    }
}
class OrderHolder extends RecyclerView.ViewHolder{

    TextView name;
    ImageView listmode;
    RecyclerView list;
    public OrderHolder(@NonNull View itemView) {
        super(itemView);
        listmode=itemView.findViewById(R.id.listMode);
        name=itemView.findViewById(R.id.name);
        list=itemView.findViewById(R.id.list);
    }
}