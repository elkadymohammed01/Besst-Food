package com.e.goodcheif.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.e.goodcheif.R;
import com.e.goodcheif.data.Note;
import com.e.goodcheif.data.myDbAdapter;
import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.CardViewHolder> {

    Context context;
    List<Note> Items;
    ArrayList<String>id;
    public NoteAdapter(Context context, List<Note> Items, ArrayList<String>id) {
        this.context = context;
        this.Items = Items;
        get_email();
        this.id=id;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.note_food_row_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        holder.name.setText(Items.get(position).getName());
        holder.type.setText(Items.get(position).getType());
        if(Items.get(position).getType().charAt(0)==('O')){
            holder.Image.setImageResource(R.drawable.order);
            holder.price.setText("$"+Items.get(position).getMessage());
            holder.message.setText("we start to repair your dish .");
        }
        else if(Items.get(position).getType().charAt(0)==('A')){
            holder.Image.setImageResource(R.drawable.booktime);
            holder.message.setText(Items.get(position).getMessage());
            holder.price.setText("  ");
        }
        else{
            holder.message.setText(Items.get(position).getMessage());
            if(Items.get(position).getType().charAt(0)==("Call").charAt(0)){
                holder.Image.setImageResource(R.drawable.call); }
            else{ holder.Image.setImageResource(R.drawable.message);} }
        holder.price.setText("");
    }

    String email;
    private void get_email(){
        myDbAdapter DB=new myDbAdapter(context);
        email=DB.getData_inf()[1];
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }


    public static final class CardViewHolder extends RecyclerView.ViewHolder{


        ImageView Image;
        TextView name,message,price,type;
        View view ;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            Image = itemView.findViewById(R.id.food_image);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.restorant_name);
            message = itemView.findViewById(R.id.message);
            price = itemView.findViewById(R.id.price);
            view=itemView;

        }
    }

}