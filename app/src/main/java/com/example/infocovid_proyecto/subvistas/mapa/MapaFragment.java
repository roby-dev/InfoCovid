package com.example.infocovid_proyecto.subvistas.mapa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infocovid_proyecto.subvistas.MainActivity;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "Estilo del mapa";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    View rootView;
    private DatabaseReference mDatabase;
    private MapView mapView;
    private List<User> usuarios = new ArrayList<>();
    private AutoCompleteTextView atDistrito;
    public static List<Alertas> alertas = MainActivity.alertas;
    public final int index = alertas.size();
    private static Context context;
    FloatingActionButton fbShow,fbAlert,fbCases;
    private AnimationUtils animationUtils;
    private Animation rotateOpen,rotateClose,fromBottom,toBottom;
    private boolean clicked =false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa2, container, false);
        MainActivity.navigationView.setCheckedItem(R.id.nav_mapa);
        context=view.getContext();
        String[] distritos = getResources().getStringArray(R.array.distritos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,distritos);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.bienvenido_mapas);
        builder.setMessage(R.string.select_map);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        settings(view,adapter);

        return view;
    }

    private void onAddButtonClicked(){
        setVisibility(clicked);
        setAnimation(clicked);
        setClickeable(clicked);
        clicked = !clicked;

    }

    private void setAnimation(boolean clicked) {
        if(!clicked){
            fbAlert.startAnimation(fromBottom);
            fbCases.startAnimation(fromBottom);
            fbShow.startAnimation(rotateOpen);
        }else{
            fbAlert.startAnimation(toBottom);
            fbCases.startAnimation(toBottom);
            fbShow.startAnimation(rotateClose);
        }
    }

    private void setVisibility(boolean clicked) {
        if(!clicked){
            fbAlert.setVisibility(View.VISIBLE);
            fbCases.setVisibility(View.VISIBLE);
        }else{
            fbAlert.setVisibility(View.INVISIBLE);
            fbCases.setVisibility(View.INVISIBLE);
        }
    }

    private void setClickeable(boolean clicked){
        if(!clicked){
            fbAlert.setEnabled(true);
            fbCases.setEnabled(true);
        }else{
            fbAlert.setEnabled(false);
            fbCases.setEnabled(false);
        }
    }

    private void settings(View view, ArrayAdapter<String> adapter) {

        atDistrito = (AutoCompleteTextView)view.findViewById(R.id.atMapa);
        atDistrito.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds:snapshot.getChildren()){
                        User user = new User();
                        user.setDescription(ds.child("description").getValue().toString());
                        user.setDistrito(ds.child("distrito").getValue().toString());
                        user.setLat(Double.parseDouble(ds.child("lat").getValue().toString()));
                        user.setLong(Double.parseDouble(ds.child("long").getValue().toString()));
                        user.setNacionalidad(ds.child("nacionalidad").getValue().toString());
                        usuarios.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        rotateOpen = animationUtils.loadAnimation(context,R.anim.rotate_open_anim);
        rotateClose = animationUtils.loadAnimation(context,R.anim.rotate_close_anim);
        fromBottom = animationUtils.loadAnimation(context,R.anim.from_bottom_anim);
        toBottom = animationUtils.loadAnimation(context,R.anim.to_bottom_anim);
        fbShow = (FloatingActionButton) view.findViewById(R.id.fbShow);
        fbAlert = (FloatingActionButton) view.findViewById(R.id.fbAlerts);
        fbCases = (FloatingActionButton) view.findViewById(R.id.fbCases);

        fbShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClicked();
            }
        });
        fbAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                for (User item : usuarios) {
                    if(item.getLat()!=0){
                        LatLng latLng = new LatLng(item.getLat(), item.getLong());

                        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(item.getEmail()));
                        marker.setTag(item);

                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                            @Override
                            public View getInfoWindow(Marker marker) {
                                return null;
                            }

                            @Override
                            public View getInfoContents(Marker m) {
                                View v = LayoutInflater.from(getActivity()).inflate(R.layout.infowindow_layout, null);
                                User user = (User) m.getTag();
                                Picasso.with(context).load("https://disease.sh/assets/img/flags/pe.png").into(((ImageView) v.findViewById(R.id.info_window_imagen)));
                                ((TextView) v.findViewById(R.id.info_window_nombre)).setText(getString(R.string.nacionalidad)+": "+user.getNacionalidad());
                                ((TextView) v.findViewById(R.id.info_window_placas)).setText("Metabolismo: "+user.getDescription());
                                ((TextView) v.findViewById(R.id.info_window_estado)).setText(getString(R.string.distrito)+": "+user.getDistrito());
                                return v;
                            }
                        });
                    }
                }
            }
        });

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');

        DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);

        fbCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                for (Alertas item : alertas) {
                    LatLng latLng = new LatLng(item.getLat(), item.getLong());

                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.virus)).position(latLng).title(item.getCountry()));
                    marker.setTag(item);

                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(Marker m) {
                            View v = LayoutInflater.from(getActivity()).inflate(R.layout.infowindow_layout, null);
                            Alertas alertas = (Alertas) m.getTag();
                            Picasso.with(context).load(alertas.getFlag()).into(((ImageView) v.findViewById(R.id.info_window_imagen)));
                            ((TextView) v.findViewById(R.id.info_window_nombre)).setText(view.getResources().getString(R.string.fallecidos)+": " + formatter.format(alertas.getDeath()));
                            ((TextView) v.findViewById(R.id.info_window_placas)).setText(alertas.getCountry());
                            ((TextView) v.findViewById(R.id.info_window_estado)).setText(view.getResources().getString(R.string.casos_confirmados)+": " + formatter.format(alertas.getCases()));
                            return v;
                        }
                    });
                }
            }
        });





        atDistrito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                LatLng latLng;
                float zoom = 15;
                switch (item.toString()){
                    case "CALANA":
                        latLng= new LatLng( -17.9408, -70.1828);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "CIUDAD NUEVA":
                        latLng= new LatLng( -17.985, -70.2381);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "CORONEL GREGORIO ALBARACCIN LANCHIPA":
                        latLng= new LatLng( -18.04, -70.2542);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "INCLAN":
                        latLng= new LatLng( -17.795,-70.495);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "LA YARADA DE LOS PALOS":
                        latLng= new LatLng( -18.2292,-70.4769);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "SAMA":
                        latLng= new LatLng( -17.865,-70.5619);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "PACHIA":
                        latLng= new LatLng( -17.8969,-70.1547);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "PALCA":
                        latLng= new LatLng( -12.6567,-74.9806);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "POCOLLAY":
                        latLng= new LatLng( -17.9964,-70.22);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "ALTO DE LA ALIANZA":
                        latLng= new LatLng( -17.9944,-70.2481);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                    case "TACNA":
                        zoom=13;
                        latLng= new LatLng(-18.0090122,-70.2435313);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
                        break;
                }
            }
        });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoom = 16;

        enableMyLocation();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

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