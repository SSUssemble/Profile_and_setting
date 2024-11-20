package com.foo.propnsetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class appsetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appsetting);

        Intent intent = new Intent(this, MainActivity.class);

        ListView listview = findViewById(R.id.listView);

        String[] listviewItem = {"다크모드 설정"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, listviewItem);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            if (position == 0) {
                
                }
            });
        }
}