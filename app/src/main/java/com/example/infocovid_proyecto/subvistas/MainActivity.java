package com.example.infocovid_proyecto.subvistas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.interfaces.AlertaApi;
import com.example.infocovid_proyecto.libraries.CircleTransform;
import com.example.infocovid_proyecto.models.API;
import com.example.infocovid_proyecto.models.Alertas;
import com.example.infocovid_proyecto.models.User;
import com.example.infocovid_proyecto.subvistas.ajustes.AjustesFragment;
import com.example.infocovid_proyecto.subvistas.prevencion.PrevencionFragment;
import com.example.infocovid_proyecto.subvistas.cifras.CifrasFragment;
import com.example.infocovid_proyecto.subvistas.home.HomeFragment;
import com.example.infocovid_proyecto.subvistas.inicio.InicioFragment;
import com.example.infocovid_proyecto.subvistas.mapa.MapaFragment;
import com.example.infocovid_proyecto.subvistas.noticias.NoticiasFragment;
import com.example.infocovid_proyecto.subvistas.triaje.TriajeFragment;
import com.example.infocovid_proyecto.vistas.login_activity;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private DrawerLayout drawer;
    public static NavigationView navigationView;

    private String url;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public static List<Alertas> alertas = new ArrayList<>();
    public static String imageUrl="";
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        Retrofit retrofit = API.getRetrofitClient();
        AlertaApi api = retrofit.create(AlertaApi.class);

        Call<List<JsonElement>> listCall = api.getAll();
        listCall.enqueue(new Callback<List<JsonElement>>() {
            @Override
            public void onResponse(Call<List<JsonElement>> call, Response<List<JsonElement>> response) {
                List<JsonElement> jsonElements = response.body();
                for(JsonElement item:jsonElements){
                    JsonObject jsonObject = item.getAsJsonObject();
                    Alertas alerta = new Alertas();

                    alerta.setCountry(jsonObject.get("country").getAsString());
                    alerta.setFlag(jsonObject.getAsJsonObject("countryInfo").get("flag").getAsString());
                    alerta.setLat(jsonObject.getAsJsonObject("countryInfo").get("lat").getAsDouble());
                    alerta.setLong(jsonObject.getAsJsonObject("countryInfo").get("long").getAsDouble());
                    alerta.setCases(jsonObject.get("cases").getAsInt());
                    alerta.setTodayCases(jsonObject.get("todayCases").getAsInt());
                    alerta.setDeath(jsonObject.get("deaths").getAsInt());
                    alerta.setTodayDeath(jsonObject.get("todayDeaths").getAsInt());

                    alertas.add(alerta);
                }
            }

            @Override
            public void onFailure(Call<List<JsonElement>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR, contacte con admin", Toast.LENGTH_SHORT).show();
            }
        });

        getSupportActionBar().show();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new InicioFragment()).addToBackStack(null).commit();
        navigationView.setCheckedItem(R.id.nav_inicio);

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            if(f instanceof InicioFragment){

            }else{
                super.onBackPressed();
                getFragmentManager().popBackStack();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void getUserInfo(){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user= new User();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    user.setName(snapshot.child("name").getValue().toString());
                    user.setLastname(snapshot.child("lastname").getValue().toString());
                    user.setEmail(snapshot.child("email").getValue().toString());
                    user.setPassword(snapshot.child("password").getValue().toString());
                    user.setDescription(snapshot.child("description").getValue().toString());
                    user.setSexo(snapshot.child("sexo").getValue().toString());
                    user.setCelular(snapshot.child("celular").getValue().toString());
                    user.setFamiliar(snapshot.child("familiar").getValue().toString());
                    user.setFamiliarTelefono(snapshot.child("familiarTelefono").getValue().toString());
                    user.setRegion(snapshot.child("region").getValue().toString());
                    user.setProvincia(snapshot.child("provincia").getValue().toString());
                    user.setDistrito(snapshot.child("distrito").getValue().toString());
                    user.setDireccion(snapshot.child("direccion").getValue().toString());
                    user.setLat(Double.parseDouble(snapshot.child("lat").getValue().toString()));
                    user.setLong(Double.parseDouble(snapshot.child("long").getValue().toString()));
                    user.setNacionalidad(snapshot.child("nacionalidad").getValue().toString());
                    user.setDocumento(snapshot.child("documento").getValue().toString());
                    user.setFechaNacimiento(snapshot.child("fechaNacimiento").getValue().toString());
                    user.setImagen(snapshot.child("imagen").getValue().toString());
                    if(!user.getImagen().isEmpty()){
                        if(!user.getImagen().contains("https")){
                            StorageReference mImageStorage = FirebaseStorage.getInstance().getReference();
                            StorageReference ref = mImageStorage.child("images").child(MainActivity.user.getImagen());
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        Uri downUri = task.getResult();
                                        imageUrl = downUri.toString();
                                        Picasso.with(MainActivity.this).load(imageUrl).transform(new CircleTransform()).into(photoImageView);
                                    }else{
                                        Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        Picasso.with(MainActivity.this).load(user.getImagen()).transform(new CircleTransform()).into(photoImageView);
                    }
                    if(user.getLastname().isEmpty()){
                        nameTextView.setText(user.getName());
                    }else{
                        nameTextView.setText(user.getLastname()+", "+user.getName());
                    }

                    emailTextView.setText(user.getEmail());
                }else{
                    FirebaseUser users = mAuth.getCurrentUser();
                    if(user!=null){
                        url=users.getPhotoUrl().toString();
                        if(url.contains("picture")){
                            url+="?type=large";
                        }
                        Log.d("IAMGEN",url);
                        nameTextView.setText(users.getDisplayName());
                        emailTextView.setText(users.getEmail());

                        Picasso.with(MainActivity.this).load(url).transform(new CircleTransform()).into(photoImageView);
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        String userID = users.getUid();
                        MainActivity.user.setEmail(users.getEmail());
                        MainActivity.user.setName(users.getDisplayName());
                        MainActivity.user.setImagen(url);
                        mDatabase.child("Users").child(userID).setValue(MainActivity.user);
                        imageUrl=MainActivity.user.getImagen();
                    }
                }

                Toast.makeText(MainActivity.this, getString(R.string.hola)+user.getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        nameTextView = (TextView) findViewById(R.id.txtLUsuario);
        photoImageView = (ImageView) findViewById(R.id.imgUsuario);
        emailTextView = (TextView) findViewById(R.id.txtLEmail);
        getUserInfo();

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_inicio:
                getSupportActionBar().show();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new InicioFragment()).addToBackStack(null).commit();
                break;

            case R.id.nav_mapa:
                getSupportActionBar().hide();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new MapaFragment()).addToBackStack(null).commit();
                break;

            case R.id.nav_triaje:
                getSupportActionBar().hide();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new TriajeFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_cifras:
                getSupportActionBar().hide();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new CifrasFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_alertas:
                getSupportActionBar().hide();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new PrevencionFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_noticias:
                getSupportActionBar().hide();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new NoticiasFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_ajustes:
                getSupportActionBar().hide();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new AjustesFragment()).addToBackStack(null).commit();
                break;

            case R.id.nav_cerrar:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Toast.makeText(MainActivity.this,R.string.bye, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, login_activity.class));
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}