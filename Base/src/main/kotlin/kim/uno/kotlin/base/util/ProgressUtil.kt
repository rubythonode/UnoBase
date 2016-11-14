package kim.uno.kotlin.base.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context

object ProgressUtil {

    private var mProgressDialog: ProgressDialog? = null
    private var mPreMessage: String? = null

    @JvmStatic
    fun isShowing(): Boolean = mProgressDialog?.isShowing?: false

    @JvmStatic
    fun show(context: Context, message: Int) = show(context, context.getString(message))

    @JvmStatic
    fun show(context: Context, message: String = "Loading...", isCancelable: Boolean = false) {
        if (isShowing()) {
            if (mPreMessage != message) {
                mPreMessage = message

                // 재사용 가능
                if (context is Activity) {
                    context.runOnUiThread(Runnable { mProgressDialog?.setMessage(message) })

                    // 재사용이 불가능
                } else {
                    mProgressDialog?.dismiss()
                    show(context, message)
                }
            }
        } else {
            mProgressDialog = ProgressDialog(context)
            mPreMessage = message

            mProgressDialog = if (mProgressDialog == null) ProgressDialog(context) else mProgressDialog
            mProgressDialog?.setMessage(message)
            mProgressDialog?.setCancelable(isCancelable)

            if (context is Activity) {
                context.runOnUiThread(Runnable { mProgressDialog?.show() })
            } else {
                mProgressDialog?.show()
            }
        }
    }

    @JvmStatic
    fun dismiss() {
        mProgressDialog?.dismiss()
        mProgressDialog = null
        mPreMessage = null
    }

}