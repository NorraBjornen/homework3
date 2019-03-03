package com.example.homework3

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

class CustomViewGroup @JvmOverloads constructor(context: Context,attrs: AttributeSet? = null,defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    private val distanceBetweenViews : Int
    private val verticalPadding = 20
    private val childViewHeight = 50

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewGroup)
        distanceBetweenViews = typedArray.getDimensionPixelSize(R.styleable.CustomViewGroup_cvg_distance_between_views, 0)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)

        val childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.UNSPECIFIED)
        val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childViewHeight, MeasureSpec.EXACTLY)

        for (index in 0 until childCount){
            val child = getChildAt(index)
            if(child.visibility == View.GONE){
                child.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY)
                )
                continue
            }
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
        }

        var usedHeight = 0
        var usedWidthInRow = 0

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if(child.measuredWidth == 0)
                continue
            if(usedWidthInRow + distanceBetweenViews + child.measuredWidth < width)
                usedWidthInRow += (distanceBetweenViews + child.measuredWidth)
            else {
                usedWidthInRow = distanceBetweenViews + child.measuredWidth
                usedHeight += (childViewHeight + verticalPadding)
            }
        }

        setMeasuredDimension(width, usedHeight + childViewHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l

        var usedHeight = 0
        var usedWidthInRow = 0

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if(child.measuredWidth == 0)
                continue
            if(usedWidthInRow + distanceBetweenViews + child.measuredWidth < width){
                child.layout(usedWidthInRow, usedHeight, usedWidthInRow + child.measuredWidth, usedHeight + child.measuredHeight)
                usedWidthInRow += (distanceBetweenViews + child.measuredWidth)
            }
            else {
                usedHeight += (child.measuredHeight + verticalPadding)
                child.layout(0, usedHeight, child.measuredWidth,usedHeight + child.measuredHeight)
                usedWidthInRow = distanceBetweenViews + child.measuredWidth
            }
        }
    }
}