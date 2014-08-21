package com.androidproject.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.androidproject.app.utils.ListAdapter;
import com.androidproject.app.utils.ViewPagerAdapter;

import java.util.List;

public class ViewActivity extends ActionBarActivity {

    private final static String IMAGES = "IMAGES";
    private final static String POSITION = "POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);

        final ImageButton likesButton = (ImageButton) findViewById(R.id.likesButton);
        likesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                likesButton.setEnabled(false);
            }
        });

        ImageButton commentButton = (ImageButton) findViewById(R.id.messageButton);
        commentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListAdapter.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            List<String> images = extras.getStringArrayList(IMAGES);
            Integer imageId = extras.getInt(POSITION);

            ViewPagerAdapter adapter = new ViewPagerAdapter(this, images);
            ViewPager myPager = (ViewPager) findViewById(R.id.slide_show);
            myPager.setAdapter(adapter);
            myPager.setCurrentItem(imageId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(this, GalleryActivity.class);

        switch (id) {
            case R.id.action_settings :
                startActivity(intent);
                return true;
            case R.id.second_bar:
                return true;
            default: return super.onOptionsItemSelected(item);
        }
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