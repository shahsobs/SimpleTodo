package in.sobs.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by SNS on 27-Jun-16.
 */
public class About extends AppCompatActivity {
    TextView tv;
    String about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        tv = (TextView) findViewById(R.id.tvAbout);
        about = "Developer Name : Shahwaaz Uddin\n" +
                "Developer Website : www.sobs.in\n" +
                "Developer Email : shah@sobs.in\n";
        tv.setText(about);
    }

}
