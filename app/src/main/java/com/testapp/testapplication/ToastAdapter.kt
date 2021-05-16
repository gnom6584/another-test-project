package com.testapp.testapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.testapp.testapplication.customtoast.CustomToastLayout
import com.testapp.testapplication.databinding.ToastLayoutBinding

class ToastAdapter(val context: Context) : CustomToastLayout.Adapter<ToastAdapter.ViewHolder>{

    class ViewHolder(view: View) : CustomToastLayout.Adapter.ViewHolder(view) {

    }

    val animationDuration by lazy { context.resources.getInteger(R.integer.custom_toast_animation_duration).toLong() }

    override fun createToastView(
        text: String,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) : CustomToastLayout.Adapter.ViewHolder {
        val view = DataBindingUtil.inflate<ToastLayoutBinding>(layoutInflater, R.layout.toast_layout, parent, false).run {
            textView.text = text
            root
        }

        with(view) {
            alpha = 0.0f
            scaleX = 0.0f
            scaleY = 0.0f
            animate().alpha(1.0f).setDuration(animationDuration).start()
            animate().scaleX(1.0f).setDuration(animationDuration).start()
            animate().scaleY(1.0f).setDuration(animationDuration).start()
        }
        return ViewHolder(view);
    }

    override fun animationDuration() = animationDuration

    override fun beforeDestroy(holder: CustomToastLayout.Adapter.ViewHolder) {
        with(holder.view) {
            animate().alpha(0.0f).setDuration(animationDuration).start()
            animate().scaleX(0.0f).setDuration(animationDuration).start()
            animate().scaleY(0.0f).setDuration(animationDuration).start()
        }
    }
}