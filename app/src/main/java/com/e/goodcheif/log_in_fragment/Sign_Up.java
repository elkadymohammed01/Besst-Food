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

import androidx.fragment.app.Fragment;

import com.e.goodcheif.Main2Activity;
import com.e.goodcheif.R;
import com.e.goodcheif.data.Check_Text;
import com.e.goodcheif.data.User;
import com.e.goodcheif.data.myDbAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Sign_Up extends Fragment {

    EditText name,email,password,phone;
    ProgressBar bar;
    Button sign;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_sign_up, container, false);

        identity(v);
        add_user(v);

        return v;
    }
    private void identity(View v){
        name=v.findViewById(R.id.edit_name);
        email=v.findViewById(R.id.edit_email);
        phone=v.findViewById(R.id.edit_phone);
        password=v.findViewById(R.id.editText2);
        sign=v.findViewById(R.id.button);
        bar=v.findViewById(R.id.bar);
    }
    private void add_user(View v){
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check_Text text=new Check_Text(getContext());
                bar.setVisibility(View.VISIBLE);
                if(!text.is_empty(name)&&!text.is_empty(email)&&!text.is_empty(phone)&&!text.is_empty(password)){

                    User user =new User(name.getText().toString(),
                            email.getText().toString(),password.getText().toString()
                            ,phone.getText().toString());
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(user.getEmail().substring(0,
                            user.getEmail().length()-4)).setValue(user);
                    myDbAdapter db =new myDbAdapter(getContext());
                    db.insertData(user.getName(),user.getPhone(),user.getEmail(),user.getPassword());
                    getActivity().finish();

                    Intent intent=new Intent(getContext(), Main2Activity.class);
                    startActivity(intent);

                }
                else{ bar.setVisibility(View.INVISIBLE);}

            }
        });

    }

}