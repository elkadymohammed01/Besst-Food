package com.e.goodcheif.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.e.goodcheif.R;
import com.e.goodcheif.adapter.ShopItemClickListener;
import com.e.goodcheif.data.Kitchen_Item;
import com.e.goodcheif.model.PopularFood;
import com.e.goodcheif.shop.Item;
import com.e.goodcheif.shop.ShopAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment implements DiscreteScrollView.OnItemChangedListener<ShopAdapter.ViewHolder>,
        View.OnClickListener , ShopItemClickListener {
    private List<Item> data= new ArrayList();
    private List<String> about;
    TextView myabout;
    Map<String ,Integer> map=new HashMap<>();
    private DiscreteScrollView itemPicker;
    private InfiniteScrollAdapter<?> infiniteAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        myabout=root.findViewById(R.id.details);
        setImage(root);
        setData();
        setMap(root);
        return  root;

    }

    private void setData(){
        data=  Arrays.asList(
                new Item(7, "Vegetables", "", R.drawable.shop8)
                , new Item(8, "Cake", "", R.drawable.shop6),
                new Item(4, "Cheese", "", R.drawable.shop2),
                new Item(5, "The bread", "", R.drawable.shop3),
                new Item(1, "Kitchen  tools", "", R.drawable.shop4),
                new Item(2, "Seasoning", "", R.drawable.shop5),
                new Item(6, "Fruits", "", R.drawable.shop7)
        );
        for(int i=0;i<7;i++)
            map.put(data.get(i).getName(),i);
        about=Arrays.asList(
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
    private void setMap(View root){

        itemPicker = root.findViewById(R.id.item_picker);
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
        itemPicker.addOnItemChangedListener(this);
        infiniteAdapter = InfiniteScrollAdapter.wrap(new ShopAdapter(data,getContext()));
        itemPicker.setAdapter(infiniteAdapter);
        itemPicker.setItemTransitionTimeMillis(500);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

    }
    private void setImage(View v){
        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)v.findViewById(R.id.imageView13);
        imageView.setImage(ImageSource.resource(R.drawable.cooker));
        SubsamplingScaleImageView imview = (SubsamplingScaleImageView)v.findViewById(R.id.imageView14);
        imview.setImage(ImageSource.resource(R.drawable.shape1));
        SubsamplingScaleImageView imv = (SubsamplingScaleImageView)v.findViewById(R.id.imageView15);
        imv.setImage(ImageSource.resource(R.drawable.shape1));
    }
    @Override
    public void onClick(View v) {

    }


    @Override
    public void onDashboardCourseClick(PopularFood popularFood, ImageView imageView) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable ShopAdapter.ViewHolder viewHolder, int adapterPosition) {

        myabout.setText(about.get(map.get(viewHolder.tv_titulo.getText().toString())));

    }
}