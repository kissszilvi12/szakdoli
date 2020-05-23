package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ChildActivity extends AppCompatActivity {
    Spinner setSpinner;
    HashMap<String,String> dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        dict = new HashMap<>();

        dict.put  ("name","  Név  ");
        dict.put  ("other","  Egyéb  ");
        dict.put  ("address","  Cím  ");
        dict.put  ("gender","  Nem  ");
        dict.put  ("year","  Évfolyam  ");
        dict.put  ("planet","  Bolygó  ");
        dict.put  ("birthDate","  Születési dátum  ");
        dict.put  ("house","  Ház  ");
        dict.put  ("parentName","  Szülő neve  ");
        dict.put  ("size","  Méret  ");
        dict.put  ("pos","  Pozíció  ");
        dict.put  ("phone","  Telefonszám  ");
        dict.put  ("foodSensitivity","  Étel érzékenység  ");
        dict.put  ("job","  Munkahely  ");
        dict.put  ("email","  Email  ");

        final HashMap<String, String> o = (HashMap<String, String>) getIntent().getSerializableExtra("childDatas");
        setSpinner = findViewById(R.id.setSpinner);
        fill(o);
        ImageButton set = findViewById(R.id.setChild);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = setSpinner.getSelectedItem().toString().trim();
                Intent intent = new Intent(ChildActivity.this, SetChildActivity.class);
                intent.putExtra("id", o.get("id"));
                intent.putExtra("field", selected);
                startActivity(intent);
            }
        });

        ImageButton delete = findViewById(R.id.deleteChild);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(o.get("id"));
            }
        });
    }

    private void fill(HashMap<String, String> data){
        TextView tv;
        int resID;

        //create a list for the spinner
        ArrayList<String> keys = new ArrayList<>();
        for(String s : data.keySet()){
            System.out.println("*"+s.trim());
            if(dict.containsKey(s))
                keys.add(dict.get(s).trim());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, keys);
        setSpinner.setAdapter(adapter);

        for(String k : data.keySet()){
            resID = getResources().getIdentifier(k, "id", getPackageName());
            tv = findViewById(resID);
            if(tv != null) {
                tv.setText(data.get(k));
                tv.getLayoutParams().height = Integer.parseInt("150");
            }
        }

        //Set text color by planet
        TextView name = findViewById(R.id.name);
        if(data.get("pos").equals("GYEREK"))
            colorByTeam(name, data.get("planet"));
        else
            name.setTextColor(Color.BLACK);
    }

    private void colorByTeam(TextView tv, String team){
        switch(team){
            case "ASTROCOMIC":
                tv.setTextColor(Color.rgb(6, 0, 120));
                break;
            case "ZOLGS":
                tv.setTextColor(Color.rgb(111, 0, 117));
                break;
            case "HIFI":
                tv.setTextColor(Color.rgb(0, 89, 0));
                break;
            case "TOBIMUG":
                tv.setTextColor(Color.rgb(117, 4, 0));
                break;
            default:
                tv.setTextColor(Color.rgb(53, 54, 50));
        }
    }

    public void submit(final String id){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Biztosan törölni szeretnéd?");

        alertDialogBuilder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        makeDeleteRequest(id);
                    }
                });
        alertDialogBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void makeDeleteRequest(String id){
        String url = "http://10.0.2.2:8080/camper"+id;

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ChildActivity.this,"Sikeres törlés!",Toast.LENGTH_LONG).show();
            }
        },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ChildActivity.this);
                        dlgAlert.setMessage("Hiba történt!");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    }
                });
            }
        }
        );
        mRequestQueue.add(stringRequest);
    }
}
