package com.akasa.kitafit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.OlahragaItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DaftarOlahragaPKAdapter extends RecyclerView.Adapter<DaftarOlahragaPKAdapter.ViewHolder> {

    Context context;
    ArrayList<OlahragaItem> list;
    int i = 1;
    public DaftarOlahragaPKAdapter(Context context, ArrayList<OlahragaItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DaftarOlahragaPKAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daftar_pola_olahraga_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarOlahragaPKAdapter.ViewHolder holder, int position) {
        holder.hari.setText("Hari "+i);
        Glide.with(context)
                .load(list.get(position).getPoster())
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .into(holder.gambarOlahraga);
        holder.olahraga.setText(list.get(position).getNama_olahraga());
        i++;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hari, olahraga;
        ImageView gambarOlahraga;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hari = itemView.findViewById(R.id.title_daftar);
            olahraga = itemView.findViewById(R.id.content_daftar);
            gambarOlahraga = itemView.findViewById(R.id.image_daftar);
        }
    }
}
