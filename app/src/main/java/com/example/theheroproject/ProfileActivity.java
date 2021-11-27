package com.example.theheroproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private JSONObject heroe;
    private TextView heroeName;
    private ImageView heroeImage;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        heroeName = findViewById(R.id.heroeName);
        heroeImage = findViewById(R.id.heroeImage);
        barChart = findViewById(R.id.barChart);

        try {
            heroe = new JSONObject(getIntent().getStringExtra("heroe"));
            heroeName.setText(heroe.getString("name"));
            Picasso.get().load(heroe.getJSONObject("image").getString("url")).into(heroeImage);
            JSONObject powers = heroe.getJSONObject("powerstats");
            int powerValues[] = {
                    powers.getInt("intelligence"),
                    powers.getInt("strength"),
                    powers.getInt("speed"),
                    powers.getInt("durability"),
                    powers.getInt("power"),
                    powers.getInt("combat"),
            };
            showGraphic(barChart, powerValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showGraphic(BarChart barChart, int[] powerValues) {
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, powerValues[0]));
        barEntryArrayList.add(new BarEntry(2, powerValues[1]));
        barEntryArrayList.add(new BarEntry(3, powerValues[2]));
        barEntryArrayList.add(new BarEntry(4, powerValues[3]));
        barEntryArrayList.add(new BarEntry(5, powerValues[4]));
        barEntryArrayList.add(new BarEntry(6, powerValues[5]));
        BarDataSet barDataSet = new BarDataSet(
                barEntryArrayList,
                " Inteligencia, Fuerza, Velocidad, Durabilidad, Poder, Combate"
        );
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(barDataSet);

        barChart.setData(barData);
    }

}