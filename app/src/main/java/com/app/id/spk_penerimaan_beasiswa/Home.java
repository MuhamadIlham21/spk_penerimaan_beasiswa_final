package com.app.id.spk_penerimaan_beasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class Home extends AppCompatActivity {
    Button btnKeputusan, btnInstruksi, btnCreator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnKeputusan = findViewById(R.id.btnToKeputusan);
        btnInstruksi = findViewById(R.id.btnToPetunjuk);
        btnCreator = findViewById(R.id.btnToDevelope);



        btnKeputusan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnInstruksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), instruksi.class);
                startActivity(intent);
            }
        });

        btnCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), creator.class);
                startActivity(intent);
            }
        });

    }

}
