package com.example.preus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> serie = new ArrayList<>();
    public static final List<Integer> AREAS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
        try {
            InputStream fraw = getResources().openRawResource(R.raw.datos_coches);
            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));
            fraw.close();
        } catch (Exception ex) {
            Log.e("Fitxers", " Error en llegir fitxer des de recurs raw");
        }
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

    private void readSeries(BufferedReader brin){
        String line = "";
        while(!line.equals("end")){

        }
    }

    private void readModel(BufferedReader brin){

    }

}