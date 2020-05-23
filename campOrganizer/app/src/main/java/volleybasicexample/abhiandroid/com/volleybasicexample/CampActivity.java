package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampActivity extends AppCompatActivity {
    private static final String TAG = ChildrenActivity.class.getName();
    ListView list;
    List<Map<String, String>> children;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp);

        //Fill list view
        children=new ArrayList<>();
        list = findViewById(R.id.simpleListView);
        adapter = new SimpleAdapter(this, children, android.R.layout.simple_list_item_2, new String[] {"name", "birthDate"}, new int[] {android.R.id.text1,android.R.id.text2});
        list.setAdapter(adapter);
        makeGetRequest();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CampActivity.this, ChildActivity.class);
                intent.putExtra("childDatas", (Serializable) children.get(position));
                startActivity(intent);
            }
        });
    }

    private void makeGetRequest() {
        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String id = getIntent().getStringExtra("campID");
        String CAMPS_URL = "http://10.0.2.2:8080/camp/"+id;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, CAMPS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                // Parsing json object response
                // response will be a json object
                HashMap<String,String> campData = new HashMap<>();
                campData.put("name", response.optString("name"));
                campData.put("theme", response.optString("theme"));
                campData.put("price", response.optString("price"));
                campData.put("place", response.optString("place"));
                campData.put("description", response.optString("description"));
                campData.put("isActive", response.optString("isActive"));
                campData.put("max", response.optString("max"));
                campData.put("from", response.optString("from"));
                campData.put("till", response.optString("till"));

                JSONArray campers = response.optJSONArray("campers");
                parseChildren(campers);

                fill(campData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjReq);
    }

    private void fill(HashMap<String, String> campData){
        //Fill textviews with datas from json object
        int resID;
        TextView tv;
        for(String k : campData.keySet()){
            resID = getResources().getIdentifier(k, "id", getPackageName());
            tv = findViewById(resID);
            if(tv != null) {
                tv.setText(campData.get(k));
            }
        }
    }

    private void parseChildren(JSONArray campers){
        try {
            // Parsing json array response
            // loop through each json object
            for(int i=0; i < campers.length(); i++){
                JSONObject jsonObject = campers.getJSONObject(i);

                String name = jsonObject.optString("name");
                String gender = jsonObject.optString("gender");
                String birthDate = jsonObject.optString("birthDate");
                String planet = jsonObject.optString("planet");
                String pos = jsonObject.optString("pos");
                String size = jsonObject.optString("size");
                String foodSensitivity = jsonObject.optString("foodSensitivity");
                String other = jsonObject.optString("other");
                String year = jsonObject.optString("year");
                String rank = jsonObject.optString("rank");
                String house = jsonObject.optString("house");
                //parent
                JSONObject parent = new JSONObject(jsonObject.optString("parent"));
                String parentName=parent.getString("name");
                String parentAddress=parent.getString("address");
                String parentPhone=parent.getString("phone");
                String parentEmail=parent.getString("email");
                String parentJob=parent.getString("job");

                Map<String,String> m = new HashMap<>();
                m.put("name",name);
                m.put("birthDate", birthDate);
                m.put("pos", pos);
                m.put("planet",planet);
                m.put("house", house);
                m.put("rank",rank);
                m.put("year", year);
                m.put("gender", gender);
                m.put("size", size);
                m.put("foodSensitivity",foodSensitivity);
                m.put("other", other);

                //parent
                m.put("parentName", parentName);
                m.put("address", parentAddress);
                m.put("phone",parentPhone);
                m.put("email", parentEmail);
                m.put("job", parentJob);

                children.add(m);
            }
            list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
