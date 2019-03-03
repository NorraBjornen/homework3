package com.example.homework3

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class CustomViewGroup @JvmOverloads constructor(context: Context,attrs: AttributeSet? = null,defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    //private val distanceBetweenViews : Int
    private val childViewHeight : Int

    private val marginTop : Int
    private val marginBottom : Int
    private val marginLeft : Int
    private val marginRight : Int

    private val marginLeftPlusRight : Int
    private val marginTopPlusBottom : Int

    private val toEnd : Boolean

    companion object {
        private const val GRAVITY_LEFT = 3
        private const val GRAVITY_RIGHT = 5
    }


    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewGroup)
        //distanceBetweenViews = typedArray.getDimensionPixelSize(R.styleable.CustomViewGroup_cvg_distance_between_views, 0)
        childViewHeight = typedArray.getDimensionPixelSize(R.styleable.CustomViewGroup_cvg_views_height, 50)

        marginTop = typedArray.getDimensionPixelSize(R.styleable.CustomViewGroup_android_layout_marginTop, 0)
        marginBottom = typedArray.getDimensionPixelSize(R.styleable.CustomViewGroup_android_layout_marginBottom, 0)
        marginLeft = typedArray.getDimensionPixelSize(R.styleable.CustomViewGroup_android_layout_marginLeft, 0)
        marginRight = typedArray.getDimensionPixelSize(R.styleable.CustomViewGroup_android_layout_marginRight, 0)

        marginLeftPlusRight = marginLeft + marginRight
        marginTopPlusBottom = marginTop + marginBottom

        val gravity = typedArray.getInt(R.styleable.CustomViewGroup_android_gravity, GRAVITY_LEFT)
        toEnd = gravity == GRAVITY_RIGHT
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

        var usedHeight = childViewHeight + marginTopPlusBottom
        var usedWidthInRow = 0

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if(child.measuredWidth == 0)
                continue

            if(usedWidthInRow + marginLeftPlusRight + child.measuredWidth < width)
                usedWidthInRow += (marginLeftPlusRight + child.measuredWidth)
            else {
                usedWidthInRow = marginLeftPlusRight + child.measuredWidth
                usedHeight += childViewHeight + marginTopPlusBottom
            }
        }

        setMeasuredDimension(width, usedHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l

        var usedHeight = 0
        var usedWidthInRow = 0

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if(child.measuredWidth == 0)
                continue

            if(usedWidthInRow + marginLeftPlusRight + child.measuredWidth < width){
                if(toEnd)
                    child.layout(width - (usedWidthInRow + child.measuredWidth + marginRight), usedHeight + marginTop, width - (usedWidthInRow + marginRight), usedHeight + marginTop + child.measuredHeight)
                else
                    child.layout(usedWidthInRow + marginLeft, usedHeight + marginTop, usedWidthInRow + marginLeft + child.measuredWidth, usedHeight + marginTop + child.measuredHeight)
                usedWidthInRow += marginLeftPlusRight + child.measuredWidth
            }
            else {
                usedHeight += (child.measuredHeight + marginTopPlusBottom)
                if(toEnd)
                    child.layout(width - (child.measuredWidth + marginRight), usedHeight + marginTop, width - marginRight,usedHeight + marginTop + child.measuredHeight)
                else
                    child.layout(marginLeft, usedHeight + marginTop,marginLeft + child.measuredWidth ,usedHeight + marginTop + child.measuredHeight)
                usedWidthInRow = marginLeftPlusRight + child.measuredWidth
            }
        }
    }
}