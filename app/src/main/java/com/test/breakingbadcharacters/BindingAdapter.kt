package com.test.breakingbadcharacters

import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view).load(url).into(view)
}

@BindingAdapter("visible")
fun visibility(view: ImageView, status: String) {
    if (status == view.context.getString(R.string.alive)) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}


@BindingAdapter("array", "title")
fun setArray(view: TextView, array: List<Any>?, title: String?) {
    var count = 0
    val strBuffer = StringBuffer()
    var strTemp = ""
    strBuffer.append(title)
    strBuffer.append(": ")
    if (array != null) {
        if (array.size == 1) {
            strBuffer.append(array[0])
            view.text = strBuffer
        } else {
            for (str in array) {
                strTemp = if (str is Int) {
                    str.toString()
                } else {
                    str as String
                }
                when (count) {
                    array.size - 1 -> {
                        strBuffer.append("and ")
                        strBuffer.append(strTemp)
                    }
                    array.size - 2 -> {
                        strBuffer.append(strTemp)
                        strBuffer.append(" ")
                    }
                    else -> {
                        strBuffer.append(strTemp)
                        strBuffer.append(", ")
                    }
                }
                count++
            }
            view.text = strBuffer
        }
    } else {
        view.visibility = View.GONE
    }
}





