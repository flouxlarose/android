package com.decinfo.examen2

import android.content.Context
import android.widget.SimpleAdapter


class AdapterCustom (
    context: Context,
    data: List<out Map<String, *>>,
    resource: Int,
    from: Array<String>,
    to: IntArray): SimpleAdapter(context, data, resource, from, to)