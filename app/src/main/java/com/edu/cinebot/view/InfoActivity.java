package com.edu.cinebot.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.edu.cinebot.R;
import com.edu.cinebot.adapter.MovieAdapter;
import com.edu.cinebot.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {
    @BindView(R.id.listViewMovies)
    public ListView listViewMovies;
    @BindView(R.id.mytoolbar)
    public Toolbar myToolbar;
    public TableLayout tablainformativa;
    private String[] header={"Sala","Horario","Dimension","Valor"};
    private ArrayList<String[]> rows= new ArrayList<>();
    private TableDinamic tablaDinamica;
    private MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setSupportActionBar(myToolbar);
        ButterKnife.bind(this);
        loadInfo();
    }

    private void loadInfo() {
        myToolbar.setTitleTextColor(Color.WHITE);
        myToolbar.setTitle("Informacion de la pelicula");
        /*List<Movie> listMovies = new ArrayList<>();
        listMovies.add(new Movie(R.drawable.bloodshot,"Bloodshot" ,"Ray Garrison, un soldado de élite que murió en combate, es resucitado gracias a una avanzada tecnología que también le da la habilidad de fuerza sobrehumana y rápida recuperación. Basada en el comic."));
        movieAdapter= new MovieAdapter(this,listMovies);*/
        listViewMovies.setAdapter(movieAdapter);


        tablainformativa = findViewById(R.id.tablaInformativa);
        tablaDinamica = new TableDinamic(tablainformativa,getApplicationContext());
        tablaDinamica.addHeader(header);
        tablaDinamica.addData(getInformacion());
        tablaDinamica.backGroundHaeder(R.color.colorPrimary);
        tablaDinamica.backGroundData(Color.WHITE,Color.LTGRAY);
        tablaDinamica.lineColor(R.color.colorPrimary);
        tablaDinamica.txtColorData(Color.BLACK);
        tablaDinamica.txtColorHeader(Color.WHITE);
    }

    public void save(View view){
        // String[] item= new String[]{Los datos necesarios}
        // tablaDinamica.addItems(item);
    }
    private ArrayList<String[]> getInformacion(){
        rows.add(new String[]{"2","2pm-4pm","2D","9000"});
        rows.add(new String[]{"3","2pm-4pm","3D","12000"});
        rows.add(new String[]{"2","4pm-6pm","2D","9000"});
        rows.add(new String[]{"4","5pm-7pm","2D","9000"});
        rows.add(new String[]{"1","3pm-5pm","3D","13000"});
        return rows;
    }
}
