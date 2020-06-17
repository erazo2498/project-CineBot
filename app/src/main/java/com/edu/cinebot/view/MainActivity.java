package com.edu.cinebot.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.edu.cinebot.R;
import com.edu.cinebot.adapter.MovieAdapter;
import com.edu.cinebot.entity.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listViewMovies)
    public ListView listViewMovies;
    @BindView(R.id.txtSearch)
    public EditText txtSearch;
    @BindView(R.id.mytoolbar)
    public Toolbar myToolbar;
    private MovieAdapter movieAdapter;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private DatabaseReference myDatabase;
    public List<Movie> listMovies;
    private final int PICK_FOTO=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(myToolbar);
        ButterKnife.bind(this);
        cargandoInfoFirebase();
        loadInfo();
        searchOnTextListener();
        onMovieClickListener();
    }

    private void cargandoInfoFirebase() {
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void onMovieClickListener() {
            listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long ld) {
                    Movie movie = listMovies.get(position);
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movie", movie);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    private void searchOnTextListener() {
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                movieAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    private void loadInfo() {
        onCreateOptionsMenu(myToolbar.getMenu());
        //Sirve para reconocer el click de los item del menu de la toolbar
        myToolbar.setOnMenuItemClickListener(item -> onOptionsItemSelected(item));
        listMovies = new ArrayList<>();
        myDatabase.child("Peliculas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        String urlImagen = ds.child("url").getValue().toString();
                        String name = ds.child("nombre").getValue().toString();
                        String descripcion =ds.child("descripcion").getValue().toString();
                        listMovies.add(new Movie( urlImagen, name , descripcion));
                    }
                    movieAdapter= new MovieAdapter(getBaseContext(),listMovies);
                    listViewMovies.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Toast.makeText(this,"Has pulsado home",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_search:
                if (txtSearch.getVisibility() == View.GONE) {
                    txtSearch.setVisibility(View.VISIBLE);
                }else txtSearch.setVisibility(View.GONE);
                return true;
            case R.id.estrenos:
                if (txtSearch.getVisibility() == View.GONE) {

                }
                Toast.makeText(this,"Has pulsado estrenos",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.cartelera:
                if (txtSearch.getVisibility() == View.GONE) {

                }
                Toast.makeText(this,"Has pulsado cartelera",Toast.LENGTH_SHORT).show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToChatBotActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
        startActivity(intent);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void logout(View view) {
        Toast.makeText(this,"Has pulsado Logout",Toast.LENGTH_SHORT).show();
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
