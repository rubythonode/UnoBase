package kim.uno.kotlin.base.ui

import android.view.View

interface BaseDragView {

    fun onDragStateChanged(isSelected: Boolean)
    fun getHandleView(): View
    fun isSwapable(): Boolean

}