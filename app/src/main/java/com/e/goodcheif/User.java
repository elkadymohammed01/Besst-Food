package com.e.goodcheif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.e.goodcheif.data.Note;
import com.e.goodcheif.data.myDbAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User extends AppCompatActivity {

    TextView user_name, email, Address;
    ImageView image;
    FloatingActionButton flag1, flag2 ,flag3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        SetId();
        setDetails();
        setFlag();
    }

    private void SetId() {
        user_name = findViewById(R.id.text_name);
        email = findViewById(R.id.text_email);
        flag1 = findViewById(R.id.flag1);
        flag2 = findViewById(R.id.flag2);
        flag3 = findViewById(R.id.settings);
        image = findViewById(R.id.imageView6);
        Address = findViewById(R.id.address);
    }

    private void setItem() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(mail);
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getApplicationContext()).load(uri).into(image);
            }
        });
        user_name.setText(name);
        email.setText("Email : " + mail);
        FirebaseDatabase.getInstance().getReference().child("Location").child(mail.substring(0, mail.length() - 4))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                            Address.setText(snapshot.getValue().toString());
                        else
                            Address.setText("Don't Have Location.");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    String mail, name, phone;

    private void setDetails() {
        int x = get_user_type();
        if (x == 1) {
            myDbAdapter Db = new myDbAdapter(getApplicationContext());
            name = Db.getData_inf()[0];
            mail = Db.getData_inf()[1];
            phone = Db.getData_inf()[2];
            setItem();
        } else {
            flag3.setVisibility(View.INVISIBLE);
            mail = getIntent().getStringExtra("user");
            FirebaseDatabase.getInstance().getReference().child("Users").child(mail)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                mail = snapshot.child("email").getValue().toString();
                                name = snapshot.child("name").getValue().toString();
                                phone = snapshot.child("phone").getValue().toString();
                            }
                            setItem();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }

    private void setFlag() {
        int x = get_user_type();
        if (x == 1) {
            id = 1;
            flag1.setImageResource(R.drawable.ic_photo_camera);
            flag2.setImageResource(R.drawable.ic_location);
            flag1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rotation(v);
                    get_data(new String[]{"image/*"});
                }
            });
            flag2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rotation(v);
                    final Dialog dod = new Dialog(User.this);
                    dod.setContentView(R.layout.edtext);
                    dod.show();
                    TextView ok = dod.findViewById(R.id.ok), cancel = dod.findViewById(R.id.cancel);
                    final EditText text = dod.findViewById(R.id.editText6);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase.getInstance().getReference().child("Location").child(mail.substring(0, mail.length() - 4))
                                    .setValue(text.getText().toString());
                            Address.setText(text.getText().toString());
                            dod.cancel();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dod.cancel();
                        }
                    });
                }
            });
            flag3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rotation(v);
                    setting();
                }
            });
        } else {
            id = 0;
            flag1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rotation(v);
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                    smsIntent.setData(Uri.parse("smsto:"));
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address"  , new String (phone));
                    smsIntent.putExtra("sms_body"  , " ");

                    try {
                        startActivity(smsIntent);
                        finish();
                        Note note=new Note();
                        note.setType("Message");
                        note.setName("Phone Message");
                        note.setMessage("We sent Message to you at  "+getTime());
                        FirebaseDatabase.getInstance().getReference()
                                .child("Note").child(mail.substring(0,mail.length()-4))
                                .child(String.valueOf(Long.MAX_VALUE-System.currentTimeMillis())).setValue(note);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(User.this,
                                "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            flag2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone));
                    if(getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE )==
                            PackageManager.PERMISSION_GRANTED){

                    }else {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},0);
                    }
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    rotation(v);
                    Note note=new Note();
                    note.setType("Call");
                    note.setName("Phone Call");
                    note.setMessage("We Call you at  "+getTime());
                    FirebaseDatabase.getInstance().getReference()
                            .child("Note").child(mail.substring(0,mail.length()-4))
                            .child(String.valueOf(Long.MAX_VALUE-System.currentTimeMillis())).setValue(note);
                    startActivity(callIntent);
                }
            });
        }
    }
    private void rotation(View view){
        ObjectAnimator.ofFloat(view, "rotation", 10f, 360f).start();
    }
    private void setting(){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.user_list);
        dialog.show();
        final TextView name,moname,email,moemail,phone,cancel,log_out;
        final ImageView profile;
        name=dialog.findViewById(R.id.name);
        moname=dialog.findViewById(R.id.moname);
        email=dialog.findViewById(R.id.email);
        moemail=dialog.findViewById(R.id.moemail);
        phone=dialog.findViewById(R.id.phone);
        profile=dialog.findViewById(R.id.profile);
        cancel=dialog.findViewById(R.id.cancel);
        log_out=dialog.findViewById(R.id.log_out);
        name.setText(this.name);
        moname.setText(this.name);
        email.setText(this.mail);
        moemail.setText(this.mail);
        phone.setText(this.phone);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(mail);
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(profile);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edText("name",name.getText().toString(),0);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_data(new String[]{"image/*"});
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edText("phone",phone.getText().toString(),2);
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDbAdapter mydb = new myDbAdapter(getApplicationContext());
                mydb.delete(name.getText().toString());
                finishAffinity();
            }
        });

    }
    private void edText(final String Type, String val, final int call){
        final Dialog dod = new Dialog(this);
        dod.setContentView(R.layout.edtext);
        dod.show();
        TextView ok = dod.findViewById(R.id.ok),
                cancel = dod.findViewById(R.id.cancel),
                title=dod.findViewById(R.id.textView31);
        final EditText text = dod.findViewById(R.id.editText6);
        title.setText(Type);
        text.setHint("Add "+Type);
        text.setText(val);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().toString().length()>6||
                        text.getText().toString().split(" ").length>=1){
                FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(mail.substring(0,mail.length()-4))
                        .child(Type).setValue(text.getText().toString());
                    myDbAdapter Db = new myDbAdapter(getApplicationContext());
                    if(call==0){
                        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
                        mydb.delete(name);
                        user_name.setText(text.getText().toString());
                        name=text.getText().toString();
                    Db.insertData(text.getText().toString(),phone,mail,"120012");}
                    else{
                        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
                        mydb.delete(name);
                        Db.insertData(name,text.getText().toString(),mail,"120012");}
                }
                else
                    Toast.makeText(User.this, "Find Problem", Toast.LENGTH_SHORT).show();

                dod.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dod.cancel();
            }
        });
    }
    int id=0;
    private int get_user_type(){
        Intent intent = getIntent();
        return intent.getIntExtra("user",0);
    }
    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            if (resultCode == Activity.RESULT_OK) {

                //             textTargetUri.setText(targetUri.toString());
                Uri targetUri = data.getData();
                file = targetUri;
                setItem();
                upload_file(mail);
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
            }
        });
    }
}