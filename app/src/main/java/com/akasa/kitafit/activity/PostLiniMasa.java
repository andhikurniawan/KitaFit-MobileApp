package com.akasa.kitafit.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.akasa.kitafit.R;
import com.bumptech.glide.Glide;

public class PostLiniMasa extends AppCompatActivity {
    private static final String TAG = "PostLiniMasa";
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    ImageView backButton, fotoPost;
    EditText captionField;
    Button postButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lini_masa);
        backButton = findViewById(R.id.back_button);
        fotoPost = findViewById(R.id.foto_post_linimasa_picture);
        captionField = findViewById(R.id.caption_post_linimasa_field);
        postButton = findViewById(R.id.button_post_linimasa);
        progressBar = findViewById(R.id.horizontalProgressBar);

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
                // Post Clicked
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

            Glide.with(this).load(mImageUri).into(fotoPost);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}