package com.dionysun.ktodo.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dionysun.ktodo.util.Commons
import java.util.*


@BindingAdapter("android:text")
fun setText(view: View, date: Calendar) {
    (view as TextView ).text = Commons.dateFormat.format(date.time).toString()
}
