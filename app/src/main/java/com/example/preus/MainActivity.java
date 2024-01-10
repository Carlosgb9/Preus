package com.example.preus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

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
    private TextView tvPrice;
    private Map<String, List<String>> mapaNomModel = new HashMap<String, List<String>>();
    private Map<String, List<Integer>> mapaPreuModel = new HashMap<String, List<Integer>>();
    private Map<String, List<String>> mapaImageModel = new HashMap<String, List<String>>();
    private List<String> series = new ArrayList<>();
    private List<Integer> extras = new ArrayList<>();
    private int preuBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
        loadSeries();
        setSpinnerSeries();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void loadSeries (){
        InputStream fraw = getResources().openRawResource(R.raw.datos_coches);

        try (BufferedReader brin = new BufferedReader(new InputStreamReader(fraw))) {
            String line = brin.readLine();

            while (line != null) { // reads and saves BMW series' cars.
                while (!line.equals("end")) {
                    series.add(line);
                    line = brin.readLine();
                }
                line = brin.readLine();

                line = loadModels(line, brin); // reads and saves each car data.

                while (line != null) { // reads and saves the extras' prices.
                    extras.add(Integer.valueOf(line));
                    line = brin.readLine();
                }
            }

        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }
    }

    private String loadModels (String line, BufferedReader brin) throws IOException {
        int i = 0;
        int pos = 0;

        for (String seriesName : series) {
            List<String> modelNames = new ArrayList<>();
            List<Integer> modelPrice = new ArrayList<>();
            List<String> modelImage = new ArrayList<>();

            while (!line.equals("end")) { // Loops through each series data.
                switch (pos) {
                    case 0: {
                        modelNames.add(line);
                        break;
                    }
                    case 1: {
                        modelPrice.add(Integer.valueOf(line));
                        break;
                    }
                    case 2: {
                        modelImage.add(line);
                        break;
                    }
                }
                line = brin.readLine();
                i += 1;
                pos = i % 3;
            }
            mapaNomModel.put(seriesName, modelNames);
            mapaPreuModel.put(seriesName, modelPrice);
            mapaImageModel.put(seriesName, modelImage);
            line = brin.readLine();
        }
        return line;
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
                setSpinerSerie(adapterView, i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setSpinerSerie(AdapterView<?> adapterView, int i){
        String selectedSerie = adapterView.getItemAtPosition(i).toString();

        ArrayAdapter<String> adapterSeries =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, mapaNomModel.get(selectedSerie));
        adapterSeries.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spModel.setAdapter(adapterSeries);

        spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getModelPrice(i, selectedSerie);
                getModelImage(i, selectedSerie);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getModelPrice(int i, String selectedSerie){
        List<Integer> priceList = mapaPreuModel.get(selectedSerie);
        int selectedPrice = (int) priceList.get(i);

        String formattedPrice = formatPrice(selectedPrice);

        preuBase = selectedPrice;
        tvPrice.setText(formattedPrice);

        cbPaint.setChecked(false);
        cbLeather.setChecked(false);
        cbNav.setChecked(false);
        rbManual.setChecked(true);
        rbGasoline.setChecked(true);
    }

    private void getModelImage(int i, String selectedSerie){
        List<String> imageList = mapaImageModel.get(selectedSerie);
        String selectedImage = (String) imageList.get(i);

        int imageResource = getResources().getIdentifier(selectedImage, "drawable", getPackageName());
        ivModel.setImageResource(imageResource);
    }

    private String formatPrice(int price) {
        String format = "##,### €";
        DecimalFormat df = new DecimalFormat(format);
        String formattedPrice = df.format(price);

        return formattedPrice;
    }

    public void setPriceOnClick(View view){
        int preuFinal = preuBase;

        if (rbAuto.isChecked()) {preuFinal = preuFinal + extras.get(0);}
        if (rbDiesel.isChecked()) {preuFinal = preuFinal + extras.get(1);}
        if (cbPaint.isChecked()) {preuFinal = preuFinal + extras.get(2);}
        if (cbLeather.isChecked()) {preuFinal = preuFinal + extras.get(3);}
        if (cbNav.isChecked()) {preuFinal = preuFinal + extras.get(4);}

        String textPreu = formatPrice(preuFinal);
        tvPrice.setText(textPreu);
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
        tvPrice = findViewById(R.id.tvPrice);
    }

}