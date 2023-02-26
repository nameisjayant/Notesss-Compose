package com.nameisjayant.noteappcompose.utils

import android.content.Context
import android.widget.Toast


fun Context.showToast(
    msg:String,
    duration:Int = Toast.LENGTH_SHORT
) = Toast.makeText(this,msg,duration).show()