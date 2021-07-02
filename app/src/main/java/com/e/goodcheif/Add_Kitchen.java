package com.e.goodcheif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.e.goodcheif.data.Kitchen_Item;
import com.e.goodcheif.model.PopularFood;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Add_Kitchen extends AppCompatActivity {


    EditText title,details,price;
    TextView post ;
    ImageView image;
    RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__kitchen);
        identity();
        char_item();
        select_image();
    }

    private void identity(){
        title=findViewById(R.id.editText3);
        details=findViewById(R.id.editText4);
        price=findViewById(R.id.editText5);
        group=findViewById(R.id.group_divider);
        post=findViewById(R.id.post);
        image=findViewById(R.id.image);
    }

    private void select_image(){
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_data(new String[]{"image/*"});
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add>3){
                    int pos=(int)(System.currentTimeMillis()%(long)Integer.MAX_VALUE);
                    if(pos>0)
                        pos=Integer.MAX_VALUE-pos;
                    else
                        pos*=-1;
                    String type=((RadioButton)findViewById(group.getCheckedRadioButtonId())).getText().toString();
                    Kitchen_Item item=new Kitchen_Item();
                    item.setTitle(title.getText().toString());
                    item.setUrl(""+pos);
                    item.setPrice(price.getText().toString());
                    item.setOffer("0");
                    item.setType(type);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Kitchen").child(String.valueOf(pos)).setValue(item);

                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(String.valueOf(pos))
                            .child("details").setValue(details.getText().toString());
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(String.valueOf(pos))
                            .child("type").setValue(type);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(String.valueOf(pos))
                            .child("rating").setValue(5.0);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(String.valueOf(pos))
                            .child("number").setValue(0);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(String.valueOf(pos))
                            .child("save").setValue(0);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(String.valueOf(pos))
                            .child("order").setValue(0);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Food_Details").child(String.valueOf(pos))
                            .child("offer").setValue(0);

                    upload_file(String.valueOf(pos));
                }
            }
        });
    }
    void get_data(String[] lop){
        try {
            String[] mimeTypes =
                    lop;

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            } else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
            }
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 100);

        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private  void char_item(){
        EditText[]texts={title,details,price};
        for(int i=0;i<texts.length;i++) {
            final int finalI = i;
            texts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.toString().length()>0&&!show[finalI]){
                        show[finalI]=true;
                        add++;
                        if(add>3)
                            post.setTextColor(Color.BLUE);
                    }
                    else if(s.toString().length()==0){
                        add--;
                        show[finalI]=false;
                        post.setTextColor(Color.GRAY);
                    }
                }
            });
        }
    }

    boolean show[]=new boolean[8];
    int add=0;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            if (resultCode == Activity.RESULT_OK) {

                //             textTargetUri.setText(targetUri.toString());
                Uri targetUri = data.getData();
                file = targetUri;
                if(!show[5])
                    add++;
                if(add>3){
                    post.setTextColor(Color.BLUE);
                }

            }
        }
    }

    Uri file;

    private void upload_file(String f){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child(f);

        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Add_Kitchen.this, "exception.toString()", Toast.LENGTH_SHORT).show();

            }
        });

    }

}