package com.resoluciones.legajos.ui;

/**
 * Created by resoluciones on 17/2/15.
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.resoluciones.legajos.R;
import com.resoluciones.legajos.util.ApplicationHelper;

public class StepPagerStrip extends View {
    private static final int[] ATTRS = new int[]{
            android.R.attr.gravity
    };
    private int mPageCount;
    private int mCurrentPage;

    private int mGravity = Gravity.LEFT | Gravity.TOP;
    private float mTabWidth;
    private float mTabHeight;
    private float mTabCurrentHeight;
    private float mTabSpacing;

    private Paint mTabSolve;
    private Paint mTabCurrentSolve;
    private Paint mTabCurrentUnsolve;
    private Paint mTabUnsolve;

    private RectF mTempRectF = new RectF();

    private OnPageSelectedListener mOnPageSelectedListener;

    public StepPagerStrip(Context context) {
        this(context, null, 0);
    }

    public StepPagerStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepPagerStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        mGravity = a.getInteger(0, mGravity);
        a.recycle();

        final Resources res = getResources();
        mTabWidth = res.getDimensionPixelSize(R.dimen.step_pager_tab_width);
        mTabHeight = res.getDimensionPixelSize(R.dimen.step_pager_tab_height);
        mTabCurrentHeight = res.getDimensionPixelSize(R.dimen.step_pager_tab_current_height);
        mTabSpacing = res.getDimensionPixelSize(R.dimen.step_pager_tab_spacing);

        mTabSolve = new Paint();
        mTabSolve.setColor(res.getColor(R.color.step_pager_solve));
        mTabCurrentSolve = new Paint();
        mTabCurrentSolve.setColor(res.getColor(R.color.step_pager_current_solve));
        mTabUnsolve = new Paint();
        mTabUnsolve.setColor(res.getColor(R.color.step_pager_unsolve));
        mTabCurrentUnsolve = new Paint();
        mTabCurrentUnsolve.setColor(res.getColor(R.color.step_pager_current_unsolve));

    }

    public void setOnPageSelectedListener(OnPageSelectedListener onPageSelectedListener) {
        mOnPageSelectedListener = onPageSelectedListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPageCount == 0) {
            return;
        }

        float totalWidth = mPageCount * (mTabWidth + mTabSpacing) - mTabSpacing;
        float totalLeft;
        boolean fillHorizontal = false;

        switch (mGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.CENTER_HORIZONTAL:
                totalLeft = (getWidth() - totalWidth) / 2;
                break;
            case Gravity.RIGHT:
                totalLeft = getWidth() - getPaddingRight() - totalWidth;
                break;
            case Gravity.FILL_HORIZONTAL:
                totalLeft = getPaddingLeft();
                fillHorizontal = true;
                break;
            default:
                totalLeft = getPaddingLeft();
        }

        switch (mGravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.CENTER_VERTICAL:
                mTempRectF.top = (int) (getHeight() - mTabHeight) / 2;
                break;
            case Gravity.BOTTOM:
                mTempRectF.top = getHeight() - getPaddingBottom() - (mTabHeight);
                break;
            default:
                mTempRectF.top = getPaddingTop();
        }

        float tabWidth = mTabWidth;
        if (fillHorizontal) {
            tabWidth = (getWidth() - getPaddingRight() - getPaddingLeft()
                    - (mPageCount - 1) * mTabSpacing) / mPageCount;
        }

        for (int i = 0; i < mPageCount; i++) {
            mTempRectF.left = totalLeft + (i * (tabWidth + mTabSpacing));
            mTempRectF.right = mTempRectF.left + tabWidth;

            Paint iPaint;

            if (i == mCurrentPage) {
                if (ApplicationHelper.i().getSolveSteps()[i]) {
                    iPaint = mTabCurrentSolve;
                } else {
                    iPaint = mTabCurrentUnsolve;
                }
            } else {
                if (ApplicationHelper.i().getSolveSteps()[i]) {
                    iPaint = mTabSolve;
                } else {
                    iPaint = mTabUnsolve;
                }
            }

            mTempRectF.bottom = mTempRectF.top + (mCurrentPage == i ? mTabCurrentHeight : mTabHeight);
            canvas.drawRect(mTempRectF, iPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                View.resolveSize(
                        (int) (mPageCount * (mTabWidth + mTabSpacing) - mTabSpacing)
                                + getPaddingLeft() + getPaddingRight(),
                        widthMeasureSpec),
                View.resolveSize(
                        (int) (mTabHeight)
                                + getPaddingTop() + getPaddingBottom(),
                        heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mOnPageSelectedListener != null) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    int position = hitTest(event.getX());
                    if (position >= 0) {
                        mOnPageSelectedListener.onPageStripSelected(position);
                    }
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private int hitTest(float x) {
        if (mPageCount == 0) {
            return -1;
        }

        float totalWidth = mPageCount * (mTabWidth + mTabSpacing) - mTabSpacing;
        float totalLeft;
        boolean fillHorizontal = false;

        switch (mGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.CENTER_HORIZONTAL:
                totalLeft = (getWidth() - totalWidth) / 2;
                break;
            case Gravity.RIGHT:
                totalLeft = getWidth() - getPaddingRight() - totalWidth;
                break;
            case Gravity.FILL_HORIZONTAL:
                totalLeft = getPaddingLeft();
                fillHorizontal = true;
                break;
            default:
                totalLeft = getPaddingLeft();
        }

        float tabWidth = mTabWidth;
        if (fillHorizontal) {
            tabWidth = (getWidth() - getPaddingRight() - getPaddingLeft()
                    - (mPageCount - 1) * mTabSpacing) / mPageCount;
        }

        float totalRight = totalLeft + (mPageCount * (tabWidth + mTabSpacing));
        if (x >= totalLeft && x <= totalRight && totalRight > totalLeft) {
            return (int) (((x - totalLeft) / (totalRight - totalLeft)) * mPageCount);
        } else {
            return -1;
        }
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
        invalidate();
    }

    public void setPageCount(int count) {
        mPageCount = count;
        invalidate();
    }

    public static interface OnPageSelectedListener {
        void onPageStripSelected(int position);
    }

}