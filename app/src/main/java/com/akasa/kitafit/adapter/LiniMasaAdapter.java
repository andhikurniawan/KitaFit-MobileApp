package com.akasa.kitafit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.LiniMasaData;

import java.util.ArrayList;

public class LiniMasaAdapter extends RecyclerView.Adapter<LiniMasaAdapter.ViewHolder> {

    Context mContext;
    ArrayList<LiniMasaData> linimasaData;

    public LiniMasaAdapter(Context mContext, ArrayList<LiniMasaData> linimasaData) {
        this.mContext = mContext;
        this.linimasaData = linimasaData;
    }

    @NonNull
    @Override
    public LiniMasaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.linimasa_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiniMasaAdapter.ViewHolder holder, int position) {
        // nunggu model
    }

    @Override
    public int getItemCount() {
        return linimasaData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoUser, fotoPost;
        TextView namaUser, tanggalUser, likeUser, captionUser;
        Button sukaButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoUser = itemView.findViewById(R.id.linimasa_avatar);
            fotoPost = itemView.findViewById(R.id.linimasa_picture);
            namaUser = itemView.findViewById(R.id.linimasa_users_name);
            tanggalUser = itemView.findViewById(R.id.linimasa_date);
            likeUser = itemView.findViewById(R.id.like_amount);
            captionUser = itemView.findViewById(R.id.linimasa_caption);
            sukaButton = itemView.findViewById(R.id.linimasa_like_button);
        }
    }
}
