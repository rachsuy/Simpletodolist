package com.suy.rachele.simpletodolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aTodoAdapter;
    ListView listViewItem;
    EditText etditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        listViewItem = (ListView) findViewById(R.id.listViewItem);
        listViewItem.setAdapter(aTodoAdapter);
        etditText = (EditText) findViewById(R.id.editText);
        listViewItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aTodoAdapter.notifyDataSetChanged();
                writeItems();
                return true;

            }
        });

        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etditText.setText(todoItems.get(position));
                todoItems.remove(position);
                aTodoAdapter.notifyDataSetChanged();
            }
        });
    }

    public void populateArrayItems() {
        readItems();
        aTodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);


    }

    private void readItems() {
        File filesdir = getFilesDir();
        File file = new File(filesdir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            e.printStackTrace();
            todoItems = new ArrayList<>();
        }

    }


    private void writeItems() {
        File filesdir = getFilesDir();
        File file = new File(filesdir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onAddItem(View view) {
        aTodoAdapter.add(etditText.getText().toString());
        etditText.setText("");
        writeItems();
    }
}
