package com.akasa.kitafit.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.OlahragaItem;
import com.akasa.kitafit.model.ProgramItem;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {

    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref;
    EditText inputSearch;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<OlahragaItem> options;
    FirebaseRecyclerAdapter<OlahragaItem, OlahragaViewHolder> adapter;
    FirebaseRecyclerOptions<ProgramItem> optionss;
    FirebaseRecyclerAdapter<ProgramItem, ProgramViewHolder> adapterr;
    DatabaseReference databaseReference;
    ImageSlider mainslider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainslider=(ImageSlider)findViewById(R.id.image_slider);


olahraga();
program();
imageslider();


        TextView Nama = findViewById(R.id.namauser);
        TextView Umur = findViewById(R.id.umur);
        ImageView Foto = findViewById(R.id.user);

        infodisplay(Nama, Umur);


    }

    private void imageslider() {
        final List<SlideModel> remoteimages=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("informasi")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot data:dataSnapshot.getChildren())
                            remoteimages.add(new SlideModel(data.child("poster").getValue().toString(),data.child("caption").getValue().toString(), ScaleTypes.FIT));

                        mainslider.setImageList(remoteimages, ScaleTypes.FIT);

                        mainslider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                Toast.makeText(getApplicationContext(),remoteimages.get(i).getTitle().toString(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }



    private void program() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("program_kesehatan");

        optionss = new FirebaseRecyclerOptions.Builder<ProgramItem>()
                .setQuery(databaseReference, ProgramItem.class).build();

        adapterr = new FirebaseRecyclerAdapter<ProgramItem, ProgramViewHolder>(optionss) {
            @Override
            protected void onBindViewHolder(@NonNull ProgramViewHolder holder, int position, @NonNull final ProgramItem model) {
                Picasso.get().load(model.getGambar_program()).into(holder.i1, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                holder.t1.setText(model.getNama_program());

            }

            @NonNull
            @Override
            public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_item, parent, false);

                return new ProgramViewHolder(view);
            }
        };


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView myList = (RecyclerView) findViewById(R.id.recyclerview2);
        myList.setLayoutManager(layoutManager);
        adapterr.startListening();
        recyclerView.setAdapter(adapterr);

    }

    private void olahraga() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("daftar_olahraga");

        options = new FirebaseRecyclerOptions.Builder<OlahragaItem>()
                .setQuery(databaseReference, OlahragaItem.class).build();

        adapter = new FirebaseRecyclerAdapter<OlahragaItem, OlahragaViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OlahragaViewHolder holder, int position, @NonNull final OlahragaItem model) {
                Picasso.get().load(model.getPoster()).into(holder.i1, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                holder.t1.setText(model.getNama_olahraga());
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent e = new Intent(Homepage.this,Detail_Olahraga.class);
                        startActivity(e);
                    }
                });
            }

            @NonNull
            @Override
            public OlahragaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.olahraga_item2, parent, false);

                return new OlahragaViewHolder(view);
            }
        };


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView myList = (RecyclerView) findViewById(R.id.recyclerview);
        myList.setLayoutManager(layoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


    private void infodisplay(final TextView Nama, final TextView Umur) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("user").child("aXVtUnqvS4U4Ip699oyNzqt5jc03");
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("email").exists())
                    {
                        String umur = dataSnapshot.child("umur").getValue().toString();
                        String nama = dataSnapshot.child("nama_user").getValue().toString();

                        Umur.setText(umur);
                        Nama.setText(nama);

                    }
                }else{

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void olahraga(View view) {
        Intent b = new Intent(Homepage.this, Olahraga.class);
        startActivity(b);
    }
}
