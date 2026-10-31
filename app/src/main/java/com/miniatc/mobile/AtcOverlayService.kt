package com.miniatc.mobile

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

class AtcOverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private var overlayView: View? = null
    private lateinit var params: WindowManager.LayoutParams

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        createOverlay()
    }

    private fun createOverlay() {

        val panel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(14), dp(10), dp(14), dp(12))

            background = GradientDrawable().apply {
                setColor(Color.rgb(15, 28, 47))
                cornerRadius = dp(14).toFloat()
                setStroke(dp(1), Color.rgb(56, 217, 245))
            }
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val title = TextView(this).apply {
            text = "MINI ATC  •  LIVE"
            textSize = 15f
            setTextColor(Color.rgb(56, 217, 245))
            setPadding(0, 0, dp(12), 0)
        }

        val close = TextView(this).apply {
            text = "✕"
            textSize = 20f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(dp(12), 0, dp(4), 0)

            setOnClickListener {
                stopSelf()
            }
        }

        header.addView(
            title,
            LinearLayout.LayoutParams(0, dp(38), 1f)
        )

        header.addView(
            close,
            LinearLayout.LayoutParams(dp(35), dp(38))
        )

        val message = TextView(this).apply {
            text = "ATC SYSTEM READY\n\nWaiting for flight plan..."
            textSize = 13f
            setTextColor(Color.WHITE)
            setPadding(0, dp(12), 0, dp(8))
        }

        panel.addView(header)
        panel.addView(message)

        params = WindowManager.LayoutParams(
            dp(300),
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = dp(15)
            y = dp(100)
        }

        title.setOnTouchListener(object : View.OnTouchListener {

            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f

            override fun onTouch(
                view: View,
                event: MotionEvent
            ): Boolean {

                when (event.action) {

                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX +
                            (event.rawX - initialTouchX).toInt()

                        params.y = initialY +
                            (event.rawY - initialTouchY).toInt()

                        overlayView?.let {
                            windowManager.updateViewLayout(it, params)
                        }

                        return true
                    }
                }

                return false
            }
        })

        overlayView = panel

        windowManager.addView(panel, params)
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        overlayView?.let {
            if (it.isAttachedToWindow) {
                windowManager.removeView(it)
            }
        }

        overlayView = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
