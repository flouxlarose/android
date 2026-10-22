package com.decinfo.tp1

import android.content.Context
import android.widget.ImageView
import android.widget.SimpleAdapter
import com.bumptech.glide.Glide

class AdapterCustom (
    context: Context,
    data: List<out Map<String, *>>,
    resource: Int,
    from: Array<String>,
    to: IntArray): SimpleAdapter(context, data, resource, from, to){

    override fun setViewImage(v: ImageView, value: String) {
        Glide.with(v.context)
            .load(value)
            .into(v)
    }
}