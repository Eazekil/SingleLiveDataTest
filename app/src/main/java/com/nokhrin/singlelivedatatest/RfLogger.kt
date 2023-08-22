package com.nokhrin.singlelivedatatest

import android.os.Looper
import android.util.Log


object RfLogger {

    enum class Type {
        LOCAL_ONLY,
        DUPLICATE_TO_CLOUD
    }

    fun d(tag: String, log_message: String?, type: Type = Type.LOCAL_ONLY) {
        Log.v(tag, log_message?:"")
    }


}