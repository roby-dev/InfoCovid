package com.example.infocovid_proyecto.ui.triaje;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infocovid_proyecto.R;

public class TriajeFragment5 extends Fragment {

      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View vista = inflater.inflate(R.layout.fragment_triaje5, container, false);
          settings(vista);
          return vista;
    }

    private void settings(View vista) {
    }
}