package com.test.breakingbadcharacters

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.ybq.android.spinkit.style.ThreeBounce
import kotlinx.android.synthetic.main.splash_activity.*
import kotlinx.coroutines.*
import okhttp3.internal.Util

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        checkInternetAndSetupView()
    }

    /*** function checks for internet connection and setup the progress bar*/
    fun checkInternetAndSetupView() {
        if (!Utils.isNetworkConnected(this)) {
            progress_bar.visibility = View.GONE
            loadingTextView.apply {
                text = getString(R.string.no_network_connection)
                gravity = Gravity.CENTER
                textSize = 20.0f
                background = ContextCompat.getDrawable(this@SplashActivity, R.drawable.layout_bg)
                setOnClickListener {
                    checkInternetAndSetupView()
                }
            }

        } else {
            progress_bar.indeterminateDrawable = ThreeBounce()
            progress_bar.visibility = View.VISIBLE
            loadingTextView.apply {
                text = getString(R.string.loading_character)
                gravity = Gravity.END
                textSize = 30.0f
                background = null
            }
            CoroutineScope(Dispatchers.IO).launch {
                timerTask(this, 3)
            }.start()
        }
    }

    /**suspend function to wait for a certain minutes before execution*/
    suspend fun timerTask(coroutineScope: CoroutineScope, seconds: Long) {
        var counter = 0L
        val start = System.currentTimeMillis()
        while (true) {
            val end = System.currentTimeMillis()
            counter = (end - start) / 1000;
            if (seconds == counter) {
                startActivity(Intent(this, SearchResultActivity::class.java))
                overridePendingTransition(
                    R.anim.slide_from_center_to_left_anim,
                    R.anim.slide_from_right_to_center_anim
                )
                finish()
                coroutineScope.cancel()
            }
            delay(1000)
        }
    }

}