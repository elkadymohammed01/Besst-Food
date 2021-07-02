package com.e.goodcheif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.goodcheif.data.Note;
import com.e.goodcheif.data.book;
import com.e.goodcheif.data.myDbAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        identity();
    }
    TextView[]text;
    CardView[]card;
    CalendarView time;
    private void identity(){
        time=findViewById(R.id.calendarView);
        text=new TextView[15];
        card=new CardView[15];
        text[0]=findViewById(R.id.textI); text[1]=findViewById(R.id.textII); text[2]=findViewById(R.id.textIII);
        text[3]=findViewById(R.id.textIV); text[4]=findViewById(R.id.textV); text[5]=findViewById(R.id.textVI);
        text[6]=findViewById(R.id.text1); text[7]=findViewById(R.id.text2); text[8]=findViewById(R.id.text3);
        text[9]=findViewById(R.id.text4); text[10]=findViewById(R.id.text5); text[11]=findViewById(R.id.text6);
        card[0]=findViewById(R.id.cardI); card[1]=findViewById(R.id.cardII); card[2]=findViewById(R.id.cardIII);
        card[3]=findViewById(R.id.cardIV); card[4]=findViewById(R.id.cardV); card[5]=findViewById(R.id.cardVI);
        card[6]=findViewById(R.id.card1); card[7]=findViewById(R.id.card2); card[8]=findViewById(R.id.card3);
        card[9]=findViewById(R.id.card4); card[10]=findViewById(R.id.card5); card[11]=findViewById(R.id.card6);
        min_time();
        onclick_time();
    }
    long appoint=System.currentTimeMillis();
    private void min_time(){

        time.setMinDate(System.currentTimeMillis());
        time.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);
                appoint = c.getTimeInMillis();

            }
        });
    }
    boolean show[]=new boolean[15];
    private void onclick_time(){
        for(int i=0;i<12;i++) {
            final int finalI = i;
            card[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text[finalI].setTextColor(getResources().getColor(R.color.White));
                    card[finalI].setCardBackgroundColor(getResources().getColor(R.color.Belize));
                    if (show[finalI]) {
                        text[finalI].setTextColor(getResources().getColor(R.color.gray));
                        card[finalI].setCardBackgroundColor(getResources().getColor(R.color.White));
                    }
                    show[finalI]=!show[finalI];
                }
            });
        }
    }
    public void back(View view) {
        finish();
    }

    public void next(View view) {
        get_email();

        int type=getIntent().getIntExtra("number",0);
        for(int i=0;i<12;i++){
            if(show[i]){
                book bb=new book();
                bb.setAppoint(appoint);
                bb.setTime(text[i].getText().toString());
                bb.setType(type);
                bb.setUser_email(email);
                Note note=new Note();
                note.setType("Appointment");
                note.setName("Appointment Booking");
                note.setMessage("you take place at "+bb.getTime()+" "+getDate(bb.getAppoint())+" at level of "+bb.getType());
                FirebaseDatabase.getInstance().getReference()
                        .child("Note").child(email.substring(0,email.length()-4))
                        .child(String.valueOf(Long.MAX_VALUE-System.currentTimeMillis())).setValue(note);
                bb.setAppoint((bb.getAppoint()/86400000)*86400000);
                FirebaseDatabase.getInstance().getReference().child("Appointment")
                        .push().setValue(bb);

            }
        }
        Intent in=new Intent(Time.this,finsh_book.class);
        in.putExtra("type",type);
        startActivity(in);
        finish();
    }

    String email;
    private void get_email(){
        myDbAdapter DB=new myDbAdapter(getApplicationContext());
        email=DB.getData_inf()[1];
    }
    private String getDate(long time){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new Date(appoint));
        return selectedDate;
    }
}
