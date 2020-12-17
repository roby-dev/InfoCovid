package com.example.infocovid_proyecto.ui.triaje;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infocovid_proyecto.MainActivity;
import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.models.Alertas;
import com.example.infocovid_proyecto.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static androidx.core.content.ContextCompat.getSystemService;


public class TriajeFragment4 extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "Estilo del mapa";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    View rootView;
    Button btnUbication;
    private MapView mapView;
    private double MyLat=0.00;
    private double MyLong=0.00;
    private Location loc;
    private LocationManager locManager;
    EditText txtRegion;
    EditText txtProvincia;
    EditText txtDistrito;
    EditText txtDireccion;
    Button btnConfirmar;
    private DatabaseReference mDatabase;

    private boolean txt1=false;
    private boolean txt2=false;
    private boolean txt3=false;
    private boolean txt4=false;
    private boolean txt5=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_triaje4, container, false);
        settings(vista,savedInstanceState);
        return vista;
    }

    private void settings(View vista, Bundle savedInstanceState) {
        txtRegion=(EditText)vista.findViewById(R.id.txtTRegion);
        txtProvincia=(EditText)vista.findViewById(R.id.txtTProvincia);
        txtDistrito=(EditText)vista.findViewById(R.id.txtTDistrito);
        txtDireccion=(EditText)vista.findViewById(R.id.txtTDirección);
        btnUbication=(Button)vista.findViewById(R.id.btnSetUbication);
        btnUbication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(MyLat,MyLong);
                mMap.addMarker(new MarkerOptions().position(latLng).title("Mi ubicación"));
                txt5=true;
                if(txt1 & txt2 & txt3 &txt4 &txt5){
                    btnConfirmar.setEnabled(true);
                }
            }
        });

        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();

        btnConfirmar = (Button)vista.findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user = MainActivity.user;
                user.setLat(MyLat);
                user.setLong(MyLong);
                user.setRegion(txtRegion.getText().toString());
                user.setProvincia(txtProvincia.getText().toString());
                user.setDistrito(txtDistrito.getText().toString());
                user.setDireccion(txtDireccion.getText().toString());

                mDatabase = FirebaseDatabase.getInstance().getReference();
                String userID = users.getUid();
                mDatabase.child("Users").child(userID).setValue(MainActivity.user);
                Toast.makeText(getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();

                TriajeFragment5 fr = new TriajeFragment5();
                fr.setArguments(savedInstanceState);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,fr)
                        .addToBackStack(null)
                        .commit();
            }
        });

        txtRegion.addTextChangedListener(new TextWatcher() {
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

                if(txt1&txt2&txt3&txt4&txt5){
                    btnConfirmar.setEnabled(true);
                }else{
                    btnConfirmar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtProvincia.addTextChangedListener(new TextWatcher() {
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

                if(txt1&txt2&txt3&txt4&txt5){
                    btnConfirmar.setEnabled(true);
                }else{
                    btnConfirmar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtDistrito.addTextChangedListener(new TextWatcher() {
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

                if(txt1&txt2&txt3&txt4&txt5){
                    btnConfirmar.setEnabled(true);
                }else{
                    btnConfirmar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtDireccion.addTextChangedListener(new TextWatcher() {
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

                if(txt1&txt2&txt3&txt4&txt5){
                    btnConfirmar.setEnabled(true);
                }else{
                    btnConfirmar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.tMap);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getContext(), "No se han definido los permisos", Toast.LENGTH_SHORT).show();
            return;
        }else {
            locManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
            loc= locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            MyLat = loc.getLatitude();
            MyLong = loc.getLongitude();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoom = 16;


            LatLng tacna = new LatLng(-18.0090122,-70.2435313);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tacna,zoom));
            enableMyLocation();

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getActivity()),item));
            //GroundOverlayOptions iconOverlay = new GroundOverlayOptions()
            //        .image(BitmapDescriptorFactory.fromResource(R.drawable.home))
            //       .position(tacna,100);
            // mMap.addGroundOverlay(iconOverlay);


    }

    private void enableMyLocation() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager
                .PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        } else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_LOCATION_PERMISSION){

            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                enableMyLocation();
            }

        }
    }
}