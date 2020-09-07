package com.akasa.kitafit.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;

public class StepViewHolder extends RecyclerView.ViewHolder {

    TextView t1;
    ImageView i1;
    View v;


    public StepViewHolder(@NonNull View itemView) {
        super(itemView);

        t1 = itemView.findViewById(R.id.namaa);
        i1 = itemView.findViewById(R.id.gambarr);
        v = itemView;

    }

}
