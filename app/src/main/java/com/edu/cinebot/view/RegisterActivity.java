package com.edu.cinebot.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.cinebot.R;
import com.edu.cinebot.entity.Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.txtUsuario)
    public EditText nombreUsuariotxt;
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
    }
    public void goToLoginActivity(View view) {
        if (validacion()){
            Usuario usuario = new Usuario(nombreUsuariotxt.getText().toString(),contrasenatxt.getText().toString(),
                    nombretxt.getText().toString(),apellidotxt.getText().toString(),null, identificacionTxt.getText().toString(),"CLIENTE");
            databaseReference.child("Usuario").child(usuario.getIdentificacion()).setValue(usuario);

            Toast.makeText(this,"Sea registrado el usuario satisfactoriamente",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"No se ha podido registrar el usuario",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validacion() {
        boolean validado = false;
        if (nombreUsuariotxt.getText().toString().equals("")){
            nombreUsuariotxt.setError("El nombre de usuario es requerido");
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
}
