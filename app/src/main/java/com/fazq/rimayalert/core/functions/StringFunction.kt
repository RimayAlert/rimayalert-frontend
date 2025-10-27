package com.fazq.rimayalert.core.functions

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import java.util.Locale

fun highlightText(queryText: String, dataText: String): CharSequence {
    try {
        return if (queryText.isNotEmpty()) {
            val startPos: Int = dataText.lowercase(Locale.getDefault())
                .indexOf(queryText.lowercase(Locale.getDefault()))
            val endPos: Int = startPos + queryText.length
            if (startPos != -1) {
                val spannable: Spannable = SpannableString(dataText)
                val colorStateList = ColorStateList(arrayOf(intArrayOf()), intArrayOf(Color.BLUE))
                val textAppearanceSpan =
                    TextAppearanceSpan(null, Typeface.BOLD, -1, colorStateList, null)
                spannable.setSpan(
                    textAppearanceSpan,
                    startPos,
                    endPos,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable
            } else {
                dataText
            }
        } else {
            dataText
        }
    } catch (exception: Exception) {
        exception.printStackTrace()
    }
    return dataText
}


fun returnTextSecure(text: String?): String {
    return if (text.isNullOrBlank()) "" else text
}

fun validateCoordinates(coordenada: String): String {
    val regex = Regex("^(-?\\d+(\\.\\d+)?),\\s*(-?\\d+(\\.\\d+)?)$")
    val matchResult = regex.matchEntire(coordenada)

    return if (matchResult != null) {
        try {
            val latitud = matchResult.groupValues[1].toDoubleOrNull() ?: 0.0
            val longitud = matchResult.groupValues[3].toDoubleOrNull() ?: 0.0
            "$latitud, $longitud"
        } catch (e: NumberFormatException) {
            "0.0, 0.0"
        }
    } else {
        "0.0, 0.0"
    }
}


fun String.isPrintable(): Boolean {
    return all { it.isPrintableAscii() }
}

fun Char.isPrintableAscii(): Boolean {
    return toInt() in 32..126
}

fun String.removeInvalidCharacters(): String {
    val indexFirst = this.indexOfFirst { it == '�' }
    val indexLast = this.indexOfLast { it == '�' }
    if (indexFirst != -1 && indexLast != -1) {
        return this.substring(0, indexFirst) + this.substring(indexLast + 1)
    }
    return this
//    return "Binary data or non-printable characters detected, not logged"
}