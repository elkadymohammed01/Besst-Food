package com.e.goodcheif.log_in_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.e.goodcheif.Main2Activity;
import com.e.goodcheif.R;
import com.e.goodcheif.data.Check_Text;
import com.e.goodcheif.data.myDbAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class log_in extends Fragment {

    EditText email,password;
    Button sign;
    ProgressBar bar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_log_in, container, false);

        identity(v);
        add_user(v);

        return v;
    }
    private void identity(View v){

        email=v.findViewById(R.id.editText);
        password=v.findViewById(R.id.editText2);
        sign=v.findViewById(R.id.button);
        bar=v.findViewById(R.id.bar);

    }
    private void add_user(View v){

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                Check_Text text =new Check_Text(getContext());
                if(!text.is_empty(email)&&!text.is_empty(password)){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(email.getText().toString()
                            .substring(0,email.getText().toString().length()-4))
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        if(snapshot.child("password").getValue().toString().equals(
                                                password.getText().toString())){
                                            myDbAdapter DB =new myDbAdapter(getContext());
                                            DB.insertData(snapshot.child("name").getValue().toString(),
                                                    snapshot.child("phone").getValue().toString(),
                                                    email.getText().toString(),password.getText().toString());
                                            startActivity(new Intent(getContext(), Main2Activity.class));
                                            getActivity().finish();
                                        }
                                        else{
                                            Toast.makeText(getContext(),"Wong Password",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(getContext(),"Can't find User",Toast.LENGTH_LONG).show(); }
                                    bar.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }
                else
                    bar.setVisibility(View.INVISIBLE);
            }
        });

    }
}
