package com.example.infocovid_proyecto.ui.inicio;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infocovid_proyecto.MainActivity;
import com.example.infocovid_proyecto.R;

public class InicioFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        MainActivity.navigationView.setCheckedItem(R.id.nav_inicio);
        return view;
    }
}