package com.app.id.spk_penerimaan_beasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class instruksi extends AppCompatActivity {

    ListView listview;
    String[] list = new String[] {
            "1. Wajib menginputkan semua data",
            "2. Data kandidat beasiswa harus lebih dari satu",
            "3. Inputkan sesuai dengan instruksi yang ada",
            "4. Hasil dari keputusan ada pada tab HASIL",
            "5. Simpan hasil tersebut jika perlu"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruksi);

        listview = (ListView) findViewById(R.id.lvInstruksi);

        final List< String > ListElementsArrayList = new ArrayList< String >
                (Arrays.asList(list));


        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
                (instruksi.this, android.R.layout.simple_list_item_1,
                        ListElementsArrayList);

        listview.setAdapter(adapter);
    }
}
