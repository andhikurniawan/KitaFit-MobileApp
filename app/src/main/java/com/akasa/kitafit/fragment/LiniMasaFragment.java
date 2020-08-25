package com.akasa.kitafit.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akasa.kitafit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class LiniMasaFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    FloatingActionButton floatingActionButton;
    public LiniMasaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lini_masa, container, false);
        recyclerView = view.findViewById(R.id.linimasa_recycler);
        context = getContext();
        floatingActionButton = view.findViewById(R.id.linimasa_fab);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    // Masih kosong karena belum ada model..
}