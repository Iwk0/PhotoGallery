package com.androidproject.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.androidproject.app.utils.ImageResize;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GalleryActivity extends ActionBarActivity {

    private final static String IMAGES = "IMAGES";
    private final static String POSITION = "POSITION";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);
        String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        String targetPath = ExternalStorageDirectoryPath + "/Wallpapers/";
        File targetDirector = new File(targetPath);
        for (File file : targetDirector.listFiles()) {
            imageAdapter.add(file.getAbsolutePath());
        }
    }

    // Test Home1

    private class ImageAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<String> itemList = new ArrayList<String>();

        public ImageAdapter(Context context) {
            this.context = context;
        }

        public void add(String path) {
            itemList.add(path);
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                    intent.putExtra(IMAGES, itemList);
                    intent.putExtra(POSITION, position);
                    startActivity(intent);
                }
            });

            imageView.setImageDrawable(getResources().getDrawable(R.drawable.empty_gallery));
            imageView.setTag(position);

            LoadPicture loadPicture = new LoadPicture(imageView, position);
            loadPicture.execute(itemList.get(position));

            return imageView;
        }
    }

    private class LoadPicture extends AsyncTask<String, String, Bitmap> {

        private WeakReference<ImageView> imageViewReference;
        private int position;

        public LoadPicture(ImageView imageView, int position) {
            this.imageViewReference = new WeakReference<ImageView>(imageView);
            this.position = position;
        }

        @Override
        protected Bitmap doInBackground(String... images) {
            return ImageResize.decodeSampledBitmapFromUri(images[0], 100, 100);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null && imageViewReference != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null && position == imageView.getTag()) {
                    imageView.setImageBitmap(result);
                }
            }
        }
    }
}