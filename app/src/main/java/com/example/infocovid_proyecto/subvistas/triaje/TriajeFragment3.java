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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.infocovid_proyecto.subvistas.MainActivity;
import com.example.infocovid_proyecto.R;

public class TriajeFragment3 extends Fragment {

    public static EditText txtNombres;
    public static EditText txtApellidos;
    public static RadioButton rbMasculino;
    public static RadioButton rbFemenino;
    public static EditText txtCelular;
    public static EditText txtEmai;
    public static EditText txtNombresFamiliar;
    public static EditText txtCelularFamiliar;
    public static EditText txtDescripcion;
    Button btnContinuar;

    private boolean txt1=false;
    private boolean txt2=false;
    private boolean txt3=false;
    private boolean txt4=false;
    private boolean txt5=false;
    private boolean txt6=false;
    private boolean txt7=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_triaje3, container, false);
        settings(vista);
        listener(savedInstanceState);
        MainActivity.navigationView.setCheckedItem(R.id.nav_triaje);
        return vista;
    }

    private void listener(Bundle savedInstanceState) {
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbFemenino.isChecked()){
                    MainActivity.user.setSexo("F");
                }else if(rbMasculino.isChecked()){
                    MainActivity.user.setSexo("M");
                }

                MainActivity.user.setCelular(txtCelular.getText().toString());
                MainActivity.user.setFamiliar(txtNombresFamiliar.getText().toString());
                MainActivity.user.setFamiliarTelefono(txtCelularFamiliar.getText().toString());
                MainActivity.user.setEmail(txtEmai.getText().toString());
                MainActivity.user.setName(txtNombres.getText().toString());
                MainActivity.user.setLastname(txtApellidos.getText().toString());

                TriajeFragment4 fr = new TriajeFragment4();
                fr.setArguments(savedInstanceState);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,fr)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void settings(View vista) {

        txtDescripcion =(EditText)vista.findViewById(R.id.txtTDescripcion);
        txtNombres=(EditText)vista.findViewById(R.id.txtTNombres);
        txtApellidos=(EditText)vista.findViewById(R.id.txtTApellidos);
        rbMasculino=(RadioButton)vista.findViewById(R.id.rbMasculino);
        rbFemenino=(RadioButton)vista.findViewById(R.id.rbFemenino);
        txtCelular =(EditText)vista.findViewById(R.id.txtTCelular);
        txtEmai=(EditText)vista.findViewById(R.id.txtTEmail);
        txtNombresFamiliar=(EditText)vista.findViewById(R.id.txtTFamiliar);
        txtCelularFamiliar=(EditText)vista.findViewById(R.id.txtTFamiliarCElular);
        btnContinuar=(Button)vista.findViewById(R.id.btnContinuarTriaje2);

        txtNombres.setText(MainActivity.user.getName());
        txtApellidos.setText(MainActivity.user.getLastname());
        if(MainActivity.user.getSexo().equals("F")){
            rbFemenino.setChecked(true);
        }else if(MainActivity.user.getSexo().equals("M")){
            rbMasculino.setChecked(true);
        }
        txtCelular.setText(MainActivity.user.getCelular());
        txtEmai.setText(MainActivity.user.getEmail());
        txtDescripcion.setText(MainActivity.user.getDescription());
        txtNombresFamiliar.setText(MainActivity.user.getFamiliar());
        txtCelularFamiliar.setText(MainActivity.user.getFamiliarTelefono());

        txtNombres.addTextChangedListener(new TextWatcher() {
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

                if(txt1&txt2&txt3&txt4&txt5&txt6&txt7){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtApellidos.addTextChangedListener(new TextWatcher() {
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

                if(txt1&txt2&txt3&txt4&txt5&txt6&txt7){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtCelular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals("")){
                    txt4=false;
                }else{
                    txt4=true;
                }

                if(txt1&txt2&txt3&txt4&txt5&txt6&txt7){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtEmai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals("")){
                    txt5=false;
                }else{
                    txt5=true;
                }

                if(txt1&txt2&txt3&txt4&txt5&txt6&txt7){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtNombresFamiliar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals("")){
                    txt6=false;
                }else{
                    txt6=true;
                }

                if(txt1&txt2&txt3&txt4&txt5&txt6&txt7){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtCelularFamiliar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals("")){
                    txt7=false;
                }else{
                    txt7=true;
                }

                if(txt1&txt2&txt3&txt4&txt5&txt6&txt7){
                    btnContinuar.setEnabled(true);
                }else{
                    btnContinuar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        RadioGroup radioGroup = (RadioGroup)vista.findViewById(R.id.rbGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                txt3=true;
            }
        });

        if(rbMasculino.isChecked() || rbFemenino.isChecked()){
            txt3=true;
        }

        if(!txtNombres.getText().toString().isEmpty()){
            txt1=true;
        }
        if(!txtApellidos.getText().toString().isEmpty()){
            txt2=true;
        }
        if(!txtEmai.getText().toString().isEmpty()){
            txt5=true;
        }

        if(!txtNombres.getText().toString().isEmpty() & !txtApellidos.getText().toString().isEmpty() & !txtCelular.getText().toString().isEmpty()
            & !txtEmai.getText().toString().isEmpty() & !txtNombresFamiliar.getText().toString().isEmpty() & !txtCelularFamiliar.getText().toString().isEmpty()  ){
            txt1=true;
            txt2=true;
            txt4=true;
            txt5=true;
            txt6=true;
            txt7=true;
            if(txt3){
                btnContinuar.setEnabled(true);
            }
        }
    }
}