package com.akasa.kitafit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.EditProfile;
import com.akasa.kitafit.activity.Profile;
import com.akasa.kitafit.activity.TutorialPenggunaanAplikasi;

public class ProfilFragment extends Fragment {
    Button editProfile, tutorial;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        editProfile = view.findViewById(R.id.button4);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        tutorial = view.findViewById(R.id.btn_tutorial);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TutorialPenggunaanAplikasi.class));
            }
        });
        return view;
    }
}
