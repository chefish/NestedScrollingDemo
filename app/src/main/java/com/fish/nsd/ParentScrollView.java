package com.fish.nsd;

import android.content.Context;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

/**
 * Created by fish on 16/8/5.
 */
public class ParentScrollView extends MyNestedScrollView {
    public ParentScrollView(Context context) {
        super(context);
    }

    public ParentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public ParentScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //TODO  canScrollVertically这里会算over的，所以不准
        if (!isBtnOnTop() && canScrollVertically(1)) {
            int oldScrollY = getScrollY();
            //滚起来
            scrollBy(dx, dy);
            consumed[1] = getScrollY() - oldScrollY;
            if (dy == consumed[1]) {
                LogUtil.fish("consumed all");
                childView = (ChildScrollView) target;
                consumeAll = true;
            } else {
                consumeAll = false;
                LogUtil.fish("consumed not all");
            }


//            LogUtil.fish("dy=" + dy + " consume=" + consumed[1]);
        }
    }

    private ChildScrollView childView;
    private boolean consumeAll;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (consumeAll && ev.getAction() == MotionEvent.ACTION_UP) {

            if (childView instanceof ChildScrollView) {
                ChildScrollView child = (ChildScrollView) childView;
                if (child.mVelocityTracker != null) {
                    final ViewConfiguration configuration = ViewConfiguration.get(getContext());
                    child.mVelocityTracker.computeCurrentVelocity(1000, configuration.getScaledMaximumFlingVelocity());
                    int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(child.mVelocityTracker,
                            child.mActivePointerId);

                    if ((Math.abs(initialVelocity) > child.mMinimumVelocity)) {
                        //因为nestedScrolling的bug，所以在这里fling
                        fling(-initialVelocity);
                        LogUtil.fish("fling up");
                        return false;
                    }
                }
                //此时，应该截获，调用fling
            }

        }
        boolean b= super.onInterceptTouchEvent(ev);
        LogUtil.fish("parent onInterceptTouchEvent="+b);
        return b;
    }

    //fling不能把button给飞走,手指上滑，velocityY为正
    public void fling(int velocityY) {
        LogUtil.fish("velocityY="+velocityY);
        if (getChildCount() > 0) {
            int height = getHeight() - getPaddingBottom() - getPaddingTop();
            int bottom = getChildAt(0).getHeight();

            mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0,
                    Math.max(0, yBtn), 0, 0);

            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    Button topBtn;
    int yBtn;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topBtn = (Button) findViewById(R.id.btn);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        yBtn = (int) topBtn.getY();
        LogUtil.fish("yBtn=" + yBtn);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (isBtnOnTop() && !mScroller.isFinished() && t > oldt) {
            //需要abort吗？
            int v=(int) mScroller.getCurrVelocity();
            //这里的速度可以调整，如果直接用v太快了
            v=v/2;
            LogUtil.fish("child Velocity=" + v);
            childView.fling(v);
//            LogUtil.fish("get top");
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private boolean isBtnOnTop() {
        return getScrollY() >= yBtn;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        LogUtil.fish("parent onNestedScroll");
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    boolean overScrollByCompat(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollByCompat(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
}
