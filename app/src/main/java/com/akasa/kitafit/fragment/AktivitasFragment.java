package com.akasa.kitafit.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.Detail_Olahraga;
import com.akasa.kitafit.activity.OlahragaViewHolder;
import com.akasa.kitafit.activity.ProgramKesehatan;
import com.akasa.kitafit.activity.ProgramViewHolder;
import com.akasa.kitafit.activity.StepViewHolder;
import com.akasa.kitafit.model.AktivitasItem;
import com.akasa.kitafit.model.OlahragaItem;
import com.akasa.kitafit.model.PolaItem;
import com.akasa.kitafit.model.PolaMakanData;
import com.akasa.kitafit.model.ProgramItem;
import com.akasa.kitafit.model.ProgramKesehatanData;
import com.akasa.kitafit.model.StepItem;
import com.akasa.kitafit.model.usermodel;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AktivitasFragment extends Fragment {
    FirebaseRecyclerOptions<StepItem> optionss;
    FirebaseRecyclerAdapter<StepItem, StepViewHolder> adapterr;
    DatabaseReference databaseReference;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    String UID;
    private FirebaseAuth firebaseAuth;;
    ImageView backButton, gambarProgram;
    TextView namaProgram, totalKalori, deskripsiProgram;
    RecyclerView polaMakanRecycler, daftarOlahragaRecycler;
    Button mulaiLatihanButton;
    View sarapanview, makanSiangView, makanMalamView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aktivitas, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("user");
        DatabaseReference aktivitas = firebaseDatabase.getReference("aktivitas_user");
        DatabaseReference opk = firebaseDatabase.getReference("daftar_olahraga_pk");
        DatabaseReference pk = firebaseDatabase.getReference("program_kesehatan");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();
        final ImageView profil = v.findViewById(R.id.user);
        final TextView mHari = v.findViewById(R.id.hari);
        final TextView mNama = v.findViewById(R.id.namaprogram);


        gambarProgram = v.findViewById(R.id.gambar_detail_program_kesehatan);
        namaProgram = v.findViewById(R.id.nama_program_detail_kesehatan);
        totalKalori = v.findViewById(R.id.jumlah_kalori_detail_program_kesehatan);
        deskripsiProgram = v.findViewById(R.id.deskripsi_detail_program_kesehatan);
        daftarOlahragaRecycler = v.findViewById(R.id.daftar_olahraga_recycler);
        mulaiLatihanButton = v.findViewById(R.id.mulai_latihan_button);
        sarapanview = v.findViewById(R.id.sarapan);
        makanSiangView = v.findViewById(R.id.makanSiang);
        makanMalamView = v.findViewById(R.id.makanMalam);


        databaseReference.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usermodel user = dataSnapshot.getValue(usermodel.class);
                if (user.getFoto_user() != null) {
                    Picasso.get().load(user.getFoto_user()).into(profil);
                } else {
                    Picasso.get().load(R.drawable.placeholder_avatar_human).into(profil);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        aktivitas.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AktivitasItem aktivitas = dataSnapshot.getValue(AktivitasItem.class);
                ProgramItem pk = dataSnapshot.getValue(ProgramItem.class);
                
                if (aktivitas.getCounter_hari() != null) {
                    mHari.setText("Hari Ke - " + aktivitas.getCounter_hari());
                } else {
                    mHari.setText("Belum Mengambil Program");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        program(v);
        polamakan(v);
        olahraga(v);
        video(v);
//        step(v);

        return v;
    }

//    private void step(View v) {
//        RecyclerView recyclerView = v.findViewById(R.id.recyclerview4);
//        recyclerView.setHasFixedSize(true);
//        DatabaseReference sref = FirebaseDatabase.getInstance().getReference().child("daftar_olahraga").child("1").child("step");
//
//        optionss = new FirebaseRecyclerOptions.Builder<StepItem>()
//                .setQuery(sref, StepItem.class).build();
//
//        adapterr = new FirebaseRecyclerAdapter<StepItem, StepViewHolder>(optionss) {
//            @Override
//            protected void onBindViewHolder(@NonNull StepViewHolder holder, int position, @NonNull final StepItem model) {
//                Picasso.get().load(model.getPoster()).into(holder.i1, new Callback() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @SuppressLint("RestrictedApi")
//                    @Override
//                    public void onError(Exception e) {
//                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//                holder.t1.setText(model.getDurasi());
//                holder.n1.setText("Step " + model.getNomor());
//            }
//
//            @NonNull
//            @Override
//            public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
//
//                return new StepViewHolder(view);
//            }
//        };
//
//
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//
//        RecyclerView myList = (RecyclerView) v.findViewById(R.id.recyclerview4);
//        myList.setLayoutManager(layoutManager);
//        adapterr.startListening();
//        recyclerView.setAdapter(adapterr);
//    }

    private void video(View v) {
        final VideoView video = (VideoView) v.findViewById(R.id.video);
        TextView url = (TextView) v.findViewById(R.id.text);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference().child("daftar_olahraga").child("1");
        DatabaseReference childreference = reference.child("link_video");
        final MediaController mediaController = new MediaController(getContext());
        childreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                Uri uri = Uri.parse(message);
                video.setVideoURI(uri);
                final ProgressDialog pd = new ProgressDialog(getContext());


                mediaController.setAnchorView(video);
                video.setMediaController(mediaController);
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

    private void olahraga(View v) {
        final TextView t1 = v.findViewById(R.id.namaolahraga);
        final TextView deskripsi = v.findViewById(R.id.deskripsi_olahraga);
        final TextView durasi = v.findViewById(R.id.durasi);
        final TextView fokus_area = v.findViewById(R.id.fokus);
        final TextView kalori = v.findViewById(R.id.kalori);

        DatabaseReference olga = firebaseDatabase.getReference("daftar_olahraga");

        olga.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                OlahragaItem olga = dataSnapshot.getValue(OlahragaItem.class);
                    t1.setText(olga.getNama_olahraga());
                    deskripsi.setText(olga.getDeskripsi());
                    durasi.setText(olga.getDurasi());
                    fokus_area.setText(olga.getFokus_area());
                    kalori.setText("~ " +olga.getKalori());
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void polamakan(View v) {
        final TextView menup = v.findViewById(R.id.menusarapan);
        final TextView menus = v.findViewById(R.id.menusiang);
        final TextView menum = v.findViewById(R.id.menumalam);
        final ImageView posterp = v.findViewById(R.id.gambar2);
        final ImageView posters = v.findViewById(R.id.gambar3);
        final ImageView posterm = v.findViewById(R.id.gambar);
        DatabaseReference pm = firebaseDatabase.getReference("pola");
        pm.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PolaItem pm = dataSnapshot.getValue(PolaItem.class);
                menup.setText(pm.getPmenu());
                menus.setText(pm.getSmenu());
                menum.setText(pm.getMmenu());
                if (pm.getPposter() != null){
                    Picasso.get().load(pm.getPposter()).into(posterp);
                } else {
                    Picasso.get().load(R.drawable.placeholder_avatar_human).into(posterp);
                }
                if (pm.getSposter() != null){
                    Picasso.get().load(pm.getSposter()).into(posters);
                } else {
                    Picasso.get().load(R.drawable.placeholder_avatar_human).into(posters);
                }
                if (pm.getMposter() != null){
                    Picasso.get().load(pm.getMposter()).into(posterm);
                } else {
                    Picasso.get().load(R.drawable.placeholder_avatar_human).into(posterm);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void program(View v) {
        final TextView mNama = v.findViewById(R.id.namaprogram);
        DatabaseReference pk = firebaseDatabase.getReference("program_kesehatan");
        pk.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProgramItem pk = dataSnapshot.getValue(ProgramItem.class);
                mNama.setText(pk.getNama_program());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}







