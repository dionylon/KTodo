package com.dionysun.ktodo.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class Commons {
    companion object {
        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat("yyy.MM.dd")
    }
}