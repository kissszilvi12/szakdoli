package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SetChildActivity extends AppCompatActivity {
    private static final String TAG = ChildrenActivity.class.getName();
    EditText editText;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_child);

        final String id = getIntent().getStringExtra("id");
        final String field = getIntent().getStringExtra("field");

        fill(field, id);

        Button button = findViewById(R.id.setChildButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePutRequest(field, id);
            }
        });
    }

    private void fill(String field, String id){
        TextView tv = findViewById(R.id.editTextDesc);
        tv.setText(field+": ");
        TableRow tableRow = findViewById(R.id.content);

    //spinners
        String[] forSpinners = {"Bolygó", "Ház", "Méret", "Pozíció", "Nem"};
        if(Arrays.asList(forSpinners).contains(field)){
            Spinner spinner = new Spinner(SetChildActivity.this);
            spinner.setId(Integer.parseInt(id));
            tableRow.addView(spinner);
            switch (field){
                case "planet":
                    String[] planets = new String[] {"", "Zolgs", "Astrocomic", "Hifi",  "Tobimug"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, planets);
                    spinner.setAdapter(adapter);
                    break;
                case "house":
                    String[] houses = new String[] {"", "Hollóhát", "Mardekár", "Griffendél",  "Hugrabug"};
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, houses);
                    spinner.setAdapter(adapter);
                    break;
                case "size":
                    String[] sizes = new String[] {"104","110","116","122","128","134","140","146","152","158","164"};
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);
                    spinner.setAdapter(adapter);
                    break;
                case "pos":
                    String[] poses = new String[]{"Gyerek", "Vezető", "Prefektus", "Ifivezető", "Apród"};
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, poses);
                    spinner.setAdapter(adapter);
                    break;
                case "gender":
                    String[] genders = {"Fiú", "Lány"};
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
                    spinner.setAdapter(adapter);
                    break;
            }
            tableRow.addView(spinner);
    //edit texts
        }else{
            EditText editText = new EditText(SetChildActivity.this);
            editText.setId(Integer.parseInt(id));
            tableRow.addView(editText);
        }
    }

    //set child
    private void makePutRequest(final String field, final String id){

        String url = "http://10.0.2.2:8080/setcamper";

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SetChildActivity.this,"Sikeres módosítás!",Toast.LENGTH_LONG).show();
                finish();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Send message when something goes wrong
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(SetChildActivity.this);
                        dlgAlert.setMessage("Hiba történt!");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    }
                });
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("id", id);
                int resID = getResources().getIdentifier(id, "id", getPackageName());
                EditText editText = findViewById(resID);
                if(editText!=null) {
                    data.put(field, editText.getText().toString());
                }else{
                    Spinner spinner = findViewById(resID);
                    data.put(field, spinner.getSelectedItem().toString().toUpperCase());
                }

                return data;
            }
        };
        mRequestQueue.add(stringRequest);
    }
}
