package com.iecisa.expandableviewexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends AppCompatActivity {
    private Page page;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_container);

        this.prepareListData();

        //ScrollView scroll = (ScrollView) findViewById(R.id.page_list_view);

        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout pageContainer = (LinearLayout) findViewById(R.id.page_layout_container);
        UiInflater.inflatePage(pageContainer, page, getBaseContext());

        //scroll.addView(pageContainer);

        /*ListView pageContainer = (ListView) findViewById(R.id.page_list_view);
        UiInflater.inflatePage(pageContainer, page, getBaseContext());*/
    }

    private void prepareListData() {
        // Adding child data

        List<ControlRow> controls1 = new ArrayList<>();
        controls1.add(new ControlRow("Nombre", "Jesus", false));
        controls1.add(new ControlRow("Dirección", "Alcázar de San Juan", true));
        controls1.add(new ControlRow("Residente Madrid?", false));
        controls1.add(new ControlRow("Ventas", "10"));

        List<ControlRow> controls5 = new ArrayList<>();
        controls5.add(new ControlRow("Nombre", "Napster", false));
        controls5.add(new ControlRow("Dirección", "Madrid", true));
        controls5.add(new ControlRow("Residente Madrid?", true));
        controls5.add(new ControlRow("Ventas", "30"));


        List<ControlRow> controls2 = new ArrayList<>();
        controls2.add(new ControlRow("Perfil", "Perfil línea 5 Gb", false));
        controls2.add(new ControlRow("Oficina plus", "Sólo fijo", true));
        controls2.add(new ControlRow("Extensión fija?", true));
        controls2.add(new ControlRow("Número de líneas", 10));
        controls2.add(new ControlRow("Tipo tarifa", "Delfín", new String[]{"Delfín", "Canguro", "Cebra"}));
        controls2.add(new ControlRow("Total Mensual", "50"));
        controls2.add(new ControlRow("Fecha alta", Calendar.getInstance().getTime()));

        List<ControlRow> controls3 = new ArrayList<>();
        controls3.add(new ControlRow("Perfil", "Perfil línea 3 Gb", false));
        controls3.add(new ControlRow("Oficina plus", "Fijo en casa", true));
        controls3.add(new ControlRow("Extensión fija?", false));
        controls3.add(new ControlRow("Número de líneas", 5));
        controls3.add(new ControlRow("Tipo tarifa", "Canguro", new String[]{"Delfín", "Canguro", "Cebra"}));
        controls3.add(new ControlRow("Total Mensual", "30"));
        controls3.add(new ControlRow("Fecha alta", Calendar.getInstance().getTime()));

        List<ControlRow> controls4 = new ArrayList<>();
        controls4.add(new ControlRow("Perfil", "Perfil línea 4 Gb", false));
        controls4.add(new ControlRow("Oficina plus", "Fijo compartido", true));
        controls4.add(new ControlRow("Extensión fija?", true));
        controls4.add(new ControlRow("Número de líneas", 2));
        controls4.add(new ControlRow("Tipo tarifa", "Cebra", new String[]{"Delfín", "Canguro", "Cebra"}));
        controls4.add(new ControlRow("Total Mensual", "60"));
        controls4.add(new ControlRow("Fecha alta", Calendar.getInstance().getTime()));

        Map<Integer, List<ControlRow>> groups = new HashMap<>();
        groups.put(0, controls2);
        groups.put(1, controls3);
        groups.put(2, controls4);

        MonoSection mono = new MonoSection("Sección mono 1", controls1);
        MonoSection mono1 = new MonoSection("Sección mono 2", controls5);
        MultiSection multi = new MultiSection("Sección multi", groups);

        List<Section> sections = new ArrayList<>();
        sections.add(mono);
        sections.add(mono1);
        sections.add(multi);
        page = new Page("Voz + voz y datos", sections);
    }
}
