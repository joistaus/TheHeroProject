package com.example.theheroproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private ListView heroesResults;
    private TextView resultCounts;


    private JSONArray heroes;
    private ArrayList<String> heroesNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultCounts = findViewById(R.id.resultCounts);
        heroesResults = findViewById(R.id.heroesResults);

        try {
            heroes = new JSONArray(getIntent().getStringExtra("heroes"));
            for (int i = 0; i < heroes.length(); i++) {
                heroesNames.add(heroes.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ResultsActivity.this,
                android.R.layout.simple_list_item_1,
                heroesNames
        );
        resultCounts.setText("Resultados: " + heroesNames.size());
        heroesResults.setAdapter(adapter);
        heroesResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String heroe = heroes.getString(position);
                    Intent intent = new Intent(
                            ResultsActivity.this,
                            ProfileActivity.class
                    );
                    intent.putExtra("heroe", heroe);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}