package com.akasa.kitafit.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.DetailProgramKesehatan;
import com.akasa.kitafit.activity.Detail_Olahraga;
import com.akasa.kitafit.activity.Homepage;
import com.akasa.kitafit.activity.Olahraga;
import com.akasa.kitafit.activity.OlahragaViewHolder;
import com.akasa.kitafit.activity.Profile;
import com.akasa.kitafit.activity.ProgramKesehatan;
import com.akasa.kitafit.activity.ProgramViewHolder;
import com.akasa.kitafit.activity.Reminderku;
import com.akasa.kitafit.adapter.DaftarProgramOlahragaHomeAdapter;
import com.akasa.kitafit.model.OlahragaItem;
import com.akasa.kitafit.model.ProgramItem;
import com.akasa.kitafit.model.ProgramKesehatanData;
import com.akasa.kitafit.model.usermodel;
import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class HomeFragment extends Fragment {

    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref;
    EditText inputSearch;
    RecyclerView recyclerViewOlga, recyclerViewProgram;
    FirebaseRecyclerOptions<OlahragaItem> options;
    FirebaseRecyclerAdapter<OlahragaItem, OlahragaViewHolder> adapter;
    FirebaseRecyclerOptions<ProgramItem> optionss;
    FirebaseRecyclerAdapter<ProgramItem, ProgramViewHolder> adapterr;
    DatabaseReference databaseReference, olgaRef, programRef;
    ImageSlider mainslider;
    ImageButton bell;
    ArrayList<String> gambarDaftar, gambarProgram;
    ArrayList<String> judulDaftar, judulProgram;
    Boolean isProgram, isOlga;
    Context context;

    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    String UID;
    TextView mUsername, mUmur;
    private FirebaseAuth firebaseAuth;

    TextView seeAllProgramKesehatan, seeAllOlahraga, judul;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        seeAllProgramKesehatan = v.findViewById(R.id.see_all_program_kesehatan);
        seeAllProgramKesehatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), ProgramKesehatan.class));
            }
        });

        seeAllOlahraga = v.findViewById(R.id.see_all_olahraga);
        seeAllOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), Olahraga.class));
            }
        });

        bell = v.findViewById(R.id.bell);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), Reminderku.class));
            }
        });

        judul = v.findViewById(R.id.judul);
        judul.setText("Hallo!");

        olgaRef = FirebaseDatabase.getInstance().getReference("daftar_olahraga");
        programRef = FirebaseDatabase.getInstance().getReference("program_kesehatan");

        context = getContext();

        recyclerViewOlga = v.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        recyclerViewOlga.setLayoutManager(linearLayoutManager);

        recyclerViewProgram = v.findViewById(R.id.recyclerview2);
        recyclerViewProgram.setLayoutManager(linearLayoutManager2);


        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainslider=(ImageSlider)v.findViewById(R.id.image_slider);


//        olahraga(v);
        readDataOlahraga();
//        program(v);
        readDataProgram();
        imageslider();

        user = FirebaseAuth.getInstance().getCurrentUser();
        UID=user.getUid();
        final ImageView profil = v.findViewById(R.id.user);
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user");
        mUsername = v.findViewById(R.id.namauser);
        mUmur = v.findViewById(R.id.umur);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usermodel user = dataSnapshot.getValue(usermodel.class);
                mUsername.setText(user.getNama_user());
                mUmur.setText(user.getUmur()+" tahun");
                if (user.getFoto_user() != null){
                    Glide.with(context.getApplicationContext())
                            .load(user.getFoto_user())
                            .centerCrop()
                            .into(profil);
                } else {
                    Glide.with(context)
                            .load(R.drawable.avatar_placeholder)
                            .centerCrop()
                            .into(profil);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    private void readDataProgram() {
        isProgram = true;
        programRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    gambarProgram = new ArrayList<>();
                    judulProgram = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        ProgramKesehatanData pkd = ds.getValue(ProgramKesehatanData.class);
                        gambarProgram.add(pkd.getGambar_program());
                        judulProgram.add(pkd.getNama_program());
                    }
                    DaftarProgramOlahragaHomeAdapter dpo = new DaftarProgramOlahragaHomeAdapter(getContext(), gambarProgram, judulProgram, isProgram);
                    recyclerViewProgram.setAdapter(dpo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readDataOlahraga() {
        isOlga = false;
        olgaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    gambarDaftar = new ArrayList<>();
                    judulDaftar = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        OlahragaItem oli = ds.getValue(OlahragaItem.class);
                        gambarDaftar.add(oli.getPoster());
                        judulDaftar.add(oli.getNama_olahraga());
                    }
                    DaftarProgramOlahragaHomeAdapter dpo = new DaftarProgramOlahragaHomeAdapter(getContext(), gambarDaftar, judulDaftar, isOlga);
                    recyclerViewOlga.setAdapter(dpo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                                Toast.makeText(getActivity(),remoteimages.get(i).getTitle().toString(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }



    private void program(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview2);
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

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView myList = (RecyclerView) v.findViewById(R.id.recyclerview2);
        myList.setLayoutManager(layoutManager);
        adapterr.startListening();
        recyclerView.setAdapter(adapterr);
    }

    private void olahraga(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
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
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                holder.t1.setText(model.getNama_olahraga());
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent e = new Intent(getActivity(), Detail_Olahraga.class);
                        e.putExtra("pid", model.getId());
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
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView myList = (RecyclerView) v.findViewById(R.id.recyclerview);
        myList.setLayoutManager(layoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


}
