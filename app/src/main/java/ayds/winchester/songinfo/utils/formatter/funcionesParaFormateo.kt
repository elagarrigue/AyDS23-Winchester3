package ayds.winchester.songinfo.utils.formatter

import ayds.winchester.songinfo.home.model.entities.Song


private fun formatReleaseDate(song: Song.SpotifySong) =
    when (song.releaseDatePrecision){
        "year" -> formatDateWithYearPrecision(song.releaseDate)
        "month" -> formatDateWithMonthPrecision(song.releaseDate)
        "day" -> formatDateWithDayPrecision(song.releaseDate)
        else -> ""
    }

private fun formatDateWithDayPrecision(date:String) =
    "${getDaySubstring(date)}/${getMonthSubstring(date)}/${getYearSubstring(date)}"
private fun getDaySubstring(date: String) = date.takeLast(2)
private fun getMonthSubstring(date: String) = date.substring(5..6)
private fun getYearSubstring(date: String) = date.take(4)

private fun formatDateWithMonthPrecision(date: String):String {
    val year = getYearSubstring(date)
    val monthNumber = getMonthSubstring(date)
    val monthName = getMonthName(monthNumber)
    return "$monthName, $year"
}

private fun getMonthName(monthNumber:String) =
    when(monthNumber){
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> ""
    }

private fun formatDateWithYearPrecision(date: String) =
    if (isLeapYear(date.toInt()))
        "$date (leap year)"
    else
        "$date (not a leap year)"

private fun isLeapYear(year:Int) = (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))