package com.example.infocovid_proyecto.subvistas.ajustes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.infocovid_proyecto.subvistas.MainActivity;
import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.libraries.CircleTransform;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AjustesFragment extends Fragment {

    private Button btnActualizar;
    private ImageButton fotoUsuario;
    private EditText txtNombres;
    private EditText txtApellidos;
    private EditText txtEmail;
    private EditText txtCelular;
    View view;

    private StorageReference storageReference;
    private FirebaseStorage storage;
    private static final int GALLERY_INTENT=1;
    private Uri imageUri;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       
         view = inflater.inflate(R.layout.fragment_ajustes,container,false);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        settings(view);
        listeners(savedInstanceState);

        MainActivity.navigationView.setCheckedItem(R.id.nav_ajustes);

        return view;
    }

    private void listeners(Bundle savedInstanceState) {
        fotoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                String userID = users.getUid();
                MainActivity.user.setName(txtNombres.getText().toString());
                MainActivity.user.setLastname(txtApellidos.getText().toString());
                MainActivity.user.setCelular(txtCelular.getText().toString());

                mDatabase.child("Users").child(userID).setValue(MainActivity.user);
                Toast.makeText(getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void settings(View view) {
        fotoUsuario = (ImageButton) view.findViewById(R.id.fotoUsuario);
        txtNombres = (EditText) view.findViewById(R.id.txtANombres);
        txtApellidos = (EditText)view.findViewById(R.id.txtAApellidos);
        txtCelular = (EditText)view.findViewById(R.id.txtACelular);
        txtEmail = (EditText)view.findViewById(R.id.txtAEmail);
        txtNombres.setText(MainActivity.user.getName());
        txtApellidos.setText(MainActivity.user.getLastname());
        txtCelular.setText(MainActivity.user.getCelular());
        txtEmail.setEnabled(false);
        txtEmail.setText(MainActivity.user.getEmail());
        if(MainActivity.user.getImagen().isEmpty()){

        }else{
            if(!MainActivity.user.getImagen().contains("https")){
                StorageReference mImageStorage = FirebaseStorage.getInstance().getReference();
                StorageReference ref = mImageStorage.child("images").child(MainActivity.user.getImagen());
                ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downUri = task.getResult();
                            String imageUrl = downUri.toString();
                            Picasso.with(getContext()).load(imageUrl).transform(new CircleTransform()).into(fotoUsuario);
                        }
                    }
                });
            }else{
                Picasso.with(getContext()).load(MainActivity.user.getImagen()).transform(new CircleTransform()).into(fotoUsuario);
            }
        }
        btnActualizar = (Button)view.findViewById(R.id.btnActualizarImagen);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode == Activity.RESULT_OK){
            imageUri = data.getData();
            fotoUsuario.setImageURI(imageUri);
            fotoUsuario.setScaleType(ImageView.ScaleType.CENTER_CROP);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Subiendo imagen");
        pd.show();
        if(!MainActivity.user.getImagen().isEmpty()){
            StorageReference ref = storage.getReference();
            StorageReference deserRef =ref.child("images/"+MainActivity.user.getImagen());
            deserRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            });
        }
        final String randomKey= UUID.randomUUID().toString();
        MainActivity.user.setImagen((randomKey));
        StorageReference riversRef = storageReference.child("images/"+randomKey);
        try{
            riversRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(getContext(),"Image Uploaded",Toast.LENGTH_LONG).show();
                            FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            String userID = users.getUid();
                            MainActivity.user.setImagen(randomKey.toString());
                            mDatabase.child("Users").child(userID).setValue(MainActivity.user);
                            StorageReference mImageStorage = FirebaseStorage.getInstance().getReference();
                            StorageReference ref = mImageStorage.child("images").child(MainActivity.user.getImagen());
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        Uri downUri = task.getResult();
                                        String imageUrl = downUri.toString();
                                    }else{
                                        //Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            pd.dismiss();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progressPercent = (100.00* snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            pd.setMessage("Procentaje: "+(int) progressPercent +" %");
                        }
                    });
        }catch(Exception e){

        }

    }

}