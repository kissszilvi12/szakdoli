package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddCampActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ChildrenActivity.class.getName();

    EditText campNameEditText;
    EditText fromEditText;
    EditText toEditText;
    EditText placeEditText;
    EditText priceEditText;
    EditText maxEditText;
    EditText descriptionEditText;
    RadioGroup themeRadioGroup;

    Button addCamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_camp);

        campNameEditText = findViewById(R.id.campNameEditText);
        fromEditText = findViewById(R.id.fromEditText);
        toEditText = findViewById(R.id.toEditText);
        placeEditText = findViewById(R.id.placeEditText);
        priceEditText = findViewById(R.id.priceEditText);
        maxEditText = findViewById(R.id.maxEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        themeRadioGroup = findViewById(R.id.themeRadioGroup);

        //Date picker
        fromEditText.setOnClickListener(this);
        toEditText.setOnClickListener(this);

        addCamp = findViewById(R.id.addCamp);
        addCamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePostRequest();
            }
        });
    }

    //create camp
    private void makePostRequest(){

        String url = "http://10.0.2.2:8080/addcamp";

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response");
                Toast.makeText(AddCampActivity.this,"Tábor sikeresen létrehozva!",Toast.LENGTH_LONG).show();
                finish();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //Send message when something goes wrong
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(AddCampActivity.this);
                        dlgAlert.setMessage("Valami hiba történt! Próbáld újra!");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    }
                });
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                int id = themeRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                String selectedtext = radioButton.getText().toString();
                switch(selectedtext){
                    case "Harry Potter":
                        data.put("theme", "HARRY_POTTER");
                        break;
                    case "Egyéb":
                        data.put("theme", "OTHER");
                        break;
                }
                data.put("name", 	campNameEditText.getText().toString());
                data.put("from", 	fromEditText.getText().toString());
                data.put("till", 	toEditText.getText().toString());
                data.put("place", 	placeEditText.getText().toString());
                data.put("price", 	priceEditText.getText().toString());
                data.put("max", 	    maxEditText.getText().toString());
                data.put("description", 	descriptionEditText.getText().toString());
                return data;
            }
        };
        mRequestQueue.add(MyStringRequest);
    }

    @Override
    public void onClick(final View v){
        // To show current date in the datepicker
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(AddCampActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                switch(v.getId()) {
                    case R.id.fromEditText:
                        fromEditText.setText(sdf.format(myCalendar.getTime()));
                        break;
                    case R.id.toEditText:
                        toEditText.setText(sdf.format(myCalendar.getTime()));
                        break;
                }
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }
}