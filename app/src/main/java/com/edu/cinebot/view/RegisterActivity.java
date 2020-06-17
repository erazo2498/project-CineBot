package com.edu.cinebot.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.cinebot.R;
import com.edu.cinebot.entity.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.txtEmail)
    public EditText emailtxt;
    @BindView(R.id.txtPassword)
    public EditText contrasenatxt;
    @BindView(R.id.txtPassword2)
    public EditText contrasena2txt;
    @BindView(R.id.txtNombre)
    public EditText nombretxt;
    @BindView(R.id.txtApellido)
    public EditText apellidotxt;
    @BindView(R.id.txtFechaNacimiento)
    public EditText fechaNacimientotxt;
    @BindView(R.id.txtIdentificacion)
    public EditText identificacionTxt;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }
    public void goToLoginActivity(View view) {
        if (validacion()){
            registrarUsuarioConAutenticacion(emailtxt.getText().toString(),contrasenatxt.getText().toString());
        }
        else{
            Toast.makeText(this,"No se ha podido registrar el usuario",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validacion() {
        boolean validado = false;
        if (emailtxt.getText().toString().equals("")){
            emailtxt.setError("El email es requerido");
        }
        else if (contrasenatxt.getText().toString().equals("")){
            contrasenatxt.setError("La contraseña es requerida");
        }
        else if (contrasena2txt.getText().toString().equals("")){
            contrasena2txt.setError("La contraseña es requerida");
        }
        else if (nombretxt.getText().toString().equals("")){
            nombretxt.setError("El nombre es requerido");
        }
        else if (apellidotxt.getText().toString().equals("")){
            apellidotxt.setError("El apellido es requerido");
        }
        else if (fechaNacimientotxt.getText().toString().equals("")){
            fechaNacimientotxt.setError("La fecha de nacimiento es requerida");
        }
        else if (identificacionTxt.getText().toString().equals("")){
            identificacionTxt.setError("La identificacion es requerida");
        }
        else if(!contrasenatxt.getText().toString().equals(contrasena2txt.getText().toString())){
            contrasena2txt.setError("La contraseña no coincide");
        }
        else{
            validado = true;
        }
        return validado;
    }
    private void crearUsuarioBaseDatos(){
        Usuario usuario = new Usuario(emailtxt.getText().toString(),contrasenatxt.getText().toString(),
                nombretxt.getText().toString(),apellidotxt.getText().toString(),null, identificacionTxt.getText().toString(),"CLIENTE");
        databaseReference.child("Usuario").child(usuario.getIdentificacion()).setValue(usuario);
    }
    private void registrarUsuarioConAutenticacion(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        crearUsuarioBaseDatos();
                        Toast.makeText(RegisterActivity.this,"Sea registrado el usuario satisfactoriamente",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            //Si el usuario ya existé
                            Toast.makeText(RegisterActivity.this, "el usuario ya existe.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                });
    }
}
