package com.akasa.kitafit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.LiniMasaData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LiniMasaAdapter extends RecyclerView.Adapter<LiniMasaAdapter.ViewHolder> {

    Context mContext;
    ArrayList<LiniMasaData> linimasaData;
    DatabaseReference ref, userLikedRef;
    private static final String TAG = "LiniMasaAdapter";
    int jumlahLike = 0;
    public LiniMasaAdapter(Context mContext, ArrayList<LiniMasaData> linimasaData) {
        this.mContext = mContext;
        this.linimasaData = linimasaData;
    }

    @NonNull
    @Override
    public LiniMasaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.linimasa_items, parent, false);
        ref = FirebaseDatabase.getInstance().getReference("lini_masa").child("id_post");
        userLikedRef = FirebaseDatabase.getInstance().getReference().child("liked_post").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LiniMasaAdapter.ViewHolder holder, final int position) {
        holder.namaUser.setText(linimasaData.get(position).getNama_user());
        holder.tanggalUser.setText(linimasaData.get(position).getTanggal_post());
        Glide.with(mContext)
                .load(linimasaData.get(position).getFoto_user())
                .centerCrop()
                .placeholder(R.drawable.avatar_placeholder)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.fotoUser);
        Glide.with(mContext)
                .load(linimasaData.get(position).getFoto_post())
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.fotoPost);
        holder.likeUser.setText(String.valueOf(linimasaData.get(position).getJumlah_like()));

        holder.captionUser.setText(linimasaData.get(position).getCaption_post());

        userLikedRef.child(String.valueOf(linimasaData.get(position).getId_post())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    holder.sukaButton.setText("Disukai");
                    holder.sukaButton.setBackground(mContext.getResources().getDrawable(R.drawable.blue_stroke_rounded_5dp));
                    holder.sukaButton.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.sukaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlahLike = Integer.parseInt(holder.likeUser.getText().toString());
                if (holder.sukaButton.getText().toString().equals("Suka")) {
                    jumlahLike++;
                    ref.child(String.valueOf(linimasaData.get(position).getId_post())).child("jumlah_like").setValue(jumlahLike);
                    userLikedRef.child(String.valueOf(linimasaData.get(position).getId_post())).setValue("Disukai");
                    holder.sukaButton.setText("Disukai");
                    holder.sukaButton.setBackground(mContext.getResources().getDrawable(R.drawable.blue_stroke_rounded_5dp));
                    holder.sukaButton.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                } else {
                    jumlahLike--;
                    ref.child(String.valueOf(linimasaData.get(position).getId_post())).child("jumlah_like").setValue(jumlahLike);
                    userLikedRef.child(String.valueOf(linimasaData.get(position).getId_post())).removeValue();
                    holder.sukaButton.setText("Suka");
                    holder.sukaButton.setBackground(mContext.getResources().getDrawable(R.drawable.button_blue_rounded_5dp));
                    holder.sukaButton.setTextColor(mContext.getResources().getColor(R.color.white));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return linimasaData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoUser, fotoPost;
        TextView namaUser, tanggalUser, likeUser, captionUser;
        Button sukaButton;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoUser = itemView.findViewById(R.id.linimasa_avatar);
            fotoPost = itemView.findViewById(R.id.linimasa_picture);
            namaUser = itemView.findViewById(R.id.linimasa_users_name);
            tanggalUser = itemView.findViewById(R.id.linimasa_date);
            likeUser = itemView.findViewById(R.id.like_amount);
            captionUser = itemView.findViewById(R.id.linimasa_caption);
            sukaButton = itemView.findViewById(R.id.linimasa_like_button);
            progressBar = itemView.findViewById(R.id.progressbarlingkaran);
        }
    }
}
