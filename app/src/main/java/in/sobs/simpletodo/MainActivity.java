package in.sobs.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    SQLdbAdapter helper;
    List<Array> getAllTaskId;
    public static final int DELETEALL = 0;
    List<String> taskIddb;
//    hghjfhgfhg


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new SQLdbAdapter(this);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        //if app opened for first time

        String[] taskList = {"one","two","three"};
//        String[] taskList1 = {"one1","two2","three3"};
        long x=0;
//        items.addAll(0,myTask);
        for (String task: taskList ){
            items.add(task);
//            helper.insertTask(task);
            x++;
        }
//        L.t(this, Long.toString(x));
        //get taskfrom db
        List<String> taskListdb = helper.readTask(1);
        //get taskID from db
        taskIddb = helper.readTask(2);
        if (!taskListdb.isEmpty()){
            items.clear();
//            L.t(this,"tasklistDB is not empty.wow");
            items.addAll(taskListdb);
//            items.addAll(taskIddb);
        }
        else{
            L.t(this,"tasklistDB is EMPTY");
        }

        setUpListViewListener();
        //open sqlhelper
    }
    //////////////////// ONCREATE ENDS

    private void setUpListViewListener() {
        //handle single click that will edit task
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView tv = (TextView) view;
                        String currentTask = tv.getText().toString();
//                        String id = taskIddb;
//                        String[] idArray = taskIddb.toArray();
                        String idValue = null;//get task table key
                        try {
                            idValue = taskIddb.get(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        L.t(MainActivity.this,"TASK is "+currentTask+ " POSITION IS "+position+ " whose id value is "+idValue);
                        Bundle basket = new Bundle();
                        basket.putString("key", currentTask);
                        basket.putInt("position",position);
                        Intent a = new Intent(MainActivity.this,EditTask.class);
                        a.putExtras(basket);
                        startActivityForResult(a,0);
//                        startActivity(a);
                    }
                }
        );

        //handle long click that will delete task
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                        items.remove(position);
//                        itemsAdapter.notifyDataSetChanged();
                        String idValue = null;//get task table key
                        int idValueInt = 0;
                        try {
                            idValue = taskIddb.get(position);
                            //convert to int
                            idValueInt = Integer.parseInt(idValue);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        int isDeleted = helper.deleteOne(idValueInt);
                        L.t(MainActivity.this,"Task successfully deleted ");
                        Intent self =new Intent(MainActivity.this,MainActivity.class);
                        startActivity(self);
                        finish();
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about){
            Intent i = new Intent(this,About.class);
            startActivity(i);
        }
        else if (id == R.id.deleteAllTask){
            helper.deleteAll();/////////////////////////////// DELETE ALL TASK FROM task TABLE
            L.t(this,"All task were deleted from task TABLE");
        }
        else if (id == R.id.showAllTask){
            Intent showIntent = new Intent(this,ShowAllTask.class);
            startActivity(showIntent);
        }
        else if (id == R.id.exit){
            finish();
        }

        return  super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view){
        EditText newItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = newItem.getText().toString();
//        itemsAdapter.add(itemText);
        newItem.setText("");
        newItem.clearFocus();
        //remove softkeyboard
//        InputMethodManager inputMethodManager = (InputMethodManager)
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(newItem.getWindowToken(),0);

        //write data
        if (itemText.equals("") || itemText==null){
            L.t(this,"Task detail cannot be empty!");
        } else{
            helper.insertTask(itemText);
            L.t(this,"Task Successfully Added");
            this.recreate();
        }


    }

    // handle EditTask.java result to update task
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bundle basket = data.getExtras();
            String taskData = basket.getString("key");
            int position = basket.getInt("position");
            String idValue = null;//get task table key
//            int idValueInt = 0;
            try {
                idValue = taskIddb.get(position);
                //convert to int
//                idValueInt = Integer.parseInt(idValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int isDeleted = helper.updateTask(idValue,taskData);
            L.t(this,"Task successfully updated :)");
            this.recreate();
        }
    }

    public void deleteAllTask(View view){
        helper.deleteAll();/////////////////////////////// DELETE ALL TASK FROM task TABLE
        L.t(this,"All task were deleted from task TABLE");
    }
}
