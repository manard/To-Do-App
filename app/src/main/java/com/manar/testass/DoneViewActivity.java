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

public class DoneViewActivity extends AppCompatActivity {
    /*Done Acticity to save Done tasks in ListView then the user can show all due tasks*/
    private ListView listviewdone;
    ArrayList<String>list=new ArrayList<>();
    private ArrayAdapter<String>adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //  DueViewActivity due=new DueViewActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_view);
        listviewdone = findViewById(R.id.listviewdone);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        Intent intent2 = getIntent();//get the name of tasks from TaskViewActivity if the task is due
        String value_From_TaskActivity = intent2.getStringExtra(TaskViewActivity.txtText);
        LoadData();
       /* Intent intent = getIntent();
        ArrayList<String> list2 = intent.getStringArrayListExtra("lista");
        Log.d("Done Activity list2", "list2: " + list2);*/
        if (list == null) {
            list = new ArrayList<>();

        }
        try {
            if (!value_From_TaskActivity.equals(null)&&!list.contains(value_From_TaskActivity)) {//To avoid null and repeating the value
                //  if(due.list.contains(value_From_TaskActivity))
                // due.list.remove(value_From_TaskActivity);
                list.add(value_From_TaskActivity);
                SaveData();
                LoadData();

          /*  if(list2.contains(value_From_TaskActivity)) {
                Toast.makeText(DoneViewActivity.this, "Done Act", Toast.LENGTH_SHORT).show();
                list2.remove(value_From_TaskActivity);
                list.add(value_From_TaskActivity);
                SaveData();
                LoadData();*/
            }
        }
        catch (Exception exception){
            exception.getMessage();
        }
      /*  ArrayList<String>list2= (ArrayList<String>) getIntent().getSerializableExtra("lista");
        //Log.d("Done Act Value", "value_From_TaskActivity: " + value_From_TaskActivity);
        Log.d("Done Act list2", "list2: " + list2);*/



    }
    public void LoadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Data3", "");
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        list = gson.fromJson(json, type);
        if (list != null) {
            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            listviewdone.setAdapter(adapter);
        }
    }
    public void SaveData(){
        Gson gson=new Gson();
        String json=gson.toJson(list);
        editor.putString("Data3",json);
        editor.commit();
        LoadData();
    }
}