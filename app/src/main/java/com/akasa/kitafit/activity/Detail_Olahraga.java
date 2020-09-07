package com.akasa.kitafit.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.OlahragaItem;
import com.akasa.kitafit.model.StepItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Detail_Olahraga extends AppCompatActivity {

    TextView t1, deskripsi, durasi, fokus_area, kalori;
    private String id = "";
    TextView url;
    VideoView video;
    ProgressDialog pd;
    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref;
    EditText inputSearch;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<StepItem> optionss;
    FirebaseRecyclerAdapter<StepItem, StepViewHolder> adapterr;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__olahraga);
        id = getIntent().getStringExtra("pid");

        t1 = findViewById(R.id.namaolahraga);
        deskripsi = findViewById(R.id.deskripsi_olahraga);
        durasi = findViewById(R.id.durasi);
        fokus_area = findViewById(R.id.fokus);
        kalori = findViewById(R.id.kalori);
        video = (VideoView) findViewById(R.id.video);
        pd = new ProgressDialog(Detail_Olahraga.this);
        url = (TextView) findViewById(R.id.text);
        pd.setMessage("Buffering please wait");
        pd.show();
    getVideo(id);
    getDetailOlahraga(id);
    getStep(id);
    }

    private void getStep(String id) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("daftar_olahraga").child(id).child("step");

        optionss = new FirebaseRecyclerOptions.Builder<StepItem>()
                .setQuery(databaseReference, StepItem.class).build();

        adapterr = new FirebaseRecyclerAdapter<StepItem, StepViewHolder>(optionss) {
            @Override
            protected void onBindViewHolder(@NonNull StepViewHolder holder, int position, @NonNull final StepItem model) {
                Picasso.get().load(model.getPoster()).into(holder.i1, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                holder.t1.setText(model.getDurasi());
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent e = new Intent(Detail_Olahraga.this,Detail_Olahraga.class);
                        startActivity(e);
                    }
                });
            }

            @NonNull
            @Override
            public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);

                return new StepViewHolder(view);
            }
        };


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView myList = (RecyclerView) findViewById(R.id.recyclerview2);
        myList.setLayoutManager(layoutManager);
        adapterr.startListening();
        recyclerView.setAdapter(adapterr);

    }

    private void getVideo(String id) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference().child("daftar_olahraga").child(id);
       DatabaseReference childreference = reference.child("link_video");
        childreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                Uri uri = Uri.parse(message);
                video.setVideoURI(uri);
                video.start();
                video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        pd.dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    private void getDetailOlahraga(String id) {
        DatabaseReference olgaRef = FirebaseDatabase.getInstance().getReference().child("daftar_olahraga").child(id);
        olgaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()) {
                    OlahragaItem olahraga = dataSnapshot.getValue(OlahragaItem.class);


                    t1.setText(olahraga.getNama_olahraga());
                    deskripsi.setText(olahraga.getDeskripsi());
                    durasi.setText(olahraga.getDurasi());
                    fokus_area.setText(olahraga.getFokus_area());
                    kalori.setText("~ " +olahraga.getKalori());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}