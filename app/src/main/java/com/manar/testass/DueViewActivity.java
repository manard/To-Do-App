package com.manar.testass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class DueViewActivity extends AppCompatActivity {
    /*Due Acticity to save Due tasks in ListView then the user can show all due tasks*/
    private ListView listviewdue;
    ArrayList<String>list=new ArrayList<>();
    private ArrayAdapter<String>adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String key="key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_view);
        listviewdue = findViewById(R.id.listviewdone);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        Intent intent2 = getIntent();//get the name of tasks from TaskViewActivity if the task is due
        String value_From_TaskActivity = intent2.getStringExtra(TaskViewActivity.txtText);
        LoadData();
        if (list == null) {
            list = new ArrayList<>();
        }
        try {
            if (!value_From_TaskActivity.equals(null)&&!list.contains(value_From_TaskActivity)) {//To avoid null and repeating the value
                list.add(value_From_TaskActivity);
                SaveData();
                LoadData();

            }
        }
        catch (Exception exception){
            exception.getMessage();
        }
        /*if (list != null) {
            Intent intent = new Intent(DueViewActivity.this, DoneViewActivity.class);/*this intent to pass Due list to DoneActivity then i want to decide
            if the user change the state of task then delete from due and add to done
            intent.putStringArrayListExtra("lista",list);
            //startActivity(intent);
            Log.d("Due Act", "listDue: " + list);
       }*/



    }
    public void LoadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Data2", "");
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        list = gson.fromJson(json, type);
        if (list != null) {
            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            listviewdue.setAdapter(adapter);
            Log.d("DueViewActivity", "Adapter data: " + Arrays.toString(new String[]{adapter.getItem(0)}));
        }
    }
    public void SaveData(){
        Gson gson=new Gson();
        String json=gson.toJson(list);
        editor.putString("Data2",json);
        editor.commit();
        LoadData();
    }
}