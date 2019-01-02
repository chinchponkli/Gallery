package com.arjit.githubprviewer.util

import android.graphics.Color
import java.lang.IllegalArgumentException

object ColorUtil {

    @JvmStatic
    fun parseColor(color: String): Int {

        return try {
            if (!color.startsWith("#")) {
                return Color.parseColor("#$color")
            }
            Color.parseColor(color)
        } catch (e: IllegalArgumentException) {
            Color.BLUE
        }
    }

    @JvmStatic
    fun isColorDark(colorString: String): Boolean {
        val color = parseColor(colorString)
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }
}
