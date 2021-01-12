package com.example.infocovid_proyecto.subvistas.triaje;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.infocovid_proyecto.subvistas.MainActivity;
import com.example.infocovid_proyecto.R;


public class fragment_triaje2 extends Fragment {

    public static EditText txtNacionalidad;
    public static EditText txtDocumentacion;
    public static EditText txtFechaNacimiento;
    Button btnContinuar;

    static boolean txt1=false;
    static boolean txt2=false;
    static boolean txt3=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_triaje2, container, false);
        settings(vista);
        listeners(savedInstanceState);
        MainActivity.navigationView.setCheckedItem(R.id.nav_triaje);
        return vista;
    }

    private void listeners(Bundle savedInstanceState) {

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.user.setNacionalidad(txtNacionalidad.getText().toString());
                MainActivity.user.setDocumento(txtDocumentacion.getText().toString());
                MainActivity.user.setFechaNacimiento(txtFechaNacimiento.getText().toString());
                TriajeFragment3 fr = new TriajeFragment3();
                fr.setArguments(savedInstanceState);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,fr)
                        .addToBackStack(null)
                        .commit();
            }
        });
        txtNacionalidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals("")){
                    txt1=false;
                }else{
                    txt1=true;
                }

                if(txt1&txt2&txt3){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtDocumentacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals("")){
                    txt2=false;
                }else{
                    txt2=true;
                }

                if(txt1&txt2&txt3){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtFechaNacimiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals("")){
                    txt3=false;
                }else{
                    txt3=true;
                }

                if(txt1&txt2&txt3){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void settings(View vista) {
        txtNacionalidad = (EditText)vista.findViewById(R.id.txtNacionalidad);
        txtDocumentacion = (EditText)vista.findViewById(R.id.txtDocumentacion);
        txtFechaNacimiento = (EditText)vista.findViewById(R.id.txtFechaNacimiento);
        btnContinuar = (Button) vista.findViewById(R.id.btnContinuarTriaje);

        txtNacionalidad.setText(MainActivity.user.getNacionalidad());
        txtDocumentacion.setText(MainActivity.user.getDocumento());
        txtFechaNacimiento.setText(MainActivity.user.getFechaNacimiento());

        if(!txtNacionalidad.getText().toString().isEmpty() & !txtDocumentacion.getText().toString().isEmpty() & !txtFechaNacimiento.getText().toString().isEmpty()){
            btnContinuar.setEnabled(true);
            txt1=true;
            txt2=true;
            txt3=true;
        }

    }
}