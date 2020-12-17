package com.example.infocovid_proyecto.ui.ajustes;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.infocovid_proyecto.MainActivity;
import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.libraries.CircleTransform;
import com.example.infocovid_proyecto.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

public class AjustesFragment extends Fragment {


    private Button btnActualizar;
    private ImageButton fotoUsuario;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private static final int GALLERY_INTENT=1;
    private Uri imageUri;
    private DatabaseReference mDatabase;
    private static String link ="";

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       
         view = inflater.inflate(R.layout.fragment_ajustes,container,false);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        settings(view);
        listeners(savedInstanceState);


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
                uploadPicture();
                StorageReference mImageStorage = FirebaseStorage.getInstance().getReference();
                StorageReference ref = mImageStorage.child("images").child(MainActivity.user.getImagen());
                ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downUri = task.getResult();
                            String imageUrl = downUri.toString();
                        }else{
                            Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void settings(View view) {
        fotoUsuario = (ImageButton) view.findViewById(R.id.fotoUsuario);
        if(MainActivity.user.getImagen().isEmpty()){

        }else{
            Picasso.with(getContext()).load(MainActivity.imageUrl).transform(new CircleTransform()).into(fotoUsuario);
        }
        btnActualizar = (Button)view.findViewById(R.id.btnActualizarImagen);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode == Activity.RESULT_OK){
            imageUri = data.getData();
            fotoUsuario.setImageURI(imageUri);
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Subiendo imagen");
        pd.show();

        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+randomKey);

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
                                link = randomKey.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                      Toast.makeText(getContext(),"Failed to Upload",Toast.LENGTH_LONG).show();
                    }
                })
        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00* snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Procentaje: "+(int) progressPercent +" %");
            }
        });
    }
}