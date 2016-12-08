package kim.uno.kotlin.base.annotation

import android.support.v4.app.Fragment
import android.text.TextUtils

object AnnotationsInflater {

    @JvmStatic
    fun inflate(fragment: Fragment) {

        try {
            for (field in fragment.javaClass.declaredFields) {
                val annotation = field.getAnnotation(FragmentArg::class.java)
                if (annotation != null) {
                    val accessible = field.isAccessible
                    field.isAccessible = true

                    val key = if (!TextUtils.isEmpty(annotation!!.key)) annotation!!.key else field.name
                    if (fragment.arguments != null && fragment.arguments.containsKey(key)) {
                        field.set(fragment, fragment.arguments.get(key))
                    }

                    field.isAccessible = accessible
                }
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

}