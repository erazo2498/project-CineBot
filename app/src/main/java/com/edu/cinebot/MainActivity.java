package com.edu.cinebot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.cinebot.adapter.MovieAdapter;
import com.edu.cinebot.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listViewMovies)
    public ListView listViewMovies;
    @BindView(R.id.txtSearch)
    public TextView txtSearch;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadInfo();
        searchOnTextListener();
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

        List<Movie> listMovies = new ArrayList<>();
        listMovies.add(new Movie(R.drawable.bloodshot,"Bloodshot" ,"Ray Garrison, un soldado de élite que murió en combate, es resucitado gracias a una avanzada tecnología que también le da la habilidad de fuerza sobrehumana y rápida recuperación. Basada en el comic."));
        listMovies.add(new Movie(R.drawable.bad_boys,"Bad Boys" ,"Marcus y Mike deben confrontar cambios de carrera y crisis de edad media, cuando se unen a un equipo de élite recién creado del departamento de policía de Miami para capturar al implacable Armando Armas, líder de un cartel de drogas."));
        listMovies.add(new Movie(R.drawable.jumanji,"Jumanji" ,"Sobre un juego"));
        movieAdapter= new MovieAdapter(this,listMovies);
        listViewMovies.setAdapter(movieAdapter);
    }
}
