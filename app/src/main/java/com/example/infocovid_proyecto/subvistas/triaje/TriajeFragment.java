package com.example.infocovid_proyecto.subvistas.triaje;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.infocovid_proyecto.subvistas.MainActivity;
import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.models.Triaje;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TriajeFragment extends Fragment {

    public static int contador =0;
    public static CheckBox cb1;
    public static CheckBox cb2;
    public static CheckBox cb3;
    public static CheckBox cb4;
    public static CheckBox cb5;
    private DatabaseReference mDatabase;
    public static Triaje triaje;

    Button btnContinuar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_triaje,container,false);
        settings(view);
        contador=0;
        listeners(savedInstanceState);
        MainActivity.navigationView.setCheckedItem(R.id.nav_triaje);
        triaje = new Triaje();
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
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String userID = users.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        checkChanges();

        mDatabase.child("Triaje").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int pre1=Integer.parseInt(snapshot.child("pre1").getValue().toString());
                    int pre2=Integer.parseInt(snapshot.child("pre2").getValue().toString());
                    int pre3=Integer.parseInt(snapshot.child("pre3").getValue().toString());
                    int pre4=Integer.parseInt(snapshot.child("pre4").getValue().toString());
                    int pre5=Integer.parseInt(snapshot.child("pre5").getValue().toString());
                    if(pre1==1){
                        cb1.setChecked(true);
                    }else {
                        cb1.setChecked(false);
                    }
                    if(pre2==1){
                        cb2.setChecked(true);
                    }else{
                        cb2.setChecked(false);
                    }
                    if(pre3==1){
                        cb3.setChecked(true);
                    }else{
                        cb3.setChecked(false);
                    }
                    if(pre4==1){
                        cb4.setChecked(true);
                    }else{
                        cb4.setChecked(false);
                    }
                    if(pre5==1){
                        cb5.setChecked(true);
                    }else{
                        cb5.setChecked(false);
                    }
                    btnContinuar.setText(R.string.actualizar);
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkChanges() {
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    contador++;
                    triaje.setPre1(1);
                }else{
                    triaje.setPre1(0);
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
                    triaje.setPre2(1);
                    contador++;
                }else{
                    triaje.setPre2(0);
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
                    triaje.setPre3(1);
                }else{
                    triaje.setPre3(0);
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
                    triaje.setPre4(1);
                    contador++;
                }else{
                    triaje.setPre4(0);
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
                    triaje.setPre5(1);
                    contador++;
                }else{
                    triaje.setPre5(0);
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