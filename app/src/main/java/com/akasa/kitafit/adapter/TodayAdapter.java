package com.akasa.kitafit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.AppDatabase;
import com.akasa.kitafit.model.ReminderData;

import java.util.List;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayViewHolder> {


    private List<ReminderData> mData;
    private Context mContext;
    public TodayAdapter(Context mContext,List<ReminderData> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public TodayAdapter.TodayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today_reminder,parent,false);
        return new TodayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TodayAdapter.TodayViewHolder holder, int position) {
        holder.message.setText(mData.get(position).getTitle());
        holder.time.setText(mData.get(position).getDate_time());
//        Picasso.get().load(mData.get(position).getImg()).into(holder.img);
//        Reminders reminders = allReminders.get(position);
//        holder.btn_hapus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                appDatabase = AppDatabase.geAppdatabase(mContext.getApplicationContext());
//                RoomDAO roomDAO = appDatabase.getRoomDAO();
//                Reminders reminder = new Reminders();
//
//                roomDAO.Delete(reminder);
//                AppDatabase.destroyInstance();
//            }
//        });
//        if(!reminders.getMessage().equals(""))
//            message.setText(reminders.getMessage());
//        else
//            message.setHint("No Message");
//        time.setText(reminders.getRemindDate().toString());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder {
        ImageButton btn_hapus;
        TextView message,time;
        ImageView img;
        private View mView;
        public TodayViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            message = mView.findViewById(R.id.t_remind);
            time = mView.findViewById(R.id.t_waktu);
            btn_hapus=mView.findViewById(R.id.btn_cancel);
            img=mView.findViewById(R.id.img_re);
        }
    }
}

