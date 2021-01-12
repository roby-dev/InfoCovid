package com.example.infocovid_proyecto.subvistas.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.infocovid_proyecto.subvistas.MainActivity;
import com.example.infocovid_proyecto.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainActivity.navigationView.setCheckedItem(R.id.nav_home);
        return inflater.inflate(R.layout.fragment_home,container,false);
    }




}