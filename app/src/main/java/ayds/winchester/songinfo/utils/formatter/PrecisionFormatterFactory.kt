package ayds.winchester.songinfo.utils.formatter

object PrecisionFormatterFactory {

    fun getPrecisionFormatter(precision:String):PrecisionFormatter =
        when(precision) {
            "year" -> YearFormatter()
            "month" -> MonthFormatter()
            else -> DayFormatter()
        }
}