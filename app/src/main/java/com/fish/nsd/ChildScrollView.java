package com.fish.nsd;

import android.content.Context;
import android.support.v4.view.ViewCompat;
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
        glowTopEnable=false;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //处理，往上fling
        if (t < oldt) {
            LogUtil.fish("child up fling t=" + t + " isfling=" + !mScroller.isFinished());
//            LogUtil.fish("child up fling to top");
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onFlingToTop(int currVelocity) {
        ParentScrollView parentScrollView= (ParentScrollView) getParent().getParent();
        parentScrollView.fling( -currVelocity/2);
    }
}
