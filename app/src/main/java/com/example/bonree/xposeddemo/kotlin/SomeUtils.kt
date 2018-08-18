package com.example.bonree.xposeddemo.kotlin

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.content.Context
import android.view.View
import android.widget.Toast

fun toast(context: Context, toast: String) = Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()

fun Application.toast(toast: String) = toast(this, toast)
fun Activity.toast(toast: String) = toast(this, toast)
fun Fragment.toast(toast: String) = toast(this.activity!!, toast)
fun View.toast(toast: String) = toast(this.context, toast)