package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailActivity extends AppCompatActivity {
    private static final String TAG = ChildrenActivity.class.getName();
    Spinner dropdown_to;
    Spinner dropdown_template;
    Spinner dropdown_tag;
    List<String> camps;
    ArrayAdapter<String> adapter;
    EditText contentEditText;
    EditText subjectEditText;
    Boolean sent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        contentEditText = findViewById(R.id.contentEditText);
        subjectEditText = findViewById(R.id.subjectEditText);
        sent = false;

        //Tablayout
        TabLayout tabLayout = findViewById(R.id.tabs);
        TabLayout.Tab tab = tabLayout.getTabAt(2);
        if(tab != null)
            tab.select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Intent children = new Intent(EmailActivity.this, ChildrenActivity.class);
                        startActivity(children);
                        break;
                    case 1:
                        Intent camps = new Intent(EmailActivity.this, CampsActivity.class);
                        startActivity(camps);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        fillToDropDown();
        fillTemplateDropDown();
        fillTagsDropDown();

        Button send = findViewById(R.id.sendButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePostRequest();
                while(sent==false){}
                Toast.makeText(getApplicationContext(), "Elküldve", Toast.LENGTH_LONG).show();
                selectTemplate("");
            }
        });

    }


    public void fillToDropDown(){
        //fill To dropdown
        camps=new ArrayList<>();
        camps.add("Összes szülő");
        camps.add("Első táborosok");

        dropdown_to = findViewById(R.id.spinner_to);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, camps);
        dropdown_to.setAdapter(adapter);
        makeGetRequest();
    }

    public void fillTemplateDropDown(){
        final String[] templates=new String[]{"Egyéni", "Tájékoztatás táborról", "Fontos tudnivalók", "Információ kérés gyerekről"};

        dropdown_template = findViewById(R.id.spinner_template);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, templates);
        dropdown_template.setAdapter(adapter2);

        //insert selected template
        dropdown_template.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    selectTemplate(templates[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }
    public void fillTagsDropDown(){
        //create a new array for adapter
        String[] tags = new String[]{"szülő név", "gyerek név", "gyerek rendfokozat", "tábor név", "tábor kezdet", "tábor vége"};

        //set datas with the new arraylist
        dropdown_tag = findViewById(R.id.spinner_tag);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tags);
        //set adapter
        dropdown_tag.setAdapter(adapter2);

        //insert selected tag
        Button add = findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = dropdown_tag.getSelectedItem().toString();
                insertTag(text);
            }
        });
    }

    //insert tag
    private void insertTag(String text){
        String original = contentEditText.getText().toString();
        int pos = contentEditText.getSelectionStart();
        String pre = original.substring(0,pos);
        String suf = original.substring(pos);
        original = pre + "{"+text+"} "+suf;
        contentEditText.setText(original);
        contentEditText.setSelection(pos+text.length()+3);
    }

    private void makeGetRequest() {
        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String CAMPS_URL = "http://10.0.2.2:8080/activeCamps";

        JsonArrayRequest req = new JsonArrayRequest(CAMPS_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        parseCampName(jsonObject);
                    }
                    dropdown_to.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(req);
    }

    //Parsing
    public void parseCampName(JSONObject jsonObject){
        String name = jsonObject.optString("name");
        Map<String, String> m = new HashMap<>();
        m.put("name", name);

        camps.add(m.get("name"));
    }

    //Send email to the server
    private void makePostRequest(){
        String email=dropdown_to.getSelectedItem().toString();
        String subject=subjectEditText.getText().toString();
        String content=contentEditText.getText().toString();

        String url = "http://10.0.2.2:8080/sendemail";//+"?"+"email=\""+email+"\"&subject=\""+subject+"\"&content=\""+content+"\"";

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email", dropdown_to.getSelectedItem().toString());
                data.put("subject", subjectEditText.getText().toString());
                data.put("content", contentEditText.getText().toString());
                sent=true;
                return data;
            }
        };
        mRequestQueue.add(MyStringRequest);
    }

    //Select template
    private void selectTemplate(String text){
        String subject, content;
        Resources res = getResources();
        switch(text){
            case "Tájékoztatás táborról":
                subject = "Tájékoztató";
                content= res.getString(R.string.tajekoztato)+res.getString(R.string.alairas);
                break;

            case "Fontos tudnivalók":
                subject = "Tábor információk";
                content=res.getString(R.string.tudnivalok)+res.getString(R.string.alairas);
                break;

            case "Információ kérés gyerekről":
                subject = "Szükséges adatok";
                content= res.getString(R.string.plusz_informacio) + res.getString(R.string.alairas) ;
                break;

            default:
                subject = "";
                content="";
        }
        subjectEditText.setText(subject);
        contentEditText.setText(content);
    }
}
