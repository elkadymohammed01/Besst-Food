package com.e.goodcheif.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.e.goodcheif.R;
import com.e.goodcheif.Time;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        identity(root);
        return root;
    }

    private void identity(final View view){
        ArrayList<Integer>id_card=new ArrayList<>(),id_text=new ArrayList<>(),id_line=new ArrayList<>();
        id_card.add(R.id.card1);id_card.add(R.id.card2);id_card.add(R.id.card3);id_card.add(R.id.card4);
        id_text.add(R.id.text1);id_text.add(R.id.text2);id_text.add(R.id.text3);id_text.add(R.id.text4);
        id_line.add(R.id.line1);id_line.add(R.id.line2);id_line.add(R.id.line3);id_line.add(R.id.line4);
        final CardView[]cardViews=new CardView[4];
        final TextView[]text=new TextView[4];
        final SubsamplingScaleImageView[]line=new SubsamplingScaleImageView[4];
        for(int i=0;i<4;i++){
            cardViews[i]=view.findViewById(id_card.get(i));
            text[i]=view.findViewById(id_text.get(i));
            line[i]=view.findViewById(id_line.get(i));
        }
        for(int i=0;i<4;i++) {
            final int finalI = i;
            line[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int u=0;u<4;u++){
                        cardViews[u].setCardBackgroundColor(Color.WHITE);
                        text[u].setTextColor(view.getResources().getColor(R.color.colorPrimary));}

                    get=finalI;
                    cardViews[finalI].setCardBackgroundColor(view.getResources().getColor(R.color.Belize));
                    text[finalI].setTextColor(view.getResources().getColor(R.color.White));
                }
            });
            cardViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int u=0;u<4;u++){
                        cardViews[u].setCardBackgroundColor(Color.WHITE);
                        text[u].setTextColor(view.getResources().getColor(R.color.colorPrimary));}

                    get=finalI;
                    cardViews[finalI].setCardBackgroundColor(view.getResources().getColor(R.color.Belize));
                    text[finalI].setTextColor(view.getResources().getColor(R.color.White));
                }
            });
        }
        final TextView next =view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(view);
            }
        });
    }
    int get=0;

    public void next(View view) {
        Intent tt=new Intent(getContext(), Time.class);
        tt.putExtra("number",get);
        startActivity(tt);
    }
}
