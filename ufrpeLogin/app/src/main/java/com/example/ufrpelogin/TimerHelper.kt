package com.example.ufrpelogin
import android.os.CountDownTimer

class TimerHelper(private val totalTime: Long, private val interval: Long, private val callback: TimerCallback) :
    CountDownTimer(totalTime, interval) {

    interface TimerCallback {
        fun onTick(millisUntilFinished: Long)
        fun onFinish()
    }

    override fun onTick(millisUntilFinished: Long) {
        callback.onTick(millisUntilFinished)
    }

    override fun onFinish() {
        callback.onFinish()
    }
}
