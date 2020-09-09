package com.akasa.kitafit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.ProgramKesehatanData;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context mContext;
    private List<ProgramKesehatanData> mData;

    public HistoryAdapter(Context mContext, List<ProgramKesehatanData> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.history_items,parent,false);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
    holder.mNamaProgram.setText(mData.get(position).getNama_program());
                Picasso.get().load(mData.get(position).getGambar_program()).into(holder.img_history);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView mNamaProgram;
        CircleImageView img_history;
        private View mView;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            mNamaProgram=mView.findViewById(R.id.name_history);
            img_history=mView.findViewById(R.id.img_history);


        }
    }
}
