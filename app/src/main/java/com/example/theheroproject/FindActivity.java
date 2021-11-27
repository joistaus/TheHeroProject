package com.example.theheroproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class FindActivity extends AppCompatActivity {

    private TextInputLayout heroField;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        heroField = findViewById(R.id.heroField);
        heroField.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                heroField.setError(null);
            }
        });

        requestQueue = Volley.newRequestQueue(this);
    }

    public void find(View view) {
        String hero = heroField.getEditText().getText().toString().trim();
        if (hero.isEmpty()) {
            heroField.setError("¡Debe ingresar un nombre!");
        } else {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    getApiUrl(hero),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("response").equals("error")) {
                                    heroField.setError("Sin Resultados");
                                } else {
                                    String heroes = response.getString("results");
                                    Intent intent = new Intent(
                                            FindActivity.this,
                                            ResultsActivity.class
                                    );
                                    intent.putExtra("heroes", heroes);
                                    heroField.getEditText().setText("");
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                heroField.setError("Ha ocurrido un error");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            heroField.setError("Ha ocurrido un error");
                        }
                    });
            requestQueue.add(request);
        }
    }

    private String getApiUrl(String hero) {
        return "https://www.superheroapi.com/api.php/1503126980053016/search/" + hero;
    }

}