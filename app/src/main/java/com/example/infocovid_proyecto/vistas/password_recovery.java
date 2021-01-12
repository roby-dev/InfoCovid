package com.example.infocovid_proyecto.vistas;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.infocovid_proyecto.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class password_recovery extends FragmentActivity {

    private EditText txtEmail;
    private Button btnRecuperar;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        mAuth=FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        txtEmail = (EditText) findViewById(R.id.txtReEmail);
        btnRecuperar = (Button) findViewById(R.id.btnReRecuperar);

        setUpActions();

    }

    private void setUpActions(){
        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                if(!email.isEmpty()){
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword(email);
                }else{
                    Toast.makeText(password_recovery.this, "Debe ingresar email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void resetPassword(String _email){
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(_email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(password_recovery.this, "Se ha enviado un correo para restablecer su contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(password_recovery.this, "No se pudo enviar correo de restablecimiento", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();
            }
        });
    }
}