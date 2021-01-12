package com.example.infocovid_proyecto.subvistas.prevencion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infocovid_proyecto.subvistas.MainActivity;
import com.example.infocovid_proyecto.R;

public class PrevencionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.navigationView.setCheckedItem(R.id.nav_alertas);
        View view = inflater.inflate(R.layout.fragment_alertas, container, false);
        return view;
    }
}