package com.example.infocovid_proyecto.ui.triaje;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.infocovid_proyecto.MainActivity;
import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.models.Triaje;
import com.example.infocovid_proyecto.ui.home.HomeFragment;
import com.example.infocovid_proyecto.ui.inicio.InicioFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class TriajeFragment5 extends Fragment {

    private Button btnFinalizar;
    private AlertDialog alertM;
    private AlertDialog.Builder builder;
    private DatabaseReference mDatabase;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;
    private CheckBox cb6;
    private CheckBox cb7;
    private CheckBox cb8;


      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View vista = inflater.inflate(R.layout.fragment_triaje5, container, false);
          settings(vista,savedInstanceState);
          MainActivity.navigationView.setCheckedItem(R.id.nav_triaje);

          return vista;
    }

    private void settings(View vista, Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String userID = users.getUid();
        cb1 = (CheckBox)vista.findViewById(R.id.cbt_1);
        cb2 = (CheckBox)vista.findViewById(R.id.cbt_2);
        cb3 = (CheckBox)vista.findViewById(R.id.cbt_3);
        cb4 = (CheckBox)vista.findViewById(R.id.cbt_4);
        cb5 = (CheckBox)vista.findViewById(R.id.cbt_5);
        cb6 = (CheckBox)vista.findViewById(R.id.cbt_6);
        cb7 = (CheckBox)vista.findViewById(R.id.cbt_7);
        cb8 = (CheckBox)vista.findViewById(R.id.cbt_8);

        mDatabase.child("Triaje").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int pre6=Integer.parseInt(snapshot.child("pre6").getValue().toString());
                    int pre7=Integer.parseInt(snapshot.child("pre7").getValue().toString());
                    int pre8=Integer.parseInt(snapshot.child("pre8").getValue().toString());
                    int pre9=Integer.parseInt(snapshot.child("pre9").getValue().toString());
                    int pre10=Integer.parseInt(snapshot.child("pre10").getValue().toString());
                    int pre11=Integer.parseInt(snapshot.child("pre11").getValue().toString());
                    int pre12=Integer.parseInt(snapshot.child("pre12").getValue().toString());
                    int pre13=Integer.parseInt(snapshot.child("pre13").getValue().toString());
                    if(pre6==1){
                        cb1.setChecked(true);
                    }else {
                        cb1.setChecked(false);
                    }
                    if(pre7==1){
                        cb2.setChecked(true);
                    }else{
                        cb2.setChecked(false);
                    }
                    if(pre8==1){
                        cb3.setChecked(true);
                    }else{
                        cb3.setChecked(false);
                    }
                    if(pre9==1){
                        cb4.setChecked(true);
                    }else{
                        cb4.setChecked(false);
                    }
                    if(pre10==1){
                        cb5.setChecked(true);
                    }else{
                        cb5.setChecked(false);
                    }
                    if(pre11==1){
                        cb6.setChecked(true);
                    }else{
                        cb6.setChecked(false);
                    }
                    if(pre12==1){
                        cb7.setChecked(true);
                    }else{
                        cb7.setChecked(false);
                    }
                    if(pre13==1){
                        cb8.setChecked(true);
                    }else{
                        cb8.setChecked(false);
                    }
                    btnFinalizar.setText(R.string.actualizar);
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnFinalizar = (Button) vista.findViewById(R.id.btnFinalizarTriaje);
          builder = new AlertDialog.Builder(getContext());
          builder.setTitle(R.string.triajeTitle);
          builder.setMessage(R.string.mensajeTriaje);
          builder.setCancelable(false);
          builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  alertM.dismiss();
                  FragmentManager fm = getActivity().getSupportFragmentManager();
                  for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                      fm.popBackStack();
                  }
                  InicioFragment fr = new InicioFragment();
                  fr.setArguments(savedInstanceState);
                  getActivity().getSupportFragmentManager().beginTransaction()
                          .replace(R.id.nav_host_fragment,fr)
                          .commit();

              }
          });
          alertM = builder.create();
          alertM.setCanceledOnTouchOutside(false);

          btnFinalizar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  alertM.show();
                  FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                  String userID = users.getUid();

                  if(cb1.isChecked()){
                      TriajeFragment.triaje.setPre6(1);
                  }else{
                      TriajeFragment.triaje.setPre6(0);
                  }

                  if(cb2.isChecked()){
                      TriajeFragment.triaje.setPre7(1);
                  }else{
                      TriajeFragment.triaje.setPre7(0);
                  }

                  if(cb3.isChecked()){
                      TriajeFragment.triaje.setPre8(1);
                  }else{
                      TriajeFragment.triaje.setPre8(0);
                  }

                  if(cb4.isChecked()){
                      TriajeFragment.triaje.setPre9(1);
                  }else{
                      TriajeFragment.triaje.setPre9(0);
                  }

                  if(cb5.isChecked()){
                      TriajeFragment.triaje.setPre10(1);
                  }else{
                      TriajeFragment.triaje.setPre10(0);
                  }

                  if(cb6.isChecked()){
                      TriajeFragment.triaje.setPre11(1);
                  }else{
                      TriajeFragment.triaje.setPre11(0);
                  }

                  if(cb7.isChecked()){
                      TriajeFragment.triaje.setPre12(1);
                  }else{
                      TriajeFragment.triaje.setPre12(0);
                  }

                  if(cb8.isChecked()){
                      TriajeFragment.triaje.setPre13(1);
                  }else{
                      TriajeFragment.triaje.setPre13(0);
                  }

                  TriajeFragment.triaje.setFecha(new Date());

                  mDatabase.child("Triaje").child(userID).setValue(TriajeFragment.triaje);

              }
          });
    }
}