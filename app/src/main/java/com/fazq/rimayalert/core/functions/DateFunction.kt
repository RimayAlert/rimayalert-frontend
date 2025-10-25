package com.fazq.rimayalert.core.functions

import android.os.Build
import androidx.annotation.RequiresApi
import org.joda.time.DateTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import org.joda.time.Duration as DurationOrg
import java.time.Duration as DurationJava


enum class DateType {
    isAfter,
    isBefore,
    isEqual
}

fun getDateTimeString(): String {
    val calendar: Calendar = GregorianCalendar()
    return SimpleDateFormat(
        "yyyy'-'MM'-'dd' 'HH':'mm':'ss",
        Locale("es", "ES")
    ).format(calendar.time)
}

fun editDateFormat(date: String): String {
    return try {
        val calendar: Calendar = GregorianCalendar()
        calendar.set(
            date.split("-")[0].toInt(),
            date.split("-")[1].toInt() - 1, date.split("-")[2].toInt()
        )
        SimpleDateFormat(
            "EEEE, d 'de' MMMM 'del' yyyy", Locale("es", "ES")
        ).format(calendar.time)
    } catch (e: Exception) {
        date
    }
}

fun howLongTimeBetween(
    dateStart: String = getDateTimeString(),
    dateEnd: String = getDateTimeString(),
    mustBe: DateType = DateType.isEqual,
): List<Int> {
    var seconds = 0
    var minutes = 0
    var hours = 0
    var days = 0
    println("dateStart: $dateStart")
    println("dateEnd: $dateEnd")
    if (knowIfDateIs(dateStart, dateEnd, mustBe)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateTime1 = getFormatLocalDateTime(dateStart)
            val dateTime2 = getFormatLocalDateTime(dateEnd)
            val duration = DurationJava.between(dateTime1, dateTime2)

            days = duration.toDays().toInt()
            hours = (duration.toHours() % 24).toInt()
            minutes = (duration.toMinutes() % 60).toInt()
            seconds = (duration.seconds % 60).toInt()

            println("Días: $days, Horas: $hours, Minutos: $minutes, Segundos: $seconds")
        } else {
            val dateTime1 = getFormatDateTime(dateStart)
            val dateTime2 = getFormatDateTime(dateEnd)
            val duration = DurationOrg(dateTime1, dateTime2)

            days = duration.standardDays.toInt()
            hours = (duration.standardHours % 24).toInt()
            minutes = (duration.standardMinutes % 60).toInt()
            seconds = (duration.standardSeconds % 60).toInt()

            println("Días: $days, Horas: $hours, Minutos: $minutes, Segundos: $seconds")
        }
    }

    return listOf(hours, minutes, seconds)
}

fun lessMinutes(minutes: Int): String {
    try {
        val cal = Calendar.getInstance()
        cal.time = Date()

        var tempDate = cal.time
        println("Fecha actual: $tempDate")

        cal[Calendar.MINUTE] = cal[Calendar.MINUTE] - minutes
        tempDate = cal.time

        println("Hora modificada: $tempDate")
        val date = SimpleDateFormat(
            "yyyy'-'MM'-'dd' 'HH':'mm':'ss",
            Locale("es", "ES")
        ).format(cal.time)
        println(date)
        return date
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return getDateTimeString()
}
fun plusMinutes(minutes: Int): String {
    try {
        val cal = Calendar.getInstance()
        cal.time = Date()

        var tempDate = cal.time
        println("Fecha actual: $tempDate")

        cal[Calendar.MINUTE] = cal[Calendar.MINUTE] + minutes
        tempDate = cal.time

        println("Hora modificada: $tempDate")
        val date = SimpleDateFormat(
            "yyyy'-'MM'-'dd' 'HH':'mm':'ss",
            Locale("es", "ES")
        ).format(cal.time)
        println(date)
        return date
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return getDateTimeString()
}

fun sumSeconds(seconds: Int): String {
    try {
        val cal = Calendar.getInstance()
        cal.time = Date()

        var tempDate = cal.time
        println("Fecha actual: $tempDate")

        cal[Calendar.SECOND] = cal[Calendar.SECOND] + seconds
        tempDate = cal.time

        println("Hora modificada: $tempDate")
        val date = SimpleDateFormat(
            "yyyy'-'MM'-'dd' 'HH':'mm':'ss",
            Locale("es", "ES")
        ).format(cal.time)
        println(date)
        return date
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return getDateTimeString()
}


fun knowIfDateIs(
    dateValue1: String = "",
    dateValue2: String,
    dateType: DateType = DateType.isEqual,
): Boolean {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateCurrent = if (dateValue1.isEmpty()) {
                LocalDateTime.now()
            } else {
                getFormatLocalDateTime(dateValue1)
            }
            val dateLast = getFormatLocalDateTime(dateValue2)
            val diff = dateCurrent.compareTo(dateLast)
            return when (dateType) {
                DateType.isAfter -> dateCurrent.isAfter(dateLast) || diff > 0
                DateType.isBefore -> dateCurrent.isBefore(dateLast) || diff < 0
                DateType.isEqual -> dateCurrent.isEqual(dateLast) || diff == 0
            }
        } else {
            val dateCurrent = if (dateValue1.isEmpty()) {
                DateTime.now()
            } else {
                getFormatDateTime(dateValue1)
            }
            val dateLast = getFormatDateTime(dateValue2)
            return when (dateType) {
                DateType.isAfter -> dateCurrent.isAfter(dateLast)
                DateType.isBefore -> dateCurrent.isBefore(dateLast)
                DateType.isEqual -> dateCurrent.isEqual(dateLast)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFormatLocalDateTime(dateTime: String): LocalDateTime {
    val date = dateTime.split(" ")[0]
    val time = dateTime.split(" ")[1]

    return LocalDateTime.of(
        date.split("-")[0].toInt(),
        date.split("-")[1].toInt(),
        date.split("-")[2].toInt(),
        time.split(":")[0].toInt(),
        time.split(":")[1].toInt(),
        time.split(":")[2].toInt()
    )
}

fun getFormatDateTime(dateTime: String): DateTime {
    val date = dateTime.split(" ")[0]
    val time = dateTime.split(" ")[1]
    return DateTime(
        date.split("-")[0].toInt(),
        date.split("-")[1].toInt(),
        date.split("-")[2].toInt(),
        time.split(":")[0].toInt(),
        time.split(":")[1].toInt(),
        time.split(":")[2].toInt()
    )
}