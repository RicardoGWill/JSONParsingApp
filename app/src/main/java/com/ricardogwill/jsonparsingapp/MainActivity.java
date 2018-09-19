package com.ricardogwill.jsonparsingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Help came from the tutorial here: https://www.youtube.com/watch?v=y2xtLqP8dSQ
// Note that this uses the build.gradle dependency: implementation 'com.android.volley:volley:1.0.0'
// Also, AndroidManifest.xml must contain: <uses-permission android:name="android.permission.INTERNET" />

public class MainActivity extends AppCompatActivity {

    private TextView infoTextView;
    private TextView resultTextView;
    Button parseButton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoTextView = findViewById(R.id.info_textView);
        resultTextView = findViewById(R.id.result_textView);
        parseButton = findViewById(R.id.parse_button);

        requestQueue = Volley.newRequestQueue(this);

        parseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTextView.setText("The United States and Their Capitals");
                resultTextView.setText("");
                jsonParse();
            }
        });

    }

    private void jsonParse() {

        String url = "https://api.myjson.com/bins/1dffto";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("states");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject statesJSONObject = jsonArray.getJSONObject(i);

                        int numberString = statesJSONObject.getInt("number");
                        String nameString = statesJSONObject.getString("name");
                        String capitalString = statesJSONObject.getString("capital");

                        resultTextView.append(  String.valueOf(numberString) + ": " + nameString + "\n" +
                                                "★ " + capitalString + "\n\n");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
