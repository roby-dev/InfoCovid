package com.example.infocovid_proyecto.ui.triaje;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.infocovid_proyecto.MainActivity;
import com.example.infocovid_proyecto.R;

public class TriajeFragment extends Fragment {

    public static int contador =0;
    public static CheckBox cb1;
    public static CheckBox cb2;
    public static CheckBox cb3;
    public static CheckBox cb4;
    public static CheckBox cb5;
    Button btnContinuar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_triaje,container,false);
        settings(view);
        listeners(savedInstanceState);
        return view;
    }

    private void listeners(Bundle savedInstanceState) {
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.user.getDocumento().isEmpty()){
                    fragment_triaje2 fr = new fragment_triaje2();
                    fr.setArguments(savedInstanceState);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment,fr)
                            .addToBackStack(null)
                            .commit();
                }else{
                    TriajeFragment5 fr = new TriajeFragment5();
                    fr.setArguments(savedInstanceState);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment,fr)
                            .addToBackStack(null)
                            .commit();
                }

            }
        });
    }

    private void settings(View view) {
        btnContinuar = (Button)view.findViewById(R.id.btnContinuarTriaje);
        cb1 = (CheckBox)view.findViewById(R.id.cb_t_1);
        cb2 = (CheckBox)view.findViewById(R.id.cb_t_2);
        cb3 = (CheckBox)view.findViewById(R.id.cb_t_3);
        cb4 = (CheckBox)view.findViewById(R.id.cb_t_4);
        cb5 = (CheckBox)view.findViewById(R.id.cb_t_5);
        checkChanges();
    }

    private void checkChanges() {
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    contador++;
                }else{
                    contador--;
                }
                if(contador>=2){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    contador++;
                }else{
                    contador--;
                }
                if(contador>=2){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }
        });
        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    contador++;
                }else{
                    contador--;
                }
                if(contador>=2){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }
        });
        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    contador++;
                }else{
                    contador--;
                }
                if(contador>=2){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }
        });
        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    contador++;
                }else{
                    contador--;
                }
                if(contador>=2){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }
        });
    }
}