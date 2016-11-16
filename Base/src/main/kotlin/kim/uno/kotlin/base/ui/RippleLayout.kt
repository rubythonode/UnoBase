package kim.uno.kotlin.base.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.Transformation
import android.widget.RelativeLayout

class RippleLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RelativeLayout(context, attrs, defStyleAttr) {

    private var mClipPath: Path
    private var mClipRadius = 0f
    private var mClipCenterX: Int = 0
    private var mClipCenterY = 0
    private var mAnimation: Animation? = null
    private var mIsContentShown = true

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        mClipPath = Path()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mClipCenterX = w / 2
        mClipCenterY = h / 2
        if (!mIsContentShown) {
            mClipRadius = 0f
        } else {
            mClipRadius = (Math.sqrt((w * w + h * h).toDouble()) / 2).toFloat()
        }

        super.onSizeChanged(w, h, oldw, oldh)
    }

    var clipRadius: Float
        get() = mClipRadius
        set(clipRadius) {
            mClipRadius = clipRadius
            invalidate()
        }

    var isContentShown: Boolean
        get() = mIsContentShown
        set(isContentShown) {
            mIsContentShown = isContentShown
            if (mIsContentShown) {
                mClipRadius = 0f
            } else {
                mClipRadius = getMaxRadius(mClipCenterX, mClipCenterY)
            }
            invalidate()
        }

    fun show(listener: Animation.AnimationListener?) {
        show(DEFAULT_DURATION, listener)
    }

    @JvmOverloads fun show(duration: Int = DEFAULT_DURATION, listener: Animation.AnimationListener? = null) {
        show(width / 2, height / 2, duration, listener)
    }

    fun show(x: Int, y: Int, listener: Animation.AnimationListener?) {
        show(x, y, DEFAULT_DURATION, listener)
    }

    @JvmOverloads fun show(halfWidth: Int, halfHeight: Int, duration: Int = DEFAULT_DURATION, listener: Animation.AnimationListener? = null) {
        var x = Math.max(0, Math.min(width, halfWidth))
        var y = Math.max(0, Math.min(width, halfHeight))

        mClipCenterX = x
        mClipCenterY = y
        val maxRadius = getMaxRadius(x, y)

        clearAnimation()

        mAnimation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                clipRadius = interpolatedTime * maxRadius
            }
        }
        mAnimation!!.interpolator = BakedBezierInterpolator()
        mAnimation!!.duration = duration.toLong()
        mAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation) {
                listener?.onAnimationRepeat(animation)
            }

            override fun onAnimationStart(animation: Animation) {
                mIsContentShown = true
                listener?.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animation) {
                listener?.onAnimationEnd(animation)
            }
        })
        startAnimation(mAnimation)
    }

    @JvmOverloads fun hide(duration: Int = DEFAULT_DURATION) {
        hide(width / 2, height / 2, duration, null)
    }

    fun hide(listener: Animation.AnimationListener?) {
        hide(DEFAULT_DURATION, listener)
    }

    fun hide(duration: Int, listener: Animation.AnimationListener?) {
        hide(width / 2, height / 2, duration, listener)
    }

    fun hide(x: Int, y: Int, listener: Animation.AnimationListener?) {
        hide(x, y, DEFAULT_DURATION, listener)
    }

    @JvmOverloads fun hide(x: Int, y: Int, duration: Int = DEFAULT_DURATION, listener: Animation.AnimationListener? = null) {
        var x = x
        var y = y

        if (x < 0) {
            x = 0
        }

        if (x > width) {
            x = width
        }

        if (y < 0) {
            y = 0
        }

        if (y > height) {
            y = height
        }

        if (x != mClipCenterX || y != mClipCenterY) {
            mClipCenterX = x
            mClipCenterY = y
            mClipRadius = getMaxRadius(x, y)
        }

        clearAnimation()

        mAnimation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                clipRadius *= (1 - interpolatedTime)
            }
        }
        mAnimation!!.interpolator = BakedBezierInterpolator()
        mAnimation!!.duration = duration.toLong()
        mAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                mIsContentShown = false
                listener?.onAnimationStart(animation)
            }

            override fun onAnimationRepeat(animation: Animation) {
                listener?.onAnimationRepeat(animation)
            }

            override fun onAnimationEnd(animation: Animation) {
                listener?.onAnimationEnd(animation)
            }
        })
        startAnimation(mAnimation)
    }

    @JvmOverloads fun next(duration: Int = DEFAULT_DURATION) {
        next(width / 2, height / 2, duration, null)
    }

    fun next(listener: Animation.AnimationListener?) {
        next(DEFAULT_DURATION, listener)
    }

    fun next(duration: Int, listener: Animation.AnimationListener?) {
        next(width / 2, height / 2, duration, listener)
    }

    fun next(x: Int, y: Int, listener: Animation.AnimationListener?) {
        next(x, y, DEFAULT_DURATION, listener)
    }

    @JvmOverloads fun next(x: Int, y: Int, duration: Int = DEFAULT_DURATION, listener: Animation.AnimationListener? = null) {
        val childCount = childCount
        if (childCount > 1) {
            for (i in 0..childCount - 1) {
                val child = getChildAt(i)
                if (i == 0) {
                    bringChildToFront(child)
                }
            }
            show(x, y, duration, listener)
        }
    }

    private fun getMaxRadius(x: Int, y: Int): Float {
        val h = Math.max(x, width - x)
        val v = Math.max(y, height - y)
        return Math.sqrt((h * h + v * v).toDouble()).toFloat()
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        if (indexOfChild(child) == childCount - 1) {
            val result: Boolean
            mClipPath.reset()
            mClipPath.addCircle(mClipCenterX.toFloat(), mClipCenterY.toFloat(), mClipRadius, Path.Direction.CW)
            canvas.save()
            canvas.clipPath(mClipPath)
            result = super.drawChild(canvas, child, drawingTime)
            canvas.restore()
            return result
        } else {
            return super.drawChild(canvas, child, drawingTime)
        }
    }

    companion object {
        val DEFAULT_DURATION = 600
    }

    class BakedBezierInterpolator : Interpolator {

        override fun getInterpolation(input: Float): Float {
            if (input >= 1.0f) {
                return 1.0f
            }

            if (input <= 0f) {
                return 0f
            }

            val position = Math.min((input * (VALUES.size - 1)).toInt(), VALUES.size - 2)
            val quantized = position * STEP_SIZE
            val difference = input - quantized
            val weight = difference / STEP_SIZE

            return VALUES[position] + weight * (VALUES[position + 1] - VALUES[position])
        }

        companion object {
            private val VALUES = floatArrayOf(0.0f, 0.0002f, 0.0009f, 0.0019f, 0.0036f, 0.0059f, 0.0086f, 0.0119f, 0.0157f, 0.0209f, 0.0257f, 0.0321f, 0.0392f, 0.0469f, 0.0566f, 0.0656f, 0.0768f, 0.0887f, 0.1033f, 0.1186f, 0.1349f, 0.1519f, 0.1696f, 0.1928f, 0.2121f, 0.237f, 0.2627f, 0.2892f, 0.3109f, 0.3386f, 0.3667f, 0.3952f, 0.4241f, 0.4474f, 0.4766f, 0.5f, 0.5234f, 0.5468f, 0.5701f, 0.5933f, 0.6134f, 0.6333f, 0.6531f, 0.6698f, 0.6891f, 0.7054f, 0.7214f, 0.7346f, 0.7502f, 0.763f, 0.7756f, 0.7879f, 0.8f, 0.8107f, 0.8212f, 0.8326f, 0.8415f, 0.8503f, 0.8588f, 0.8672f, 0.8754f, 0.8833f, 0.8911f, 0.8977f, 0.9041f, 0.9113f, 0.9165f, 0.9232f, 0.9281f, 0.9328f, 0.9382f, 0.9434f, 0.9476f, 0.9518f, 0.9557f, 0.9596f, 0.9632f, 0.9662f, 0.9695f, 0.9722f, 0.9753f, 0.9777f, 0.9805f, 0.9826f, 0.9847f, 0.9866f, 0.9884f, 0.9901f, 0.9917f, 0.9931f, 0.9944f, 0.9955f, 0.9964f, 0.9973f, 0.9981f, 0.9986f, 0.9992f, 0.9995f, 0.9998f, 1.0f, 1.0f)
            private val STEP_SIZE = 1.0f / (VALUES.size - 1)
        }
    }

}