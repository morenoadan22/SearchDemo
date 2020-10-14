package com.ngmatt.weedmapsandroidcodechallenge.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.hideKeyboard(view: View)  {
    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.toast(message: String?, length: Int = Toast.LENGTH_SHORT) = message?.let {
    Toast.makeText(this, it, length).show()
}