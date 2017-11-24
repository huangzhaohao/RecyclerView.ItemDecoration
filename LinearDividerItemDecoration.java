package com.hzh.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

/**
 * view 间距
 */
public class LinearDividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private int mOrientation;
    private Context mContext;
    private TextPaint mTextPaint;
    private float listDividerSize = 2;
    private int listDividerColor = Color.parseColor("#eeeeee");

    public LinearDividerItemDecoration(Context context,int orientation,int listDividerSize){
        mContext = context;
        mTextPaint = new TextPaint();
        mTextPaint.setColor(this.listDividerColor);
        this.listDividerSize = listDividerSize;
        setOrientation(orientation);
    }

    public LinearDividerItemDecoration(Context context,int orientation,int listDividerSize,int listDividerColor){
        mContext = context;
        mTextPaint = new TextPaint();
        this.listDividerColor = listDividerColor;
        mTextPaint.setColor(this.listDividerColor);
        this.listDividerSize = listDividerSize;
        setOrientation(orientation);
    }
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        super.onDraw(c, parent);
        if(mOrientation == HORIZONTAL_LIST){
            drawHorizontal(c, parent);
        }else{
            drawVertical(c, parent);
        }
    }
    /**
     * 绘制垂直分割线
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        //分割线的左边界 = 子View的左padding值
        int rectLeft = parent.getPaddingLeft();
        //分割线的右边界 = 子View的宽度 - 子View的右padding值
        int rectRight = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i ++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 分割线的top = 子View的底部 + 子View的margin值
            int rectTop = child.getBottom() + layoutParams.bottomMargin;
            // 分割线的bottom = 分割线的top + 分割线的高度
            float rectBottom = rectTop + listDividerSize;
            c.drawRect(rectLeft,rectTop,rectRight,rectBottom,mTextPaint);
        }
    }

    /**
     * 绘制水平分割线
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        //分割线的上边界 = 子View的上padding值
        int rectTop = parent.getPaddingTop();
        //分割线的下边界 = 子View的高度 - 子View的底部padding值
        int rectBottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i ++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            //分割线的Left = 子View的右边界 + 子View的左margin值
            int rectLeft = child.getRight() + layoutParams.rightMargin;
            //分割线的right = 分割线的Left + 分割线的宽度
            float rectRight = rectLeft + listDividerSize;
            c.drawRect(rectLeft,rectTop,rectRight,rectBottom,mTextPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(mOrientation == VERTICAL_LIST){
            outRect.set(0,0,0,(int)listDividerSize);
        } else{
            outRect.set(0,0,(int)listDividerSize,0);
        }
    }
}
