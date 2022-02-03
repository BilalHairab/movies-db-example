package com.bilal.movies.app.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Bilal Hairab on 03/02/2022.
 */
class ItemsSpacingDecoration(private val horizontalSpacing: Int, private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = verticalSpacing
        outRect.top = verticalSpacing

        outRect.left = horizontalSpacing
        outRect.right = horizontalSpacing
    }
}