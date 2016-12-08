package kim.uno.sample.mediabutton

import android.content.ComponentName
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.registerMediaButtonEventReceiver(ComponentName(this, MediaButtonReceiver::class.java))
    }
}
