package com.e.goodcheif.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.goodcheif.BookingMangment;
import com.e.goodcheif.List_Order;
import com.e.goodcheif.Notification;
import com.e.goodcheif.R;
import com.e.goodcheif.User;
import com.e.goodcheif.adapter.LoadingAdapter;
import com.e.goodcheif.adapter.offerFoodAdapter;
import com.e.goodcheif.adapter.PopularFoodAdapter;
import com.e.goodcheif.carboy;
import com.e.goodcheif.data.myDbAdapter;
import com.e.goodcheif.model.PopularFood;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView popularRecycler, asiaRecycler;
    PopularFoodAdapter popularFoodAdapter;
    offerFoodAdapter asiaFoodAdapter;
    ImageView image,moge;
    EditText search;
    List<PopularFood> popularFoodList = new ArrayList<>();
    List<PopularFood> offerFoodList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        on_loading(root);
        setPopularRecyclerAdapter(root);
        setSearch(root);
        setMap(root);

        return root;
    }
    ArrayList<Integer>pos[];
    private void setMap(View v){
        moge=v.findViewById(R.id.imageView);
        moge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent me=new Intent(getContext(), User.class);
                me.putExtra("user",1);
                startActivity(me);
            }
        });
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(new myDbAdapter(getContext()).getData_inf()[1]);
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getContext()).load(uri).into(moge);
            }
        });
        image=v.findViewById(R.id.imageView2);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDbAdapter DB =new myDbAdapter(getContext());
                if(DB.getData_inf()[1].toLowerCase().equals("elkadymohammed00@gmail.com")){
                    getContext().startActivity(new Intent(getContext(), carboy.class));
                }
                else if(DB.getData_inf()[1].toLowerCase().equals("elkadymohammed01@gmail.com")){
                    getContext().startActivity(new Intent(getContext(), List_Order.class));
                }
                else if(DB.getData_inf()[1].toLowerCase().equals("elkadymohammed02@gmail.com")){
                    getContext().startActivity(new Intent(getContext(), BookingMangment.class));
                }
                else{ getContext().startActivity(new Intent(getContext(), Notification.class));}
            }
        });
    }
    private void on_loading(View v){
        asiaRecycler = v.findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        asiaRecycler.setLayoutManager(layoutManager);
        LoadingAdapter load = new LoadingAdapter(getContext(),1,1);
        asiaRecycler.setAdapter(load);
        popularRecycler = v.findViewById(R.id.popular_recycler);

                layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
         load = new LoadingAdapter(getContext(),1,0);
        popularRecycler.setAdapter(load);
    }
    private void setSearch(View root){
        search =root.findViewById(R.id.editText);
        search.addTextChangedListener(new TextWatcher() {
            int x=0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            x=x;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            x=x;
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<PopularFood>pop=new ArrayList<>();
                if(s.toString().length()>x){
                    for(int i=0;i<popularFoodList.size();i++) {
                        if (pos[i].size() >= x){
                            String id=popularFoodList.get(i).getName();
                            for(int u=(pos[i].size()<=0?0:pos[i].get(pos[i].size()-1)+1);u<id.length();u++){
                                if(s.toString().charAt(s.toString().length()-1)==id.charAt(u)){
                                    pos[i].add(u);
                                    pop.add(popularFoodList.get(i));
                                    break;
                                } } } }
                            x++;
                }
                else{
                    x--;
                    for(int i=0;i<popularFoodList.size();i++) {
                        if(pos[i].size()==x)
                            pop.add(popularFoodList.get(i));
                        else if(pos[i].size()>x){
                            pos[i].remove(x);
                            pop.add(popularFoodList.get(i));
                        }

                    }
                    }
                PopularFoodAdapter adapter=new PopularFoodAdapter(getContext(),pop);
                popularRecycler.swapAdapter(adapter,false);
            }
        });
    }
    private void setPopularRecyclerAdapter(final View v){
        FirebaseDatabase.getInstance().getReference()
                .child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PopularFood pp=new PopularFood();
                    pp.setName(dataSnapshot.child("name").getValue().toString());
                    pp.setPrice(dataSnapshot.child("price").getValue().toString());
                    pp.setOffer(dataSnapshot.child("offer").getValue().toString());
                    pp.setImageUrl(Integer.parseInt(dataSnapshot.child("imageUrl").getValue().toString()));
                    popularFoodList.add(pp);
                    if(!pp.getOffer().equals("0"))
                        offerFoodList.add(pp);
                }
                setPopularRecycler(popularFoodList,v);

                setAsiaRecycler(offerFoodList,v);

                pos=new ArrayList[popularFoodList.size()+5];
                for(int i=0;i<pos.length;i++)
                    pos[i]=new ArrayList<>();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void setPopularRecycler(List<PopularFood> popularFoodList,View v) {

        popularRecycler = v.findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularFoodAdapter = new PopularFoodAdapter(getContext(), popularFoodList);
        popularRecycler.setAdapter(popularFoodAdapter);

    }

    private void setAsiaRecycler(List<PopularFood> FoodList,View v) {

        asiaRecycler = v.findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        asiaRecycler.setLayoutManager(layoutManager);
        asiaFoodAdapter = new offerFoodAdapter(getContext(), FoodList);
        asiaRecycler.setAdapter(asiaFoodAdapter);

    }
}
