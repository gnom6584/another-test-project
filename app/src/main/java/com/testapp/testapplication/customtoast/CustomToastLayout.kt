package com.testapp.testapplication.customtoast

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CustomToastLayout : FrameLayout, CoroutineScope {

    interface Adapter<T> where T : Adapter.ViewHolder {

        abstract class ViewHolder(val view: View)

        fun createToastView(text: String, layoutInflater: LayoutInflater, parent: ViewGroup) : ViewHolder

        fun beforeDestroy(holder: ViewHolder)

        fun animationDuration() : Long
    }

    var adapter : Adapter<*>? = null

    val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val layoutInflater = LayoutInflater.from(context)

    fun showToast(text: String, durationMs: Long) {

        if(adapter == null)
            return

        val adapter = adapter!!

        val viewHolder = adapter.createToastView(text, layoutInflater, this)
        addView(viewHolder.view)
        launch {
            val beforeDestroyTime = adapter.animationDuration()
            delay(durationMs - beforeDestroyTime)
            adapter.beforeDestroy(viewHolder)
            delay(beforeDestroyTime)
            removeView(viewHolder.view)
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

}