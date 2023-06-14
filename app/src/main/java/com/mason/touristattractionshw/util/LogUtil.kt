package com.mason.touristattractionshw.util

import java.lang.Exception

//import com.mason.touristattractionshw.BuildConfig

class LogUtil {
    companion object {
        val SHOW = false
//        val SHOW = !BuildConfig.DEBUG

        fun d(TAG: String, s: String) {
            if (!SHOW) return
            android.util.Log.d("${this::class.java.simpleName}.$TAG", s)
        }

        fun i(TAG: String, s: String) {
            if (!SHOW) return
            android.util.Log.i("${this::class.java.simpleName}.$TAG", s)
        }

        fun e(TAG: String, s: String) {
            if (!SHOW) return
            android.util.Log.e("${this::class.java.simpleName}.$TAG", s)
        }

        fun e(TAG: String, s: String, e: Exception) {
            if (!SHOW) return
            android.util.Log.e("${this::class.java.simpleName}.$TAG", "$s \n $e" )
        }


    }
}