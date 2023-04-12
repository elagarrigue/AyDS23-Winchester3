package ayds.winchester.songinfo.utils.formatter

object PrecisionFormatterFactory {
    //Crear Enumerado
    fun getPrecisionFormatter(precision:String):PrecisionFormatter =
        when(precision) {
            "year" -> YearFormatter()
            "month" -> MonthFormatter()
            else -> DayFormatter()
        }
}