package com.example.appcalculator.utils

import android.content.Context
import android.util.DisplayMetrics




 class ScreenUtil {
    companion object{
         fun convertDpToPixel(dp: Int, context: Context?): Int {
             if (context != null) {
                 return dp * (context.resources.displayMetrics.densityDpi as Int / DisplayMetrics.DENSITY_DEFAULT)
             }
             return 0
        }
         fun convertPixelsToDp(px: Int, context: Context?): Int {
             if (context != null) {
                 return px / (context.resources.displayMetrics.densityDpi as Int / DisplayMetrics.DENSITY_DEFAULT)
             }
             return 0
        }

    }

}