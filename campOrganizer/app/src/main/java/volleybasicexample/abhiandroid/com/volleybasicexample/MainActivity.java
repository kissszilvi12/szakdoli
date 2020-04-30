package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnChildren = findViewById(R.id.children);
        Button btnCamps = findViewById(R.id.camps);
        Button btnEmail = findViewById(R.id.email);

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
}
