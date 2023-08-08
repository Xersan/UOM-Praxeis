package com.example.uompraxeis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    private TextView txtView1;
    private TextView txtView3;
    private TextView txtView4;
    private TextView txtView5;

    private Spinner spinner;

    private RadioGroup radioGroup;

    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);

        txtView1 = findViewById(R.id.textView1);
        txtView3 = findViewById(R.id.textView3);
        txtView4 = findViewById(R.id.textView4);
        txtView5 = findViewById(R.id.textView5);

        radioGroup = findViewById(R.id.radioGroup);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        RequestQueue volleyQueue = Volley.newRequestQueue(this);
        String url = "https://diavgeia.gov.gr/opendata/organizations.json?category=UNIVERSITY";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response ->
                {
                    JSONArray jArray = getJSONArray(getJSONObject(response), "organizations");

                    for (int i=0; i < jArray.length(); i++)
                    {
                        try {
                            JSONObject oneObject = jArray.getJSONObject(i);
                            String oneObjectsItem = oneObject.getString("label");

                            if(oneObjectsItem.equals("ΠΑΝΕΠΙΣΤΗΜΙΟ ΜΑΚΕΔΟΝΙΑΣ"))
                            {
                                String str = oneObject.getString("uid");
                                txtView1.setText(str);
                                uid = str;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, error -> Toast.makeText(getApplicationContext(),"Try again!", Toast.LENGTH_SHORT).show());

        volleyQueue.add(stringRequest);

        button1.setOnClickListener(v ->
        {
            RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

            if(!radioButton.getText().toString().equals("Units"))
            {
                setTextViewsLoading(new ArrayList<>(Arrays.asList(txtView3, txtView4, txtView5)));
                getData(uid, spinner.getSelectedItem().toString(), volleyQueue);
            }
            else
                listUnits();
        });
    }


    private void getData(String university, String year, RequestQueue volleyQueue)
    {
        String urlTOTAL = "https://diavgeia.gov.gr/opendata/search?org=" + university + "&from_issue_date=" + year + "-01-01&to_issue_date=" + year + "-12-31";
        String urlRevoked = "https://diavgeia.gov.gr/opendata/search?org=" + university + "&status=revoked&from_date=" + year + "-01-01&to_date=" + year + "-12-31";

        getTotalDecisions(urlTOTAL, txtView3, volleyQueue);
        getRevokedDecisions(urlRevoked, txtView4, txtView5, volleyQueue);
    }


    private void getTotalDecisions(String url, TextView textView, RequestQueue volleyQueue)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> textView.setText(getJsonString(getJSONObject(getJsonString(getJSONObject(response), "info")), "total")),
                error -> Toast.makeText(getApplicationContext(),"Try again!", Toast.LENGTH_SHORT).show());

        volleyQueue.add(stringRequest);
    }


    private void getRevokedDecisions(String url, TextView totalTextView, TextView personalDataTextView, RequestQueue volleyQueue)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response ->
                {
                    JSONObject jObject = getJSONObject(response);
                    String total = getJsonString(getJSONObject(getJsonString(jObject, "info")), "total");
                    JSONArray jArray = getJSONArray(jObject, "decisions");

                    int count = 0;
                    for (int i=0; i < jArray.length(); i++)
                    {
                        try {
                            JSONObject oneObject = jArray.getJSONObject(i);
                            boolean oneObjectsItem = oneObject.getBoolean("privateData");
                            if(oneObjectsItem)
                                count++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    totalTextView.setText(total);
                    personalDataTextView.setText(String.valueOf(count));

                }, error -> Toast.makeText(getApplicationContext(),"Try again!", Toast.LENGTH_SHORT).show());

        volleyQueue.add(stringRequest);
    }


    public static JSONObject getJSONObject(String response)
    {
        JSONObject jObject = null;

        try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObject;
    }


    private String getJsonString(JSONObject jObject, String label)
    {
        String aJsonString = "";

        try {
            aJsonString = jObject.getString(label);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return aJsonString;
    }


    public static JSONArray getJSONArray(JSONObject jObject, String label)
    {
        JSONArray jArray = null;

        try {
            jArray = jObject.getJSONArray(label);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jArray;
    }


    private void setTextViewsLoading(ArrayList<TextView> list)
    {
        String str = "loading";

        for (TextView txtView : list)
            txtView.setText(str);
    }


    private void listUnits()
    {
        Intent i = new Intent(MainActivity.this, ListUnitsActivity.class);
        i.putExtra("University ID", uid);
        startActivity(i);
    }
}