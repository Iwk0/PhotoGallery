package com.androidproject.app.utils;

/**
 * Created by imishev on 1.8.2014 г..
 */

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidproject.app.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> images;
    private LinearLayout linearLayout;
    private ViewPager pager;
    private boolean isHidden = false;

    public ViewPagerAdapter(Activity activity, List<String> images) {
        this.images = images;
        this.activity = activity;

        linearLayout = (LinearLayout) activity.findViewById(R.id.toolBar);

        pager = (ViewPager) activity.findViewById(R.id.slide_show);
        pager.setPageMargin(30);
    }

    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ImageView view = new ImageView(activity);
        view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        view.setAdjustViewBounds(true);
        view.setImageBitmap(ImageResize.decodeSampledBitmapFromUri(images.get(position), 300, 300));
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!isHidden) {
                    slideToBottom(linearLayout);
                } else {
                    slideToTop(linearLayout);
                }
                isHidden = !isHidden;
            }
        });
        collection.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
        arg0.removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    private void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    private void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }
}