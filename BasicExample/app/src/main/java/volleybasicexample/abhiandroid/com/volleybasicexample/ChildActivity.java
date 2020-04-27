package volleybasicexample.abhiandroid.com.volleybasicexample;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        TextView tv;
        int resID;
        HashMap<String, String> o = (HashMap<String, String>) getIntent().getSerializableExtra("childDatas");

        //Set text color by planet
        tv = findViewById(R.id.name);
        if (o.get("pos").equals("LEADER")) {
            tv = findViewById(R.id.name);
            tv.setTextColor(Color.BLACK);
        }if(o.get("pos").equals("CHILD")){
            switch(o.get("planet")){
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
                    tv.setTextColor(Color.rgb(63, 64, 60));
            }
        }

        //Set background color by house
        ConstraintLayout cl = findViewById(R.id.constraintLayout);
        switch(o.get("house")){
            case "SLYTHERIN":
                cl.setBackgroundResource(R.drawable.slytherin);
                break;
            case "HUFFLEPUFF":
                cl.setBackgroundResource(R.drawable.hufflepuff);
                break;
            case "RAVENCLAW":
                cl.setBackgroundResource(R.drawable.ravenclaw);
                break;
            case "GRYFFINDOR":
                cl.setBackgroundResource(R.drawable.gryffindor);
                break;
            default:
                cl.setBackgroundColor(Color.rgb(214, 212, 201));
        }

        for(String k : o.keySet()){
            resID = getResources().getIdentifier(k, "id", getPackageName());
            tv = findViewById(resID);
            if(tv != null) {
                tv.setText(o.get(k));
                tv.getLayoutParams().height = Integer.parseInt("150");
            }
        }

    }
}
