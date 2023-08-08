package com.example.uompraxeis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListUnitsActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_units);

        String university = getIntent().getStringExtra("University ID");

        TextView textViewCount = findViewById(R.id.textViewCount);

        ListView listView = findViewById(R.id.listView);

        String url = "https://diavgeia.gov.gr/opendata/organizations/" + university + "/units.json?status=active";

        RequestQueue volleyQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response ->
                {
                    JSONArray jArray = MainActivity.getJSONArray(MainActivity.getJSONObject(response), "units");

                    ArrayList<String> unitsList = new ArrayList<>();
                    int count = 0;

                    for (int i=0; i < jArray.length(); i++)
                    {
                        try {
                            JSONObject oneObject = jArray.getJSONObject(i);
                            String oneObjectsItem = oneObject.getString("label");

                            unitsList.add(oneObjectsItem);
                            count++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, unitsList);
                    listView.setAdapter(adapter);

                    textViewCount.setText(String.valueOf(count));

                }, error -> Toast.makeText(getApplicationContext(),"Try again!", Toast.LENGTH_SHORT).show());

        volleyQueue.add(stringRequest);
    }
}