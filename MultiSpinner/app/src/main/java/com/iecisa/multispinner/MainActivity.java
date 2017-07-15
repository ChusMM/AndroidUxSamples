package com.iecisa.multispinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        OptionValor[] array = {new OptionValor("primero", "PRIMERO"), new OptionValor("segun", "SEGUN"), new OptionValor("tercer", "TERCER")};
        MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner);
        if (multiSelectionSpinner != null) {
            multiSelectionSpinner.setItems(array);
            //multiSelectionSpinner.setSelection(new Integer[]{0, 2});

            multiSelectionSpinner.setSelection("primero|tercer");
            multiSelectionSpinner.setListener(this);
        }
    }

    @Override
    public void selectedIndices(List<Integer> indices) { }

    @Override
    public void selectedOptionValor(List<OptionValor> optionValorList) {
        Toast.makeText(this, optionValorList.toString(), Toast.LENGTH_LONG).show();
    }
}