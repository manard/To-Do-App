package com.manar.testass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
/*My Program has 4 Activities, Main Activity It allows you to add and watch Duo Tasks and Done Tasks*/
    public static final String taskString = "Name";

    private EditText edttxt;
    private Button btn;
    private RadioButton rdbtn1;//done
    private RadioButton rdbtn2;//due
    private ListView listview;
    ArrayList<Task> list = new ArrayList<>();
    ;
    private ArrayAdapter<Task> adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Task Program");
        edttxt = findViewById(R.id.edttxt);
        btn = findViewById(R.id.btn);
        rdbtn1 = findViewById(R.id.rdbtn1);
        rdbtn2 = findViewById(R.id.rdbtn2);
        listview = findViewById(R.id.listview);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        //  list.add(new Task("HOme1"));

        LoadData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task taskObj = new Task(edttxt.getText().toString());
                Log.d("ListBefore","list"+list);
                if (taskObj.toString().isEmpty()) {//if user press Add Button before write in EditText
                    Toast.makeText(MainActivity.this, "Empty Data,Try again", Toast.LENGTH_SHORT).show();

                } else if (!taskObj.equals(null)&&!list.contains(edttxt.getText().toString().trim())) {//To avoid repeating the value
                    Log.d("ListAfter","list"+list);
                    list.add(taskObj);//add task
                    Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    SaveData();
                    LoadData();
                    adapter.notifyDataSetChanged();

                }
               /* else if(list.contains(edttxt.getText().toString())){
                    Toast.makeText(MainActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
                }*/
                if (list == null) {
                    list = new ArrayList<>();
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TaskViewActivity.class);
                String strTask = list.get(position).getTaskName();
                intent.putExtra(taskString, strTask);

                // Get the position and put it as an extra
                int index = position;
                intent.putExtra("index", index);

                startActivity(intent);
            }
        });


        rdbtn2.setOnClickListener(new View.OnClickListener() {//to Show Due Tasks
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DueViewActivity.class);
                startActivity(intent);

            }

        });
        rdbtn1.setOnClickListener(new View.OnClickListener() {//to show Done Tasks
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoneViewActivity.class);
                startActivity(intent);
            }
        });


    }

    private void LoadData() {//Load Data Using GSON
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Data", "");

        // Check if the json string is not empty
        if (!json.isEmpty()) {
            Type type = new TypeToken<ArrayList<Task>>() {
            }.getType();
            list = gson.fromJson(json, type);

            // Check if the list is not null before creating the adapter
            if (list != null) {
                adapter = new ArrayAdapter<Task>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void SaveData() {//save Data
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("Data", json);
        editor.commit();
        LoadData();
    }
}