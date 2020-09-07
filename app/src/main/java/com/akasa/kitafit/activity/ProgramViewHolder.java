package com.akasa.kitafit.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;

public class ProgramViewHolder extends RecyclerView.ViewHolder {

    public TextView t1;
    public ImageView i1;
    public View v;


    public ProgramViewHolder(@NonNull View itemView) {
        super(itemView);

        t1 = itemView.findViewById(R.id.namaa);
        i1 = itemView.findViewById(R.id.gambarr);
        v = itemView;


    } }

