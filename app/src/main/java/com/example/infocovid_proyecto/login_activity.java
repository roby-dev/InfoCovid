package com.example.infocovid_proyecto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.infocovid_proyecto.models.User;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class login_activity extends FragmentActivity {

    public FirebaseAuth mFireBaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;

    CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private static final String TAG ="FacebookAuthentication";
    ProgressBar progressDialog;

    DatabaseReference mDatabase;

    private final static int RC_SIGN_IN = 123;

    private Button btnIngresar;
    private ImageButton btnGoogle;
    private EditText txtUsuario;
    private EditText txtPassword;
    private TextView btnRegistrar;
    private String email="";
    private String password="";
    private TextView btnRecuperar;
    private LoginButton loginButton;
    private Timer t;
    private int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        mFireBaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mFireBaseAuth.getCurrentUser();
                if(user != null){
                    goToMainScreen();
                }
            }
        };

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginFacebook);
        //loginButton.setReadPermissions(Arrays.asList("email","public_profile"));

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    mFireBaseAuth.signOut();;
                }
            }
        };

        btnGoogle = (ImageButton) findViewById(R.id.ivGoogle);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        txtUsuario=(EditText) findViewById(R.id.txtUsuario);
        txtPassword =(EditText) findViewById(R.id.txtPassword);
        btnRegistrar = (TextView) findViewById(R.id.txtRegistrar);
        btnRecuperar = (TextView) findViewById(R.id.txtRRecuperar);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setup();

    }

    public void buttonClickLoginFB(View v){

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                prog();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(login_activity.this, "User cancelled it", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(login_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void setup() {
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtUsuario.getText().toString();
                password = txtPassword.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(getApplicationContext(), "Ingrese Datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(login_activity.this,register_activity.class));
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRequest();
                signIn();
            }
        });

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_activity.this,password_recovery.class));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null){
            mFireBaseAuth.removeAuthStateListener((mAuthListener));
        }
    }

    private void handleFacebookAccessToken(@NotNull AccessToken accessToken) {
        Log.d(TAG,"handleFacebookoken"+accessToken);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFireBaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(login_activity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Error"+accessToken);
                }else{
                    Log.d(TAG,"Sign in with cretenditla succesful"+accessToken);
                    FirebaseUser user = mFireBaseAuth.getCurrentUser();
                    updateUI(user);
                    goToMainScreen();
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){

        }
    }


    public void prog(){
        progressDialog = (ProgressBar)findViewById(R.id.progressBar);
        progressDialog.setVisibility(View.VISIBLE);
        t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                progressDialog.setProgress(counter);
            }
        };
    }

    private void goToMainScreen(){
        if(progressDialog!=null){
            t.cancel();
        }
        if(mFireBaseAuth.getCurrentUser().equals(null)){
         Toast.makeText(login_activity.this,"HOLA",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(login_activity.this, "Bienvenido " + mFireBaseAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(login_activity.this,MainActivity.class));
        finish();
    }

    private void loginUser(){
         mFireBaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mFireBaseAuth.getCurrentUser();
                    goToMainScreen();
                }else{
                    Toast.makeText(login_activity.this, "Compruebe los datos", Toast.LENGTH_SHORT).show();
                }
             }
         });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFireBaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mFireBaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            prog();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mFireBaseAuth.getCurrentUser();
                            // saveInDataBase(user);
                            goToMainScreen();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(login_activity.this, "Error con google sign in", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}