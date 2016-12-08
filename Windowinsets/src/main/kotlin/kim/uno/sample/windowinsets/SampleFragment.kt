package kim.uno.sample.windowinsets

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kim.uno.kotlin.base.annotation.AnnotationsInflater
import kim.uno.kotlin.base.annotation.FragmentArg
import kim.uno.kotlin.base.util.LogUtil
import kotlinx.android.synthetic.main.fragment_sample.view.*

class SampleFragment : Fragment() {

    @FragmentArg(key = "argKey")
    var testArg: String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater?.inflate(R.layout.fragment_sample, container, false)

        if (userVisibleHint) {
            ViewCompat.setOnApplyWindowInsetsListener(activity.window.decorView, { v, insets ->
                LogUtil.i("insets: " + insets.systemWindowInsetTop)
                view?.scroll_view?.setPadding(
                        view?.scroll_view.paddingLeft,
                        view?.scroll_view.paddingTop + insets.systemWindowInsetTop,
                        view?.scroll_view.paddingRight,
                        view?.scroll_view.paddingBottom + insets.systemWindowInsetBottom)
                insets
            })
        }
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        AnnotationsInflater.inflate(this)
        if (isVisibleToUser) LogUtil.i("testArg: " + testArg)
    }

}