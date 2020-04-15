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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.edu.cinebot.R;
import com.edu.cinebot.adapter.MovieAdapter;
import com.edu.cinebot.entity.Movie;
import com.edu.cinebot.view.InfoActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(myToolbar);
        ButterKnife.bind(this);
        loadInfo();
        searchOnTextListener();
        onMovieClickListener();
    }

    private void onMovieClickListener() {
            listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long ld) {
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
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
        List<Movie> listMovies = new ArrayList<>();
        listMovies.add(new Movie(R.drawable.bloodshot,"Bloodshot" ,"Ray Garrison, un soldado de élite que murió en combate, es resucitado gracias a una avanzada tecnología que también le da la habilidad de fuerza sobrehumana y rápida recuperación. Basada en el comic."));
        listMovies.add(new Movie(R.drawable.bad_boys,"Bad Boys" ,"Marcus y Mike deben confrontar cambios de carrera y crisis de edad media, cuando se unen a un equipo de élite recién creado del departamento de policía de Miami para capturar al implacable Armando Armas, líder de un cartel de drogas."));
        listMovies.add(new Movie(R.drawable.jumanji,"Jumanji" ,"Sobre un juego"));
        movieAdapter= new MovieAdapter(this,listMovies);
        listViewMovies.setAdapter(movieAdapter);
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
}
