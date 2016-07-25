package kim.uno.kotlin.base.util

import android.app.Activity
import android.content.Context
import android.widget.Toast

private var toast: Toast? = null

fun showToast(context: Context, message: Int, duration: Int = Toast.LENGTH_SHORT) {
    showToast(context, context.getString(message), duration)
}

fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    try {
        if (context is Activity) {
            context.runOnUiThread(Runnable { makeText(context, message, duration) })
        } else {
            makeText(context, message, duration)
        }
    } catch (e: Exception) {
        logE(e)
    }
}

private fun makeText(context: Context, message: String, duration: Int) {
    dismissToast()

//    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    View innerView = inflater.inflate(R.layout.toast, null);
//    innerView.setPadding(0, 0, 0, (int) (PrefUtil.getInt(context, Consts.SP_DISPLAY_HEIGHT) * 0.1f));
//    ((TextView) innerView.findViewById(R.id.tv_toast)).setText(message);

    toast = Toast.makeText(context, message, duration)
//    toast.setView(innerView);
//    toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
    toast!!.duration = duration
    toast!!.show()
}

fun dismissToast() {
    toast!!.cancel()
    toast = null
}
