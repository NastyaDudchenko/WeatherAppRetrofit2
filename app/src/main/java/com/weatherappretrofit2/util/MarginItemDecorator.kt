package com.weatherappretrofit2.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorator(private val spaceHeight: Int, private val spaceBottom: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            bottom = spaceBottom
            //right = spaceHeight
        }
    }
}