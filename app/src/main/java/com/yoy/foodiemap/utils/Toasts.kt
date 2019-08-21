package com.yoy.foodiemap.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by yuyang on 2017/6/30.
 */



fun Context.toast(msg: Int,yOffset: Int = 0) = ToastInstance.show(this,msg,Toast.LENGTH_SHORT,yOffset)

fun Context.toast(msg: CharSequence,yOffset: Int = 0) = ToastInstance.show(this,msg,Toast.LENGTH_SHORT,yOffset)

fun Context.toastLong(msg: Int,yOffset: Int = 0) = ToastInstance.show(this,msg,Toast.LENGTH_LONG,yOffset)

fun Context.toastLong(msg: CharSequence,yOffset: Int = 0) = ToastInstance.show(this,msg,Toast.LENGTH_LONG,yOffset)

fun Fragment.toast(msg: CharSequence,yOffset: Int = 0) = ToastInstance.show(this.requireContext(),msg,Toast.LENGTH_SHORT,yOffset)


object ToastInstance{
    var toast: Toast? = null


    fun show(context: Context,msg: Int,duration: Int,yOffset: Int){
        cancel()
        toast = Toast.makeText(context,msg,duration)
        if (yOffset != 0)
            toast?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM,0,yOffset)
        toast?.show()
    }

    fun show(context: Context,msg: CharSequence,duration: Int,yOffset: Int){
        cancel()
        toast = Toast.makeText(context,msg,duration)
        if (yOffset != 0)
            toast?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM,0,yOffset)
        toast?.show()
    }


    fun cancel(){
        toast?.cancel()
        toast = null
    }
}
