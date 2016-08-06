package com.fish.nsd;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by fish on 16/8/5.
 */
public class ChildScrollView extends MyNestedScrollView {
    public ChildScrollView(Context context) {
        super(context);
        init();
    }

    public ChildScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChildScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public ChildScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    private void init() {
        setNestedScrollingEnabled(true);
    }



}
