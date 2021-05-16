package com.testapp.testapplication

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnDetach
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.testapp.testapplication.customtoast.CustomToastLayout
import com.testapp.testapplication.utility.ToastGenerator
import com.testapp.testapplication.inputfields.ValidationError
import com.testapp.testapplication.utility.date.DateConverter
import com.testapp.testapplication.utility.date.DefaultDateConverter
import java.util.*

interface OnClickListener {
    fun onClick()
}

interface OnFocusChangeListener {
    fun onFocusChange(hasFocus: Boolean)
}


interface OnActionCallback {
    fun onAction()
}

interface OnDateChangedListener {
    fun onDateChanged(date: Date)
}

@BindingAdapter("app:text_by_validation_error")
fun setTextByValidationError(textView: TextView, error: ValidationError) {
    val context = textView.context
    textView.text = when(error) {
        ValidationError.None -> ""
        ValidationError.Empty -> context.getString(R.string.required_field)
        ValidationError.PasswordsEquality -> context.getString(R.string.password_mismatch)
        ValidationError.Email -> context.getString(R.string.wrong_email_format)
        ValidationError.NoVariantsSelected -> context.getString(R.string.at_least_one_option_must_be_selected)
    }
}

@BindingAdapter("app:onClick")
fun setOnClickListener(view: View, onClickListener: OnClickListener) {
    view.setOnClickListener {
        onClickListener.onClick()
    }
}

@BindingAdapter("app:viewId")
fun setOnClickListener(view: View, id: Int) {
    view.id = id
}

@BindingAdapter("app:setFocusChangeListener")
fun setOnFocusChangeListener(view: View, onFocusChangeListener: OnFocusChangeListener) {
    view.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus -> onFocusChangeListener.onFocusChange(hasFocus)}
}

@BindingAdapter("app:edit_text_show_password")
fun setEditTextShowPassword(editText: EditText, state: Boolean) {
    editText.transformationMethod = if(state) null else PasswordTransformationMethod()
}

@BindingAdapter("app:on_text_changed_no_value")
fun onTextChanged(editText: EditText, onActionCallback: OnActionCallback) {
    editText.doOnTextChanged { text, start, before, count -> onActionCallback.onAction() }
}

@BindingAdapter("app:custom_toast_layout_adapter")
fun setCustomToastLayoutAdapter(layout: CustomToastLayout, adapter: CustomToastLayout.Adapter<*>) {
    layout.adapter = adapter
}

@BindingAdapter(value = ["app:custom_toast_layout_messages_listener", "app:custom_toast_layout_toast_duration"], requireAll = true)
fun setCustomToastLayoutAdapter(layout: CustomToastLayout, toastGenerator: ToastGenerator, toastDuration: Long) {
    toastGenerator.listener.observe(layout.context as LifecycleOwner, Observer {
        if(it != null)
            layout.showToast(it, toastDuration)
    })
}

@BindingAdapter(value = ["app:text_view_as_date_picker_button"])
fun setAsDatePickerButton(textView: TextView, onDateChangedListener: OnDateChangedListener) {
    textView.setOnClickListener {
        val context = textView.context

        val date = Calendar.getInstance().apply {
            with(context.resources) {
                set(getInteger(R.integer.init_birth_year), getInteger(R.integer.init_birth_month), getInteger(R.integer.init_birth_day))
            }
        }

        val dialog = DatePickerDialog(
            textView.context, R.style.SpinnerDatePickerStyle,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                onDateChangedListener.onDateChanged(Calendar.getInstance().apply { set(year, month, day) }.time)
            },
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()

        textView.doOnDetach {
            dialog.dismiss()
        }
    }
}

@BindingAdapter(value = ["app:text_by_date_value", "app:text_by_date_converter"], requireAll = false)
fun setTextByDate(textView: TextView, date: Date?, dateConverter: DateConverter?) {
    if(date == null)
        return

    textView.text = (dateConverter ?: DefaultDateConverter()).convertToString(date)
}

@BindingAdapter("app:clipToOutline")
fun setClipToOutline(view: View, state: Boolean) {
    view.clipToOutline = state
}

@BindingAdapter("app:imageView_bmp_src")
fun setImageViewBitmap(imageView: ImageView, bitmap: Bitmap?) {
    imageView.setImageBitmap(bitmap)
}

@BindingAdapter("app:frag_container_navigator")
fun setNavigator(navHostFragment: FragmentContainerView, navigator: Navigator) {
    val navController = navHostFragment.findNavController()
    navigator.navigationListener = object : Navigator.Listener {
        override fun receiveNavigationAction(action: Int, bundle: Bundle?) {
            navController.navigate(action, bundle)
        }
    }
}