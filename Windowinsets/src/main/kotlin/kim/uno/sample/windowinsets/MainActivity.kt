package kim.uno.sample.windowinsets

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v4.view.WindowInsetsCompat
import android.support.v7.app.AppCompatActivity
import kim.uno.kotlin.base.util.LogUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var adapter: SamplePagerAdapter? = null
    var insets: WindowInsetsCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LogUtil.logEnable = true

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView, { v, insets ->
            this.insets = insets
            initView()
            insets
        })
    }

    fun initView() {
        adapter = SamplePagerAdapter(supportFragmentManager)
        pager.adapter = adapter
        pager.setPageTransformer(false, getPageTransformer())
    }

    fun getPageTransformer() = ViewPager.PageTransformer { view, position ->

        val MIN_SCALE = 0.7f

        val pageWidth = view.width

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.alpha = 0f

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.alpha = 1 + position

            // Counteract the default slide transition
            view.translationX = pageWidth * -position

            // Scale the page down (between MIN_SCALE and 1)
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.alpha = 1f
            view.translationX = 0f
            view.scaleX = 1f
            view.scaleY = 1f

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.alpha = 0f
        }
    }


}
