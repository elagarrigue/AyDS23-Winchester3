package ayds.winchester.songinfo.home.view.formatter

object PrecisionFormatterFactory {
    //Crear Enumerado
    fun getPrecisionFormatter(precision:String): PrecisionFormatter =
        when(precision) {
            "year" -> YearFormatter()
            "month" -> MonthFormatter()
            else -> DayFormatter()
        }
}