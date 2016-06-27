package in.sobs.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SNS on 22-Jun-16.
 */
public class EditTask extends AppCompatActivity {
    EditText taskText;
    Button cancel,update;
    String taskData;
    int taskPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
//        Toast.makeText(this,"You have clicked on about jjjjjus",Toast.LENGTH_LONG).show();

        taskText = (EditText) findViewById(R.id.etEditTaskText);
//        cancel = (Button) findViewById(R.id.bCancelUpdate);
        update = (Button) findViewById(R.id.bUpdateTask);
        //show soft keyboard
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(taskText,InputMethodManager.SHOW_FORCED);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //for invisible
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Bundle gotBasket = getIntent().getExtras();
        taskData = gotBasket.getString("key");
        taskPosition = gotBasket.getInt("position");
//        String taskPosition = Integer.toString(gotBasket.getInt("position"));
        String toastData = "You have selected "+ taskData + " which was at position "+ taskPosition;
//        L.t(this,toastData);

        taskText.setText(taskData);
        //now put cursor at end
//        taskText.append("j");
        taskText.setSelection(taskText.getText().length());
        taskText.setBackgroundColor(Color.GREEN);
    }

    public void updateTask(View view){
        String currentUpdatedTask = taskText.getText().toString();
//            L.t(this,"Taskdata value is "+ currentUpdatedTask);
        // if data not updated
        if (taskData.equals(currentUpdatedTask)){
            L.t(this,"Nothing Updated.");
            Intent main = new Intent(this,MainActivity.class);
            finish();
            startActivity(main);

        }


        Bundle basket = new Bundle();
        basket.putString("key", currentUpdatedTask);
        basket.putInt("position",taskPosition);
        Intent i = new Intent(EditTask.this,MainActivity.class);
        i.putExtras(basket);
        setResult(RESULT_OK,i);
        finish();
    }
}
