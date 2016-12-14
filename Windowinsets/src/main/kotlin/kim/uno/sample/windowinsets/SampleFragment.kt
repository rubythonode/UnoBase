package kim.uno.sample.windowinsets

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import kotlinx.android.synthetic.main.fragment_sample.view.*

class SampleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater?.inflate(R.layout.fragment_sample, container, false)
        if (activity is MainActivity && (activity as MainActivity).insets != null) {
            view?.scroll_view?.setPadding(
                    view?.scroll_view.paddingLeft,
                    view?.scroll_view.paddingTop + (activity as MainActivity).insets!!.systemWindowInsetTop,
                    view?.scroll_view.paddingRight,
                    view?.scroll_view.paddingBottom + (activity as MainActivity).insets!!.systemWindowInsetBottom)
        }
        return view
    }

    fun getStatusBarHeight(): Int {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return 0

        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimension(resourceId).toInt() else 0
    }

    fun getNavigationBarheight(): Int {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return 0

        var hasNavigationBar: Boolean

        val boolResourceId = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        val heightResourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        if (boolResourceId > 0) {
            hasNavigationBar = resources.getBoolean(boolResourceId)
        } else {
            val hasMenuKey = ViewConfiguration.get(activity).hasPermanentMenuKey()
            val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            hasNavigationBar = !hasMenuKey && !hasBackKey
        }

        return if (hasNavigationBar && heightResourceId > 0) resources.getDimension(heightResourceId).toInt() else 0
    }

}