package com.androidproject.app.utils;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.androidproject.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListAdapter extends ListActivity {

    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM HH:mm");
    private ArrayList<String> listItems;
    private ArrayAdapter<String> adapter;
    private EditText comment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_dialog);
        comment = (EditText) findViewById(R.id.commentText);

        listItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);
    }

    public void addComment(View view) {
        listItems.add(simpleDateFormat.format(new Date()) + "\nIwk0: " + comment.getText().toString());
        adapter.notifyDataSetChanged();
        comment.setText("");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}