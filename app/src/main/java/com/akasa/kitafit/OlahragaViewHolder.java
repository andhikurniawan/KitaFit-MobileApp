package com.akasa.kitafit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OlahragaViewHolder extends RecyclerView.ViewHolder {

    TextView t1, deskripsi, durasi, fokus_area, kalori;
    ImageView i1;
    View v;


    public OlahragaViewHolder(@NonNull View itemView) {
        super(itemView);

        t1 = itemView.findViewById(R.id.nama);
        i1 = itemView.findViewById(R.id.gambar);
        deskripsi = itemView.findViewById(R.id.deskripsi_olahraga);
        durasi = itemView.findViewById(R.id.durasi);
        fokus_area = itemView.findViewById(R.id.fokus);
        kalori = itemView.findViewById(R.id.kalori);
        v = itemView;


    }
}