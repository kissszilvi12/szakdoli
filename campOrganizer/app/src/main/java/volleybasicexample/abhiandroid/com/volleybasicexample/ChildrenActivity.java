package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildrenActivity extends AppCompatActivity {
    private static final String TAG = ChildrenActivity.class.getName();
    ListView list;
    List<Map<String, String>> children;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);

        list = findViewById(R.id.simpleListView);
        children = new ArrayList<>();

        //Tablayout
        TabLayout tabLayout = findViewById(R.id.tabs);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch(tab.getPosition()) {
                        case 1:
                            Intent camps = new Intent(ChildrenActivity.this, CampsActivity.class);
                            startActivity(camps);
                            break;
                        case 2:
                            Intent email = new Intent(ChildrenActivity.this, EmailActivity.class);
                            startActivity(email);
                            break;
                    }
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}
                @Override
                public void onTabReselected(TabLayout.Tab tab) {}
            });


        //Fill list view with datas from json
        makeJsonArrayRequest();
        adapter = new SimpleAdapter(this, children, android.R.layout.simple_list_item_2, new String[] {"name", "birthDate"}, new int[] {android.R.id.text1,android.R.id.text2});

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ChildrenActivity.this, ChildActivity.class);
            intent.putExtra("childDatas", (Serializable) children.get(position));
            startActivity(intent);
            }
        });
    }

    private void makeJsonArrayRequest() {
        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String CHILDREN_URL = "http://10.0.2.2:8080/";

        JsonArrayRequest req = new JsonArrayRequest(CHILDREN_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Parsing json array response
                    // loop through each json object
                    for(int i=0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

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

                        m.put("parentName", parentName);
                        m.put("parentAddress", parentAddress);
                        m.put("parentPhone",parentPhone);
                        m.put("parentEmail", parentEmail);
                        m.put("parentJob", parentJob);

                        children.add(m);
                    }
                    list.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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