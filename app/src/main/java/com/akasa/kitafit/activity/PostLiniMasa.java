package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.LiniMasaData;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PostLiniMasa extends AppCompatActivity {
    private static final String TAG = "PostLiniMasa";
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    ImageView backButton, fotoPost;
    EditText captionField;
    Button postButton;
    ProgressBar progressBar;
    DatabaseReference ref, counterRef, imageRef, userRef;
    StorageTask mUploadTask;
    StorageReference mStorageRef;
    int idPost = 0;
    String nama_user, foto_user, tanggal_post, foto_post, caption_post;
    int jumlah_like = 0;
    Locale locale = new Locale("id", "ID");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lini_masa);
        backButton = findViewById(R.id.back_button);
        fotoPost = findViewById(R.id.foto_post_linimasa_picture);
        captionField = findViewById(R.id.caption_post_linimasa_field);
        postButton = findViewById(R.id.button_post_linimasa);
        progressBar = findViewById(R.id.horizontalProgressBar);
        ref = FirebaseDatabase.getInstance().getReference("lini_masa");
        counterRef = FirebaseDatabase.getInstance().getReference("lini_masa").child("counter_id_post");
        userRef = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // cek counter post + user data
        cekCounterPost();
        getUserData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressBar.setVisibility(View.GONE);
        fotoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageUri == null){
                    Toast.makeText(PostLiniMasa.this, "Silakan Pilih Gambar Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(PostLiniMasa.this, "Proses Upload sedang berlangsung, mohon bersabar.", Toast.LENGTH_SHORT).show();
                } else {
                    if (mImageUri != null){
                        mStorageRef = FirebaseStorage.getInstance().getReference("liniMasaPost").child(String.valueOf(idPost));
                        StorageReference fileReference = mStorageRef.child(String.valueOf(idPost)+"_"+System.currentTimeMillis()+"."+getFileExtension(mImageUri));
                        mUploadTask = fileReference.putFile(mImageUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressBar.setProgress(0);
                                            }
                                        }, 2000);
                                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                foto_post = uri.toString();
                                                idPost++;
                                                counterRef.setValue(idPost);
                                                // cek hari ini
                                                Date c = Calendar.getInstance().getTime();
                                                System.out.println("Current time : "+c);

                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk.mm, dd MMMM yyyy", locale);
                                                tanggal_post = simpleDateFormat.format(c);
                                                Log.d(TAG, "Hari ini : "+tanggal_post);
                                                caption_post = captionField.getText().toString();
                                                LiniMasaData liniMasaData = new LiniMasaData(idPost, nama_user, foto_user, tanggal_post, foto_post, caption_post, jumlah_like);
                                                ref.child(String.valueOf(idPost)).setValue(liniMasaData);
                                                Log.d(TAG, "onSuccess: Sukses Post!");
                                            }
                                        });
                                        Toast.makeText(PostLiniMasa.this, "Sukses Menambahkan Post!", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(PostLiniMasa.this, "Ups ada kesalahan : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                        progressBar.setProgress((int) progress);
                                    }
                                });
                    } else {
                        Toast.makeText(PostLiniMasa.this, "Anda Belum Memilih Gambar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getUserData() {
        Log.d(TAG, "getUserData: getting User Data..");
        // get user name
        userRef.child("nama_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nama_user = snapshot.getValue().toString();
                Log.d(TAG, "onDataChange-UserName: "+nama_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        // get foto user
        userRef.child("foto_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foto_user = snapshot.getValue().toString();
                Log.d(TAG, "onDataChange-FotoUser: "+foto_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cekCounterPost() {
        Log.d(TAG, "cekCounterPost: Checking Counter...");
        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    idPost = Integer.parseInt(snapshot.getValue().toString());
                } else {
                    counterRef.setValue(idPost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Glide.with(this).load(mImageUri).centerCrop().into(fotoPost);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}