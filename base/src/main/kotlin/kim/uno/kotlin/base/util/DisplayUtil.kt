package kim.uno.kotlin.base.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup

private val BASE_GUIDE_WIDTH = 720f
private val BASE_GUIDE_HEIGHT = 1280f

fun getDpi(context: Context): Float {
    return context.resources.displayMetrics.density
}

fun getPixelFromDp(context: Context, dp: Float): Int {
    return (getDpi(context) * dp).toInt()
}

fun getDisplayMetrics(context: Context): DisplayMetrics {
    return context.resources.displayMetrics
}

fun getWidth(context: Context): Int {
    return getDisplayMetrics(context).widthPixels
}

fun getHeight(context: Context): Int {
    return getDisplayMetrics(context).heightPixels
}

/**
 * 현재 가로 해상도에 맞춰 리턴
 * @param px
 * *
 * @return
 */
fun fromW(context: Context, px: Float): Float {
    return if (px > 0) getWidth(context) * (px / BASE_GUIDE_WIDTH) else 0f
}

/**
 * 현재 세로 해상도에 맞춰 리턴
 * @param px
 * *
 * @return
 */
fun fromH(context: Context, px: Float): Float {
    return if (px > 0) getHeight(context) * (px / BASE_GUIDE_HEIGHT) else 0f
}

fun setSizeFromW(context: Context, v: View, w: Float, h: Float) {
    setSize(v, fromW(context, w), fromW(context, h))
}

fun setSizeFromH(context: Context, v: View, w: Float, h: Float) {
    setSize(v, fromH(context, w), fromH(context, h))
}

fun setSize(v: View, w: Float, h: Float) {
    if (w > 0) v.layoutParams.width = w.toInt()
    if (h > 0) v.layoutParams.height = h.toInt()
}

fun setMarginFromW(context: Context, v: View, l: Float, t: Float, r: Float, b: Float) {
    setMargin(v, fromW(context, l), fromW(context, t), fromW(context, r), fromW(context, b))
}

fun setMarginFromH(context: Context, v: View, l: Float, t: Float, r: Float, b: Float) {
    setMargin(v, fromH(context, l), fromH(context, t), fromH(context, r), fromH(context, b))
}

fun setMargin(v: View, l: Float, t: Float, r: Float, b: Float) {
    (v.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = l.toInt()
    (v.layoutParams as ViewGroup.MarginLayoutParams).topMargin = t.toInt()
    (v.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = r.toInt()
    (v.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = b.toInt()
}
