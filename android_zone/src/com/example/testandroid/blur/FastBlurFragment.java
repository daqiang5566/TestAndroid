package com.example.testandroid.blur;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testandroid.R;
import com.example.testandroid.common.FastBlur;

/**
 * Created by paveldudka on 3/4/14.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class FastBlurFragment extends Fragment {
    private final String DOWNSCALE_FILTER = "downscale_filter";

    private ImageView image;
    private TextView text;
    private CheckBox downScale;
    private TextView statusText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        text = (TextView) view.findViewById(R.id.text);
        
        image = (ImageView) view.findViewById(R.id.picture);
        image.setBackgroundResource(R.drawable.picture);
        statusText = addStatusText((ViewGroup) view.findViewById(R.id.controls));
        addCheckBoxes((ViewGroup) view.findViewById(R.id.controls));
        
        
        if (savedInstanceState != null) {
            downScale.setChecked(savedInstanceState.getBoolean(DOWNSCALE_FILTER));
        }
        applyBlur();
        return view;
    }

    private void applyBlur() {
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                image.buildDrawingCache();

                Bitmap bmp = image.getDrawingCache();
                blur(bmp);
                return true;
            }
        });
    }

    private void blur(Bitmap bkg) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 8;
        float radius = 20;
        if (downScale.isChecked()) {
            scaleFactor = 8;
            radius = 2;
        }

        Bitmap overlay = Bitmap.createBitmap((int) (bkg.getWidth()),
                (int) (bkg.getHeight()/2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        //canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        

        overlay = FastBlur.doBlur(bkg, (int)radius, true);
        image.setBackground(new BitmapDrawable(getResources(), overlay));
        statusText.setText(System.currentTimeMillis() - startMs + "ms");
    }

    @Override
    public String toString() {
        return "Fast blur";
    }

    private void addCheckBoxes(ViewGroup container) {

        downScale = new CheckBox(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        downScale.setLayoutParams(lp);
        downScale.setText("Downscale before blur");
        downScale.setVisibility(View.VISIBLE);
        downScale.setTextColor(0xFFFFFFFF);
        downScale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                applyBlur();
            }
        });
        container.addView(downScale);
    }

    private TextView addStatusText(ViewGroup container) {
        TextView result = new TextView(getActivity());
        result.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        result.setTextColor(0xFFFFFFFF);
        container.addView(result);
        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(DOWNSCALE_FILTER, downScale.isChecked());
        super.onSaveInstanceState(outState);
    }
}
