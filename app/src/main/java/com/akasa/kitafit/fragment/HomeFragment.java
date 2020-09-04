package com.akasa.kitafit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.ProgramKesehatan;

public class HomeFragment extends Fragment {

    TextView seeAllProgramKesehatan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        seeAllProgramKesehatan = v.findViewById(R.id.see_all_program_kesehatan);
        seeAllProgramKesehatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), ProgramKesehatan.class));
            }
        });
        return v;
    }
}
