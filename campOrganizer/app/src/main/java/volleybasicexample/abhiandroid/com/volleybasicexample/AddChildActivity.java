package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddChildActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ChildrenActivity.class.getName();
    AddChildActivity addChildActivity;

    //camps
    ArrayList<String> activeCampNames;
    ArrayList<Integer> activeCampIds;

    //child
    EditText nameEditText, birthDateEditText, foodSensitivityEditText, descriptionEditText;
    Spinner planetSpinner, houseSpinner, sizeSpinner, posSpinner;
    RadioGroup genderRadioGroup;

    //parent
    EditText parentNameEditText, postCodeEditText, countryEditText, streetEditText, phoneEditText, emailEditText, jobEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        //Camps
        activeCampIds = new ArrayList<>();
        activeCampNames = new ArrayList<>();
        addChildActivity = this;
        makeGetRequest();

        //Parent
        parentNameEditText = findViewById(R.id.parentNameEditText);
        postCodeEditText = findViewById(R.id.postCodeEditText);
        countryEditText = findViewById(R.id.countryEditText);
        streetEditText = findViewById(R.id.streetEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        jobEditText = findViewById(R.id.jobEditText);

        //Child
        planetSpinner = findViewById(R.id.planetSpinner);
        houseSpinner = findViewById(R.id.houseSpinner);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        posSpinner = findViewById(R.id.posSpinner);

        nameEditText = findViewById(R.id.nameEditText);
        birthDateEditText = findViewById(R.id.birthDateEditText);
        foodSensitivityEditText = findViewById(R.id.foodSensitivityEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);

        genderRadioGroup = findViewById(R.id.genderRadioGroup);

        //fill planet spinner with planets
        String[] planets = new String[] {"", "Zolgs", "Astrocomic", "Hifi",  "Tobimug"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, planets);
        planetSpinner.setAdapter(adapter);

        //fill house spinner with houses
        String[] houses = new String[] {"", "Hollóhát", "Mardekár", "Griffendél",  "Hugrabug"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, houses);
        houseSpinner.setAdapter(adapter);

        //fill size spinner with sizes
        String[] sizes = new String[] {"", "104","110","116","122","128","134","140","146","152","158","164"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);
        sizeSpinner.setAdapter(adapter);

        //fill position spinner with sizes
        String[] poses = new String[]{"Gyerek", "Vezető", "Prefektus", "Ifivezető", "Apród"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, poses);
        posSpinner.setAdapter(adapter);

        birthDateEditText.setOnClickListener(this);

        Button addChild = findViewById(R.id.addChild);
        addChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePostRequest();
            }
        });
    }

    @Override
    public void onClick(final View v){
        // To show current date in the datepicker
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(AddChildActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                birthDateEditText.setText(df.format(myCalendar.getTime()));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle(getString(R.string.datum_kivalasztasa));
        mDatePicker.show();
    }

    //create child
    private void makePostRequest(){

        String url = "http://10.0.2.2:8080/addcamper";

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Send message when something goes wrong
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(AddChildActivity.this);
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
                int id = genderRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                String selectedtext = radioButton.getText().toString();
                data.put("gender", selectedtext.toUpperCase());
                data.put("name",    nameEditText.getText().toString());
                data.put("birthDate",birthDateEditText.getText().toString());

                if(planetSpinner.getSelectedItem().toString().equals("")){
                    data.put("planet", 	"NINCS");
                }else{
                    data.put("planet", 	planetSpinner.getSelectedItem().toString().toUpperCase());
                }

                if(houseSpinner.getSelectedItem().toString().equals("")){
                    data.put("house", 	"NINCS");
                }else{
                    data.put("house", 	houseSpinner.getSelectedItem().toString().toUpperCase());
                }

                data.put("size", 	sizeSpinner.getSelectedItem().toString());
                data.put("pos", 	posSpinner.getSelectedItem().toString().toUpperCase());
                data.put("foodSensitivity", 	foodSensitivityEditText.getText().toString());
                data.put("other", 	descriptionEditText.getText().toString());
                //Parent
                data.put("parentName",parentNameEditText.getText().toString());
                data.put("postCode", postCodeEditText.getText().toString());
                data.put("country", 	countryEditText.getText().toString());
                data.put("street", 	streetEditText.getText().toString());
                data.put("phone", 	phoneEditText.getText().toString());
                data.put("email", 	emailEditText.getText().toString());
                data.put("job", 	    jobEditText.getText().toString());

                //camps
                StringBuilder camps= new StringBuilder();
                for(int i = 0; i< activeCampIds.size(); i++){
                    CheckBox checkBox = findViewById(activeCampIds.get(i));
                    if(checkBox.isChecked())
                        camps.append(checkBox.getId()).append(";");
                }
                data.put("camps", camps.toString());
                for(String k : data.keySet()){
                    System.out.println(k + ": " + data.get(k));
                }
                return data;
            }
        };
        mRequestQueue.add(MyStringRequest);
    }

    //Parsing
    public void parseCampName(JSONObject jsonObject){
        String name = jsonObject.optString("name");
        Map<String, String> m = new HashMap<>();
        m.put("name", jsonObject.optString("name"));
        m.put("id", jsonObject.optString("id"));

        activeCampNames.add(m.get("name"));
        activeCampIds.add( Integer.parseInt(m.get("id")) );
    }

    private void makeGetRequest() {
        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String CAMPS_URL = "http://10.0.2.2:8080/activecamps";

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
                    //Add checkboxes to the form
                    TableLayout tableLayout = findViewById(R.id.tableCamps);
                    String name;
                    for(int i = 0; i < activeCampNames.size(); i++) {
                        name = activeCampNames.get(i);
                        TableRow tableRow = new TableRow(AddChildActivity.this);    //add new row to the table
                        CheckBox checkBox = new CheckBox(AddChildActivity.this);    //add checkbox to the new row
                        checkBox.setId(activeCampIds.get(i));   //checkbox's id is the camp's id
                        checkBox.setText(name);
                        tableRow.addView(checkBox);
                        tableLayout.addView(tableRow);
                    }
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
}