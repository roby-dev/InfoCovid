package com.example.infocovid_proyecto.ui.mapa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infocovid_proyecto.MainActivity;
import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.interfaces.AlertaApi;
import com.example.infocovid_proyecto.models.API;
import com.example.infocovid_proyecto.models.Alertas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "Estilo del mapa";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    View rootView;

    private MapView mapView;

    public static List<Alertas> alertas = MainActivity.alertas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa2, container, false);
        return view;
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

        for (Alertas item : alertas) {
            LatLng latLng = new LatLng(item.getLat(), item.getLong());

            Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.virus)).position(latLng).title(item.getCountry()));
            marker.setTag(item);

            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker m) {
                    View v = LayoutInflater.from(getActivity()).inflate(R.layout.infowindow_layout, null);
                    String[] info = m.getTitle().split("&");
                    String url = m.getSnippet();
                    Alertas alertas = (Alertas) m.getTag();
                    Picasso.with(LayoutInflater.from(getActivity()).getContext()).load(alertas.getFlag()).into(((ImageView) v.findViewById(R.id.info_window_imagen)));
                    ;
                    ((TextView) v.findViewById(R.id.info_window_nombre)).setText("Fallecidos: " + alertas.getDeath());
                    ((TextView) v.findViewById(R.id.info_window_placas)).setText(alertas.getCountry());
                    ((TextView) v.findViewById(R.id.info_window_estado)).setText("Casos: " + alertas.getCases());
                    return v;
                }
            });


            enableMyLocation();

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getActivity()),item));
            //GroundOverlayOptions iconOverlay = new GroundOverlayOptions()
            //        .image(BitmapDescriptorFactory.fromResource(R.drawable.home))
             //       .position(tacna,100);
           // mMap.addGroundOverlay(iconOverlay);
        }
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