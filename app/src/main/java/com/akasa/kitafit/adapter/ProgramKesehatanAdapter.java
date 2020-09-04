package com.akasa.kitafit.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.DetailProgramKesehatan;
import com.akasa.kitafit.model.ProgramKesehatanData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProgramKesehatanAdapter extends RecyclerView.Adapter<ProgramKesehatanAdapter.ViewHolder> {

    public static final String TAG = "ProgramKesehatanAdapter";
    public static final String ID_PROGRAM_KESEHATAN = "com.akasa.kitafit.id_program_kesehatan";
    public static final String GAMBAR_PROGRAM_KESEHATAN = "com.akasa.kitafit.gambar_program_kesehatan";
    public static final String KALORI_PROGRAM_KESEHATAN = "com.akasa.kitafit.kalori_program_kesehatan";
    public static final String NAMA_PROGRAM_KESEHATAN = "com.akasa.kitafit.nama_program_kesehatan";
    public static final String DESKRIPSI_PROGRAM_KESEHATAN = "com.akasa.kitafit.deskripsi_program_kesehatan";

    Context context;
    ArrayList<ProgramKesehatanData> list;

    public ProgramKesehatanAdapter(Context context, ArrayList<ProgramKesehatanData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.program_kesehatan_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context)
                .load(list.get(position).getGambar_program())
                .centerCrop()
                .into(holder.gambarProgram);
        holder.judulProgram.setText(list.get(position).getNama_program());
        holder.totalOlahraga.setText(String.valueOf(list.get(position).getTotal_olahraga()));
        holder.totalKalori.setText(String.valueOf(list.get(position).getTotal_kalori()));
        holder.durasiProgram.setText(String.valueOf(list.get(position).getDurasi_program()));
        holder.detailProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Detail Program : "+list.get(position).getNama_program());
                Intent intent = new Intent(context, DetailProgramKesehatan.class);
                intent.putExtra(ID_PROGRAM_KESEHATAN, String.valueOf(list.get(position).getId_program_Kesehatan()));
                intent.putExtra(GAMBAR_PROGRAM_KESEHATAN, list.get(position).getGambar_program());
                intent.putExtra(KALORI_PROGRAM_KESEHATAN, holder.totalKalori.getText().toString());
                intent.putExtra(NAMA_PROGRAM_KESEHATAN, holder.judulProgram.getText().toString());
                intent.putExtra(DESKRIPSI_PROGRAM_KESEHATAN, list.get(position).getDeskripsi());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gambarProgram;
        TextView judulProgram, totalOlahraga, totalKalori, durasiProgram;
        RelativeLayout detailProgram;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarProgram = itemView.findViewById(R.id.foto_program_kesehatan);
            judulProgram = itemView.findViewById(R.id.judul_program_kesehatan);
            totalOlahraga = itemView.findViewById(R.id.jumlah_olahraga_program_kesehatan);
            totalKalori = itemView.findViewById(R.id.jumlah_kalori_program_kesehatan);
            durasiProgram = itemView.findViewById(R.id.jumlah_durasi_program_kesehatan);
            detailProgram = itemView.findViewById(R.id.program_kesehatan_relative);
        }
    }
}
