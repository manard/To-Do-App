package com.manar.testass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TaskViewActivity extends AppCompatActivity {
/*Task Activity to let the user decide what the task Due or Done*/

    public static final String txtText = "Text";
    private TextView txtview;
    private RadioButton chk1; // done
    private RadioButton chk2; // due
    private Button savebtn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Task> list;
    private int value2_From_Main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        txtview = findViewById(R.id.txtview);
        savebtn = findViewById(R.id.savebtn);//save Stutas Button
        chk1 = findViewById(R.id.chk1);
        chk2 = findViewById(R.id.chk2);

        Intent intent = getIntent();
        String value_From_Main = intent.getStringExtra(MainActivity.taskString);
        txtview.setText(value_From_Main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        ArrayList<String>lis= (ArrayList<String>) getIntent().getSerializableExtra("lista");
        Log.d("TASKVIEW","lisTask"+lis);
        value2_From_Main = intent.getIntExtra("index", -1);
        if (value2_From_Main != -1) {
            LoadData();

            // Set the initial state of the checkboxes based on the loaded task status
            Task task = list.get(value2_From_Main);//get position for tasks from Main Activity
            chk1.setChecked(task.isStutas());
            chk2.setChecked(!task.isStutas());

        }

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the status of the current task based on the Radio Button state
                list.get(value2_From_Main).setStutas(chk1.isChecked());
                // Save the updated list
                SaveData();
                if(chk1.isChecked()){//if done
                    Intent intent2=new Intent(TaskViewActivity.this,DoneViewActivity.class);//this intent to pass the name of task to Done Activity
                    String txt_value=txtview.getText().toString();
                    intent2.putExtra(txtText,txt_value);
                    startActivity(intent2);

                }
                else if(chk2.isChecked()){//if due
                    Intent intent2=new Intent(TaskViewActivity.this,DueViewActivity.class);//this intent to pass the name of task to Due Activity
                    String txt_value=txtview.getText().toString();
                    intent2.putExtra(txtText,txt_value);
                    startActivity(intent2);
                }
                Toast.makeText(TaskViewActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void SaveData() {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("Data", json);
        editor.apply();
    }

    private void LoadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Data", "");

        // Check if the json string is not empty
        if (!json.isEmpty()) {
            Type type = new TypeToken<ArrayList<Task>>() {
            }.getType();
            list = gson.fromJson(json, type);
        } if (list==null) {
            // If the json string is empty, initialize an empty list
            list = new ArrayList<>();
        }
    }
}