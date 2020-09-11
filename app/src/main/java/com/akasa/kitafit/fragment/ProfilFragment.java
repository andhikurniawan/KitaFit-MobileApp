package com.akasa.kitafit.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.EditProfile;
import com.akasa.kitafit.activity.Profile;
import com.akasa.kitafit.activity.TutorialPenggunaanAplikasi;
import com.akasa.kitafit.activity.Login;

import com.akasa.kitafit.adapter.HistoryAdapter;
import com.akasa.kitafit.model.ProgramKesehatanData;
import com.akasa.kitafit.model.usermodel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfilFragment extends Fragment {
    Button editProfile, changepass, btn_tutorial, btn_logout;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference passRef;
    RecyclerView mRecyclerView;
    LayoutInflater inflater;
    View dialogView;
    private List<ProgramKesehatanData> listData;
    HistoryAdapter adapter;
    String UID;
    TextView mUsername;
    private FirebaseAuth firebaseAuth;
    String oldPass;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        context = getContext();
        editProfile = view.findViewById(R.id.btn_edit_profil);
        changepass = view.findViewById(R.id.btn_changepass);
        btn_tutorial = view.findViewById(R.id.btn_tutorial);
        btn_logout = view.findViewById(R.id.btn_logout);
        user = FirebaseAuth.getInstance().getCurrentUser();
        passRef = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("password");
        readUserPass();
        UID = user.getUid();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finishAffinity();
            }
        });
        btn_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TutorialPenggunaanAplikasi.class);
                startActivity(intent);
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetPassword = new EditText(v.getContext());
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter New Password > 6 Characters long.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        final String newPassword = resetPassword.getText().toString();
                        String email=user.getEmail();
                        AuthCredential credential = EmailAuthProvider.getCredential(email,oldPass);
                        Toast.makeText(context, "Sedang Mengupdate Password...", Toast.LENGTH_SHORT).show();
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            HashMap<String, Object> userMap = new HashMap<>();
                                            userMap. put("password", resetPassword.getText().toString());
                                            ref.child(UID).updateChildren(userMap);
                                            Toast.makeText(context, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else {
                                    Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passwordResetDialog.create().show();

            }


        });
        final CircularImageView profil = view.findViewById(R.id.imgprofil_user);
        mRecyclerView = view.findViewById(R.id.recyclerView_history);
        mUsername = view.findViewById(R.id.userprofil_nama);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        showListData();



        DatabaseReference databaseReference = firebaseDatabase.getReference("user");
        databaseReference.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usermodel user = dataSnapshot.getValue(usermodel.class);
                mUsername.setText(user.getNama_user());
                Glide.with(context.getApplicationContext())
                        .load(user.getFoto_user())
                        .centerCrop()
                        .into(profil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void readUserPass() {
        passRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                oldPass = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showListData() {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("program_kesehatan");
        final DatabaseReference pkref = FirebaseDatabase.getInstance().getReference("history_program");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listData = new ArrayList<>();
                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        pkref.child(firebaseAuth.getCurrentUser().getUid()).child("id_program_kesehatan").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                                        if (ds.child("id_program_kesehatan").getValue().toString().equals(ds2.getKey())) {
                                            System.out.println("Value " + ds.getValue());
                                            ProgramKesehatanData pk = ds.getValue(ProgramKesehatanData.class);
                                            listData.add(pk);
                                        }
                                    }
                                    adapter = new HistoryAdapter(getActivity(), listData);
                                    mRecyclerView.setAdapter(adapter);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public void GoToEdit(View view) {
//        Intent intent = new Intent(getActivity(), EditProfile.class);
//        startActivity(intent);
//    }
//
//    public void Logout(View view) {
//        firebaseAuth.signOut();
//        getActivity().finish();
//        startActivity(new Intent(getActivity(), Login.class));
//    }
//
//    public void ChangePass(View v) {
//        final EditText resetPassword = new EditText(v.getContext());
//
//        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
//        passwordResetDialog.setTitle("Reset Password ?");
//        passwordResetDialog.setMessage("Enter New Password > 6 Characters long.");
//        passwordResetDialog.setView(resetPassword);
//
//        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // extract the email and send reset link
//                String newPassword = resetPassword.getText().toString();
//                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getActivity(), "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getActivity(), "Password Reset Failed.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // close
//            }
//        });
//
//        passwordResetDialog.create().show();
//
//    }

}
