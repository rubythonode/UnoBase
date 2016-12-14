package kim.uno.sample.windowinsets

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray

class SamplePagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    val fragments = SparseArray<Fragment>()

    override fun getItem(position: Int): Fragment {
        if (fragments.get(position, null) == null) {
            fragments.put(position, SampleFragment())
        }

        return fragments.get(position)
    }

    override fun getCount(): Int = 4

}