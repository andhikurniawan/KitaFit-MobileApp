package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.akasa.kitafit.R;
import com.akasa.kitafit.adapter.AdapterReminder;
import com.akasa.kitafit.adapter.NotifAlarm;
import com.akasa.kitafit.adapter.TodayAdapter;
import com.akasa.kitafit.fragment.ProfilFragment;
import com.akasa.kitafit.model.AppDatabase;
import com.akasa.kitafit.model.LiniMasaData;
import com.akasa.kitafit.model.ReminderData;
import com.akasa.kitafit.model.Reminders;
import com.akasa.kitafit.model.RoomDAO;
import com.akasa.kitafit.model.usermodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Reminderku extends AppCompatActivity {
    private ImageButton add;
    private Dialog dialog;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView, rc;
    private TextView empty, t_remind, listempty;
    private View v_remind;
    private ImageView img_remind;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    String UID;
    DatabaseReference mref;
    TextView mUsername;
    int idPost = 0;
    ArrayList<ReminderData> list;
    ArrayList<ReminderData> todayList;
    DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("h:mm a");
    DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    Locale locale = new Locale("id", "ID");
    long tanggalHariIniMillis;
    long tanggalBesokInMillis;
    long pilihanUserMillis;
    DatabaseReference idRef;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminderku);
        appDatabase = AppDatabase.geAppdatabase(Reminderku.this);
        idRef = FirebaseDatabase.getInstance().getReference("Reminder").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mUsername = findViewById(R.id.nama_user);
