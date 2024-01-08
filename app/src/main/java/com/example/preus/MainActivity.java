package com.example.preus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner spSerie;
    private Spinner spModel;
    private ImageView ivModel;
    private RadioButton rbManual;
    private RadioButton rbAuto;
    private RadioButton rbGasoline;
    private RadioButton rbDiesel;
    private CheckBox cbPaint;
    private CheckBox cbLeather;
    private CheckBox cbNav;
    private List<String> series;
    private List<Model> models;
    private Map<String, List<Model>> mapaModel;
    private int autoPrice;
    private int dieselPrice;
    private int paintPrice;
    private int leatherPrice;
    private int navPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
        loadInfo();
        setSpinnerSeries();
    }

    private void loadInfo (){

        try {
            InputStream fraw = getResources().openRawResource(R.raw.datos_coches);
            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            series = new ArrayList<>();
            mapaModel = new HashMap<>();

        String line;
        while (!(line = brin.readLine()).equals("end")){
            series.add(line);
        }
        for (int i = 0; i<(int) series.stream().count(); i++){
            String serie = series.get(i);
            Model model;
            String name;
            String image;
            int preu;
            while (!(line = brin.readLine()).equals("end")){
                name = line;
                line = brin.readLine();
                preu = Integer.parseInt(line);
                line = brin.readLine();
                image = line;

                model = new Model(name, preu, image);

                models.add(model);
            }
            mapaModel.put(serie, models);
        }
        autoPrice = (Integer.parseInt(brin.readLine()));
        dieselPrice = (Integer.parseInt(brin.readLine()));
        paintPrice = (Integer.parseInt(brin.readLine()));
        leatherPrice = (Integer.parseInt(brin.readLine()));
        navPrice = (Integer.parseInt(brin.readLine()));

        fraw.close();
        } catch (Exception ex) {
            Log.e("Fitxers", " Error en llegir fitxer des de recurs raw");
        }
    }

    private void setSpinnerSeries(){
        ArrayAdapter<String> adapterSeries =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, series);

        adapterSeries.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spSerie.setAdapter(adapterSeries);

        spSerie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String serie = series.get(i);
                setSpinerSerie(serie);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setSpinerSerie(String serie){
        List<String> nomModel = new ArrayList<>();

        models = mapaModel.get(serie);
        for (int i = 0; i< (int) models.stream().count();i++){
            nomModel.add(models.get(i).getName());
        }

        ArrayAdapter<String> adapterSeries =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, nomModel);
        adapterSeries.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spModel.setAdapter(adapterSeries);

        spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initializer(){
        spSerie = findViewById(R.id.spSerie);
        spModel = findViewById(R.id.spModel);
        ivModel = findViewById(R.id.ivModel);
        rbManual = findViewById(R.id.rbManual);
        rbAuto = findViewById(R.id.rbAuto);
        rbGasoline = findViewById(R.id.rbGasoline);
        rbDiesel = findViewById(R.id.rbDiesel);
        cbPaint = findViewById(R.id.cbPaint);
        cbLeather = findViewById(R.id.cbLeather);
        cbNav = findViewById(R.id.cbNav);
    }

}