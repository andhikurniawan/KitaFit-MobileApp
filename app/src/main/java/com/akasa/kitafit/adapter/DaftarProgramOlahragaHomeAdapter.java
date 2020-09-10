package com.akasa.kitafit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.Olahraga;
import com.akasa.kitafit.activity.ProgramKesehatan;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DaftarProgramOlahragaHomeAdapter extends RecyclerView.Adapter<DaftarProgramOlahragaHomeAdapter.ViewHolder> {

    Context context;
    ArrayList<String> gambarList;
    ArrayList<String> judulList;
    Boolean isProgram;

    public DaftarProgramOlahragaHomeAdapter(Context context, ArrayList<String> gambarList, ArrayList<String> judulList, Boolean isProgram) {
        this.context = context;
        this.gambarList = gambarList;
        this.judulList = judulList;
        this.isProgram = isProgram;
    }

    @NonNull
    @Override
    public DaftarProgramOlahragaHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.program_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarProgramOlahragaHomeAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(gambarList.get(position))
                .centerCrop()
                .into(holder.gambar);
        holder.judul.setText(judulList.get(position));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isProgram == true){
                    context.startActivity(new Intent(context, ProgramKesehatan.class));
                } else if (isProgram == false){
                    context.startActivity(new Intent(context, Olahraga.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gambarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gambar;
        TextView judul;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.gambarr);
            judul = itemView.findViewById(R.id.namaa);
            relativeLayout = itemView.findViewById(R.id.programitemRelative);
        }
    }
}