//        t_remind = findViewById(R.id.t_remind);
//        t_waktu = findViewById(R.id.t_waktu);
        final CircularImageView profil = findViewById(R.id.user_img);
        add = findViewById(R.id.fab_add);
        empty = findViewById(R.id.empty);
        t_remind = findViewById(R.id.ti_remind);
        v_remind=findViewById(R.id.view_remind);
        img_remind=findViewById(R.id.img_remind);
        listempty=findViewById(R.id.listempty);
        ImageButton btn_back=(ImageButton)findViewById(R.id.btn_bac_remind);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user");

        rc = findViewById(R.id.recyclerViewToday);
        rc.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(Reminderku.this, RecyclerView.HORIZONTAL, false);
        rc.setLayoutManager(linearLayoutManager2);

        todayReminderList();
        readIdPost();

        databaseReference.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usermodel user = dataSnapshot.getValue(usermodel.class);
                mUsername.setText(user.getNama_user()+"!");
                Picasso.get().load(user.getFoto_user()).into(profil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Reminderku.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminder();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Reminderku.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        setItemsInRecyclerView();
        showdata();

    }


    private void readIdPost() {
        idRef.child("counter_id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    idPost = Integer.parseInt(dataSnapshot.getValue().toString());
                } else {
                    idRef.child("counter_id").setValue(idPost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void todayReminderList() {
        todayList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Reminder").child(UID).child("id_reminder");
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        String dateSlash = "" + c.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + c.get(Calendar.YEAR);
        DateFormat format = new SimpleDateFormat("EEEEEEE, dd MMMM yyyy", locale);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse(dateSlash);
            long millis = date.getTime();
            tanggalHariIniMillis = millis;
            c.setTimeInMillis(millis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tanggalHariIni = DateFormat.getDateInstance(DateFormat.FULL, locale).format(c.getTime());
        System.out.println("Tanggal hari ini : " + tanggalHariIni + "\nDalam Millis : " + tanggalHariIniMillis);
        // tanggal besok
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        tanggalBesokInMillis = c.getTimeInMillis();
        System.out.println("Millis Besok : " + c.getTimeInMillis());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                todayList.clear();
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    if (Long.parseLong(ds1.child("timeInMillis").getValue().toString()) > tanggalHariIniMillis && Long.parseLong(ds1.child("timeInMillis").getValue().toString()) < tanggalBesokInMillis) {
                        ReminderData remind2 = ds1.getValue(ReminderData.class);
                        todayList.add(remind2);

                    }
                }
                if(todayList.size()>0){
                    empty.setVisibility(View.INVISIBLE);
                    t_remind.setVisibility(View.INVISIBLE);
                    v_remind.setVisibility(View.INVISIBLE);
                    img_remind.setVisibility(View.INVISIBLE);
                    rc.setVisibility(View.VISIBLE);
                }else{
                    empty.setVisibility(View.VISIBLE);
                    t_remind.setVisibility(View.VISIBLE);
                    v_remind.setVisibility(View.VISIBLE);
                    img_remind.setVisibility(View.VISIBLE);
                    rc.setVisibility(View.INVISIBLE);
                }
                TodayAdapter adapterReminder = new TodayAdapter(Reminderku.this, todayList);
                rc.setAdapter(adapterReminder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showdata() {
        list = new ArrayList<>();
        DatabaseReference refku = FirebaseDatabase.getInstance().getReference("Reminder").child(UID).child("id_reminder");
        refku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ReminderData remind = ds.getValue(ReminderData.class);
                    list.add(remind);
                }
                if(list.size()>0){
                    listempty.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                    listempty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }

                AdapterReminder adapterReminder = new AdapterReminder(Reminderku.this, list);
                recyclerView.setAdapter(adapterReminder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void setItemsInRecyclerView() {
//        RoomDAO dao = appDatabase.getRoomDAO();
//        temp = dao.orderThetable();
//        if(temp.size()>0) {
//            empty.setVisibility(View.INVISIBLE);
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//        adapter = new AdapterReminder(getApplicationContext(),temp);
//        recyclerView.setAdapter(adapter);
//    }

    private void addReminder() {
        dialog = new Dialog(Reminderku.this);
        dialog.setContentView(R.layout.floating_popup);

        final EditText dateAndTime = dialog.findViewById(R.id.Date_reminder);
        Button tambah;
        tambah = dialog.findViewById(R.id.btn_tambah_remind);
        final EditText message = dialog.findViewById(R.id.title_reminder);
        final TextView mTanggal = dialog.findViewById(R.id.tgl_remind);
        final TextView mJam = dialog.findViewById(R.id.jam_remind);


        //GetTanggal
        LocalDate today = LocalDate.now();
        final String tgl = today.format(formatterdate);

        //GetJam
        LocalTime now = LocalTime.now();
        final String jam = now.format(formattertime);

        final Calendar newCalender = Calendar.getInstance();
        dateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(Reminderku.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(Reminderku.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);
                                Calendar tem = Calendar.getInstance();
                                Log.w("TIME", System.currentTimeMillis() + "");
                                mTanggal.setText(tgl);
                                mJam.setText(jam);
                                if (newDate.getTimeInMillis() - tem.getTimeInMillis() > 0) {
                                    dateAndTime.setText(newDate.getTime().toString());
                                    pilihanUserMillis = newDate.getTimeInMillis();
                                }
                                else
                                    Toast.makeText(Reminderku.this, "Invalid time", Toast.LENGTH_SHORT).show();

                            }
                        }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                        time.show();

                    }
                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RoomDAO roomDAO = appDatabase.getRoomDAO();
//                Reminders reminders = new Reminders();
//                reminders.setMessage(message.getText().toString().trim());
//                Date remind = new Date(dateAndTime.getText().toString().trim());
//                reminders.setRemindDate(remind);
//                roomDAO.Insert(reminders);
//                List<Reminders> l = roomDAO.getAll();
//                reminders = l.get(l.size()-1);
//                Log.e("ID chahiye",reminders.getId()+"");

                String title = message.getText().toString().trim();
                String remind = dateAndTime.getText().toString().trim();
                Date remindtime = new Date(dateAndTime.getText().toString().trim());
                String tanggal = mTanggal.getText().toString().trim();
                String waktu = mJam.getText().toString().trim();
                //GetTanggal

                ReminderData reminderData = new ReminderData();
                idPost++;
                idRef.child("counter_id").setValue(idPost);
                reminderData.setId(idPost);
                reminderData.setTitle(title);
                reminderData.setDate_time(remind);
                reminderData.setJam(waktu);
                reminderData.setTanggal(tanggal);
                reminderData.setTimeInMillis(pilihanUserMillis);


                mref = FirebaseDatabase.getInstance().getReference().child("Reminder").child(UID).child("id_reminder");
                mref.child(String.valueOf(idPost)).setValue(reminderData);

                Calendar calendar = Calendar.getInstance(cal.getTimeZone());
                calendar.setTime(remindtime);
                calendar.set(Calendar.SECOND, 0);
                Intent intent = new Intent(Reminderku.this, NotifAlarm.class);
                intent.putExtra("Message", reminderData.getTitle());
                intent.putExtra("RemindDate", reminderData.getDate_time());
                intent.putExtra("Image", reminderData.getImg());
                intent.putExtra("id", reminderData.getId());
                PendingIntent intent1 = PendingIntent.getBroadcast(Reminderku.this, reminderData.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intent1);

                Toast.makeText(Reminderku.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
//                setItemsInRecyclerView();
//                AppDatabase.destroyInstance();
                dialog.dismiss();

            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}