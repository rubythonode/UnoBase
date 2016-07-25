package kim.uno.kotlin.base.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context

private var mProgressDialog: ProgressDialog? = null
private var mPreMessage: String? = null

val isShowing: Boolean = mProgressDialog!!.isShowing

fun showProgress(context: Context, message: Int) = showProgress(context, context.getString(message))

fun showProgress(context: Context, message: String = "Loading...", isCancelable: Boolean = false) {
    if (isShowing) {
        if (mPreMessage != message) {
            mPreMessage = message

            // 재사용 가능
            if (context is Activity) {
                context.runOnUiThread(Runnable { mProgressDialog!!.setMessage(message) })

                // 재사용이 불가능
            } else {
                mProgressDialog!!.dismiss()
                showProgress(context, message)
            }
        }
    } else {
        mProgressDialog = ProgressDialog(context)
        mPreMessage = message

        mProgressDialog = if (mProgressDialog == null) ProgressDialog(context) else mProgressDialog
        mProgressDialog!!.setMessage(message)
        mProgressDialog!!.setCancelable(isCancelable)

        if (context is Activity) {
            context.runOnUiThread(Runnable { mProgressDialog!!.show() })
        } else {
            mProgressDialog!!.show()
        }
    }
}

fun dismissProgress() {
    mProgressDialog!!.dismiss()
    mProgressDialog = null
    mPreMessage = null
}