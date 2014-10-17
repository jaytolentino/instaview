package com.nuzzel.jay.instaview.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by jay on 10/2/14.
 */
public class FullImageView extends ImageView {
    public FullImageView(Context context) {
        super(context);
    }
    public FullImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FullImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width); // forced to be square... TODO better way to get height?
    }
}
