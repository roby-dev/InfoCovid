package com.example.infocovid_proyecto.vistas;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.models.User;
import com.example.infocovid_proyecto.subvistas.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class register_activity extends FragmentActivity {

    private EditText txtEmail;
    private EditText txtNombre;
    private EditText txtPassword;
    private EditText txtApellido;
    private EditText txtDescripcion;
    private Button btnRegistrar;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        asignarObjetos();
        setUp();

    }

    private void asignarObjetos(){
        txtApellido = (EditText) findViewById(R.id.txtRApellidos);
        txtNombre = (EditText) findViewById(R.id.txtRNombres);
        txtEmail = (EditText) findViewById(R.id.txtREmail);
        txtPassword = (EditText) findViewById(R.id.txtRPassword);
        txtDescripcion = (EditText) findViewById(R.id.txtRDescripcion);
        btnRegistrar = (Button) findViewById(R.id.btnRRegistrar);
    }


    private void setUp(){
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setName(txtNombre.getText().toString());
                user.setLastname(txtApellido.getText().toString());
                user.setEmail(txtEmail.getText().toString());
                user.setPassword(txtPassword.getText().toString());
                user.setDescription(txtDescripcion.getText().toString());

                if(!user.getName().isEmpty() && !user.getLastname().isEmpty() && !user.getEmail().isEmpty() && !user.getPassword().isEmpty() && !user.getDescription().isEmpty()){
                    if(user.getPassword().length()>=6){
                        registerUser(user);
                    }else{
                        Toast.makeText(register_activity.this, "Password debe contener 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(register_activity.this, "Deber completar campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(User user){
        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",user.getName());
                    map.put("lastname",user.getLastname());
                    map.put("email",user.getEmail());
                    map.put("password",user.getPassword());
                    map.put("description",user.getDescription());
                    map.put("sexo","");
                    map.put("celular","");
                    map.put("familiar","");
                    map.put("familiarTelefono","");
                    map.put("region","");
                    map.put("provincia","");
                    map.put("distrito","");
                    map.put("direccion","");
                    map.put("lat",0);
                    map.put("long",0);
                    map.put("nacionalidad","");
                    map.put("documento","");
                    map.put("fechaNacimiento","");
                    map.put("imagen","");

                    String id= mAuth.getCurrentUser().getUid();
                            mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if(task2.isSuccessful()){
                                        Toast.makeText(register_activity.this, "Gracias por registrarte", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(register_activity.this, MainActivity.class));
                                        finish();
                                    }else{
                                        Toast.makeText(register_activity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(register_activity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}