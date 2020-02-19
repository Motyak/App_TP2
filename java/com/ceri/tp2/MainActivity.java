package com.ceri.tp2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        wines list, tmp
//        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
//        Map<String, String> datum = new HashMap<String, String>(2);
//        datum.put("line1","Châteauneuf-du-pape");
//        datum.put("line2","vallée du Rhône");
//        data.add(datum);
//        datum = new HashMap<String, String>(2);
//        datum.put("line1", "Arbois");
//        datum.put("line2", "Jura");
//        data.add(datum);
//
//        SimpleAdapter adapter = new SimpleAdapter(this, data,
//                android.R.layout.simple_list_item_2,
//                new String[] {"line1", "line2" },
//                new int[] {android.R.id.text1, android.R.id.text2 });

        WineDbHelper wineDbHelper = new WineDbHelper(this);
        wineDbHelper.getWritableDatabase(); //calls onCreate method
        Cursor c = wineDbHelper.fetchAllWines();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c,
                new String[]{WineDbHelper.COLUMN_NAME,WineDbHelper.COLUMN_WINE_REGION}, new int[]{android.R.id.text1,android.R.id.text2});

        ListView listView = (ListView) findViewById(R.id.lvWines);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
