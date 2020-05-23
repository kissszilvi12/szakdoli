package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ChildrenActivity.class.getName();
    ListView list;
    List<Map<String, String>> camps;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camps);

        //Tablayout
        TabLayout tabLayout = findViewById(R.id.tabs);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        if (tab != null)
            tab.select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        Intent children = new Intent(CampsActivity.this, ChildrenActivity.class);
                        startActivity(children);
                        break;
                    case 2:
                        Intent email = new Intent(CampsActivity.this, EmailActivity.class);
                        startActivity(email);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        //Fill list view
        list = findViewById(R.id.simpleListView);
        camps = new ArrayList<>();
        makeGetRequest();

        adapter = new SimpleAdapter(this, camps, android.R.layout.simple_list_item_2, new String[] {"name", "from"}, new int[] {android.R.id.text1,android.R.id.text2});
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CampsActivity.this, CampActivity.class);
                intent.putExtra("campID", camps.get(position).get("id"));
                startActivity(intent);
            }
        });

        Button addCamp = findViewById(R.id.addCamp);
        addCamp.setOnClickListener(this);
    }

    private void makeGetRequest() {
        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String CAMPS_URL = "http://10.0.2.2:8080/camps";

        JsonArrayRequest req = new JsonArrayRequest(CAMPS_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String name = jsonObject.optString("name");
                        String from = jsonObject.optString("from");
                        String id = jsonObject.optString("id");

                        Map<String, String> m = new HashMap<>();
                        m.put("name", name);
                        m.put("from", from);
                        m.put("id", id);
                        camps.add(m);
                    }
                    list.setAdapter(adapter);
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(CampsActivity.this, AddCampActivity.class);
        startActivity(intent);
    }
}
