package com.ceri.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class WineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);

        final WineDbHelper wineDbHelper = new WineDbHelper(this);
        Intent intent = getIntent();

        EditText etNom = (EditText) findViewById(R.id.wineName);
        EditText etRegion = (EditText) findViewById(R.id.editWineRegion);
        EditText etLoc = (EditText) findViewById(R.id.editLoc);
        EditText etClim = (EditText) findViewById(R.id.editClimate);
        EditText etArea = (EditText) findViewById(R.id.editPlantedArea);
        Button btnSauvegarder = (Button) findViewById(R.id.button);

        Wine wine = intent.getParcelableExtra("Wine");

        etNom.setText(wine.getTitle());
        etRegion.setText(wine.getRegion());
        etLoc.setText(wine.getLocalization());
        etClim.setText(wine.getClimate());
        etArea.setText(wine.getPlantedArea());

        btnSauvegarder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
//                on cree un Wine avec les nouvelles valeurs
                Wine w = new Wine();
//                on recupere l'id dans l'objet wine de l'intent
                w.setId(((Wine)WineActivity.this.getIntent().getParcelableExtra("Wine")).getId());
                w.setTitle(((EditText) findViewById(R.id.wineName)).getText().toString());
                w.setRegion(((EditText) findViewById(R.id.editWineRegion)).getText().toString());
                w.setLocalization(((EditText) findViewById(R.id.editLoc)).getText().toString());
                w.setClimate(((EditText) findViewById(R.id.editClimate)).getText().toString());
                w.setPlantedArea(((EditText) findViewById(R.id.editPlantedArea)).getText().toString());

                if(w.getId()==0)
//                    on ajoute le wine
                    wineDbHelper.addWine(w);
                else
//                    on update le wine de la bdd
                    wineDbHelper.updateWine(w);

//                on recharge la premiere activitee
                startActivity(new Intent(WineActivity.this, MainActivity.class));
            }
        });
    }
}
