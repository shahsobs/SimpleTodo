package in.sobs.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by SNS on 26-Jun-16.
 */
public class ShowAllTask extends AppCompatActivity {
    ListView lvShowTask;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    SQLdbAdapter helper;
    ArrayList<String> showAllTask;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_task);
        helper = new SQLdbAdapter(this);
        lvShowTask = (ListView) findViewById(R.id.lvShowAllTask);

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvShowTask.setAdapter(itemsAdapter);
        //get all atsk with id
        showAllTask = helper.showAllTask();
        if (showAllTask.isEmpty() || showAllTask==null){
            L.t(this,"No task found");
        }
        else{
            items.addAll(showAllTask);
        }
    }
}
