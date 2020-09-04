package com.akasa.kitafit.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.AppDatabase;
import com.akasa.kitafit.model.Reminders;
import com.akasa.kitafit.model.RoomDAO;

import java.util.List;

public class AdapterReminder extends RecyclerView.Adapter<AdapterReminder.MyViewHolder> {

    private AppDatabase appDatabase;
    private List<Reminders> allReminders;
    private TextView message,time;
    private Context mContext;
    public AdapterReminder(Context mContext, List<Reminders> allReminders) {
        this.allReminders = allReminders;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public AdapterReminder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterReminder.MyViewHolder holder, int position) {
        Reminders reminders = allReminders.get(position);
        holder.btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDatabase = AppDatabase.geAppdatabase(mContext.getApplicationContext());
                RoomDAO roomDAO = appDatabase.getRoomDAO();
                Reminders reminder = new Reminders();

                roomDAO.Delete(reminder);
                AppDatabase.destroyInstance();
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.myDialog));
                builder.setCancelable(true);
                builder.setTitle("Batalkan Reminder");
                builder.setMessage("Apakah anda yakin untuk membatalkan reminder ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();

            }
        });
        if(!reminders.getMessage().equals(""))
            message.setText(reminders.getMessage());
        else
            message.setHint("No Message");
        time.setText(reminders.getRemindDate().toString());

    }

    @Override
    public int getItemCount() {
        return allReminders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton btn_hapus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.judul_remind);
            time = itemView.findViewById(R.id.date_remind);
            btn_hapus=itemView.findViewById(R.id.btn_cancel);
        }
    }
}
