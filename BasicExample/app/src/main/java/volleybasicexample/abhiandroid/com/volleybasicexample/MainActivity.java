package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private static final String TAG = MainActivity.class.getName();
    private Button btnChildren;
    private Button btnCamps;
    private Button btnEmail;
    private StringBuilder children;
    private StringBuilder camps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnChildren = findViewById(R.id.children);
        btnCamps = findViewById(R.id.camps);
        btnEmail = findViewById(R.id.email);

        btnEmail.setOnClickListener(this);
        btnCamps.setOnClickListener(this);
        btnChildren.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.children:
                Intent children = new Intent(MainActivity.this, ChildrenActivity.class);
                startActivity(children);
                break;
            case R.id.camps:
                Intent camps = new Intent(MainActivity.this, CampsActivity.class);
                startActivity(camps);
                break;
            case R.id.email:
                Intent email = new Intent(MainActivity.this, EmailActivity.class);
                startActivity(email);
                break;
        }
    }

//**********************HTTP request******************************
    private void sendAndRequestResponse(final String which) {
        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        System.out.println(1);
        //String Request initialized
        String url="";
        String CAMP_URL = "http://10.0.2.2:8080/camps";
        String CHILDREN_URL = "http://10.0.2.2:8080/";
        switch(which){
            case "children":
                url= CHILDREN_URL;
                break;
            case "camps":
                url= CAMP_URL;
                break;
            case "email":
                url= CHILDREN_URL;
                break;
            default:
                url= CHILDREN_URL;
        }
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(which.equals("camps"))
                    camps.append(response);
                if(which.equals("children"))
                    children.append(response);
                System.out.println("3. Response: " + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        System.out.println(4);
        mRequestQueue.add(mStringRequest);
        //return requestData.toString();
    }

//**********************************JSON PARSE***************************************
    //********CAMPS*********
    private void jsonParsingCamp(String strJson) {
        System.out.println("CAMPS: " + strJson);
//        textChildren.setText("");
        StringBuilder camps= new StringBuilder();
        try {
            // Create the root JSONObject from the JSON string.
            //JSONObject  jsonRootObject = new JSONObject(strJson);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray =  new JSONArray(strJson); //jsonRootObject.optJSONArray("Camps");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.optString("name");
                String theme = jsonObject.optString("theme");
                String price = jsonObject.optString("price");
                String place = jsonObject.optString("place");
                String description = jsonObject.optString("description");
                boolean isActive = jsonObject.optBoolean("isActive");
                String max = jsonObject.optString("max");

                String campers = jsonObject.optString("campers");
                if(!campers.isEmpty()) {
                    jsonParsingChildren(campers);
                }

                String from = jsonObject.optString("from");
                String till = jsonObject.optString("till");

                camps.append("Camp ").append(i).append(" : \n name= ").append(name)
                                    .append(" \n theme= ").append(theme)
                                    .append(" \n price= ").append(price)
                                    .append(" \n place= ").append(place)
                                    .append(" \n description= ").append(description)
                                    .append(" \n isActive= ").append(isActive)
                                    .append(" \n max= ").append(max)
                                    .append(" \n from= ").append(from)
                                    .append(" \n till= ").append(till).append("\n\n");
            }
//            textCamp.setText(camps.toString());

            System.out.println("Camp parse done.");
        } catch (JSONException e) {e.printStackTrace();}
    }

    //*******CHILDREN********
    private void jsonParsingChildren(String strJson){
        StringBuilder children= new StringBuilder();
        System.out.println(strJson);
//        textCamp.setText("");
        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(strJson);

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.optString("id");
                String name = jsonObject.optString("name");
                String gender = jsonObject.optString("gender");
                String birthDate = jsonObject.optString("birthDate");
                String planet = jsonObject.optString("planet");
                String pos = jsonObject.optString("pos");
                String size = jsonObject.optString("size");
                String foodSensitivity = jsonObject.optString("foodSensitivity");
                String other = jsonObject.optString("other");

                /*
                parent
                JSONObject parent = new JSONObject(jsonObject.optString("parent").toString());
                String parentName=parent.getString("name");
                String parentAddress=parent.getString("address");
                String parentPhone=parent.getString("phone");
                String parentEmail=parent.getString("email");
                String parentJob=parent.getString("job");
                String parentStr="...Parent Name:"+parentName+"\n"+"...Parent Address:"+parentAddress+"\n"+"...Parent Phone:"+parentPhone+"\n"+"...Parent Email:"+parentEmail+"\n"+"...Parent Job:"+parentJob;
                */
                String year = jsonObject.optString("year");
                String rank = jsonObject.optString("rank");

                children.append("Children").append(i).append(" : name= ").append(name)
                        .append(" \n id= ").append(id)
                        .append(" \n gender= ").append(gender)
                        .append(" \n birthDate= ").append(birthDate)
                        .append(" \n planet= ").append(planet)
                        .append(" \n pos= ").append(pos)
                        .append(" \n size= ").append(size)
                        .append(" \n foodSensitivity= ").append(foodSensitivity)
                     //   .append(" \n parent= ").append(parentStr).append(" \n")
                        .append(" \n year= ").append(year)
                        .append(" \n rank= ").append(rank).append(" \n ");
            }
//            textChildren.setText(children.toString());

            System.out.println("Json parse done.");
        } catch (JSONException e) {e.printStackTrace();}
    }
}
