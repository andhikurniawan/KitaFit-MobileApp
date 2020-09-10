package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.akasa.kitafit.R;
import com.akasa.kitafit.fragment.ProfilFragment;
import com.akasa.kitafit.model.usermodel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

public class EditProfile extends AppCompatActivity {
    private String checker = "";
    EditText nama_user,jk_user,umur_user, bb_user,tb_user;
    private FirebaseUser user;
    String UID;
    private CircularImageView updateProfilePic;
    int IMAGE_REQUEST_CODE = 71;
    Uri imagePath;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    public Uri donwload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        nama_user = (EditText) findViewById(R.id.txt_nama);
        jk_user = (EditText) findViewById(R.id.txt_jeniskelamin);
        umur_user = (EditText) findViewById(R.id.txt_umur);
        bb_user = (EditText) findViewById(R.id.txt_berat);
        tb_user = (EditText) findViewById(R.id.txt_tinggi);
        updateProfilePic = findViewById(R.id.img_user);
        Button Submit = (Button) findViewById(R.id.btn_edit);
        ImageButton btn_back=(ImageButton)findViewById(R.id.btn_bac_edit);
        firebaseStorage = FirebaseStorage.getInstance();
       storageReference = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        UID=user.getUid();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this, ProfilFragment.class));
            }
        });

        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference("user");
        UsersRef.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usermodel user = dataSnapshot.getValue(usermodel.class);
                Picasso.get().load(user.getFoto_user()).into(updateProfilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        userInfoDisplay(nama_user,jk_user,umur_user,bb_user,tb_user);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }
            }
        });
    }
    private void updateOnlyUserInfo() {
        storageReference=firebaseStorage.getReference();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
        if(imagePath!=null){
        StorageReference storageReference2nd = storageReference.child("User").child(UID+System.currentTimeMillis()+ "." + getFileExtension(imagePath));
        storageReference2nd.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                donwload = uriTask.getResult();
                HashMap<String, Object> userMap = new HashMap<>();
                userMap. put("berat_badan", bb_user.getText().toString());
                userMap. put("jenis_kelamin", jk_user.getText().toString());
                userMap. put("nama_user", nama_user.getText().toString());
                userMap. put("tinggi_badan", tb_user.getText().toString());
                userMap. put("umur", umur_user.getText().toString());
                userMap. put("foto_user", donwload.toString());
                ref.child(UID).updateChildren(userMap);


                Toast.makeText(EditProfile.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfile.this, EditProfile.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        }else{
            HashMap<String, Object> userMap = new HashMap<>();
            userMap. put("berat_badan", bb_user.getText().toString());
            userMap. put("jenis_kelamin", jk_user.getText().toString());
            userMap. put("nama_user", nama_user.getText().toString());
            userMap. put("tinggi_badan", tb_user.getText().toString());
            userMap. put("umur", umur_user.getText().toString());
            ref.child(UID).updateChildren(userMap);


            Toast.makeText(EditProfile.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditProfile.this, EditProfile.class));
            finish();
        }

    }

    private String getFileExtension(Uri imagePath) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        //returning the file extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imagePath));
    }

    private void userInfoSaved() {
        if (TextUtils.isEmpty(bb_user.getText().toString()))
        {
            Toast.makeText(this, "Isi Berat Badan", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(jk_user.getText().toString()))
        {
            Toast.makeText(this, "Isi Jenis Kelamin", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nama_user.getText().toString()))
        {
            Toast.makeText(this, "Isi Nama", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tb_user.getText().toString()))
        {
            Toast.makeText(this, "Isi Tinggi Badan", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(umur_user.getText().toString()))
        {
            Toast.makeText(this, "Isi Umur User", Toast.LENGTH_SHORT).show();
        }
        else if (getFileExtension(imagePath).equals(""))
        {
            Toast.makeText(this, "Isi Umur User", Toast.LENGTH_SHORT).show();
        }
    }
    private void userInfoDisplay(final EditText nama_user, final EditText jk_user, final EditText umur_user, final EditText bb_user, final EditText tb_user) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference("user");
        UsersRef.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if(dataSnapshot.child("nama_user").exists()){
                        String nama=dataSnapshot.child("nama_user").getValue().toString();
                        String jenis_kelamin=dataSnapshot.child("jenis_kelamin").getValue().toString();
                        String umur=dataSnapshot.child("umur").getValue().toString();
                        String bb=dataSnapshot.child("berat_badan").getValue().toString();
                        String tb=dataSnapshot.child("tinggi_badan").getValue().toString();

                        nama_user.setText(nama);
                        umur_user.setText(umur);
                        jk_user.setText(jenis_kelamin);
                        bb_user.setText(bb);
                        tb_user.setText(tb);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                updateProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void PilihGambar(View view) {
        //creating intent
        Intent intent = new Intent();
        //setting intent type  as image to select image from phone storage
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "pilih gambar"), IMAGE_REQUEST_CODE);
    }
}