package kim.uno.sample.mediabutton

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent

class MediaButtonReceiver : BroadcastReceiver() {

    override fun onReceive(cxt: Context, intent: Intent) {

        val event: KeyEvent?
        val action: Int

        Log.i("uno", "intent.getAction(): " + intent.action)
        if (Intent.ACTION_MEDIA_BUTTON != intent.action) {
            return
        }

        event = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_KEY_EVENT) as KeyEvent

        Log.i("uno", "EXTRA_KEY_EVENT: " + event)
        if (event == null) {
            return
        }


        Log.i("uno", "event.getAction(): " + event.action)
        action = event.action
        if (true) return

        when (event.keyCode) {
            KeyEvent.KEYCODE_HEADSETHOOK ->

                if (action == KeyEvent.ACTION_UP) {

                } else if (action == KeyEvent.ACTION_DOWN) {

                }
        }

        // Stop other apps from seeing the event and interfering with our
        // actions.
        resultData = null
        abortBroadcast()
    }

}