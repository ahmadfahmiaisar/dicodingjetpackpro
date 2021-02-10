package com.example.submission1.view.shimmerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.example.submission1.R
import com.facebook.shimmer.ShimmerFrameLayout
import java.util.jar.Attributes

class ShimmerView : ShimmerFrameLayout {

    private lateinit var container: LinearLayout

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(context)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttributes: Int) : super(
        context,
        attributeSet,
        defStyleAttributes
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.shimmer_view_container, this)
        container = view.findViewById(R.id.container) as LinearLayout
    }

    fun start(numberOfPlaceholderItem: Int) {
        createItem(numberOfPlaceholderItem)
        startShimmer()
        this.visibility = View.VISIBLE
        invalidate()
    }

    fun start(numberOfPlaceholderItem: Int, @LayoutRes layoutResId: Int) {
        createItem(numberOfPlaceholderItem, layoutResId)
        startShimmer()
        this.visibility = View.VISIBLE
        invalidate()
    }

    private fun createItem(
        numberOfPlaceholderItem: Int,
        layoutResId: Int = R.layout.shimmer_view_container
    ) {
        repeat(numberOfPlaceholderItem) {
            val placeholder = inflate(context, layoutResId, null)
            container.addView(placeholder)
        }
    }

    fun stop() {
        stopShimmer()
        this.visibility = View.GONE
    }
}