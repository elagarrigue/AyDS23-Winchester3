package ayds.winchester.songinfo.home.view.formatter

interface PrecisionFormatterFactory{
    fun get(precision: String): PrecisionFormatter
}
object PrecisionFormatterFactoryImpl:PrecisionFormatterFactory {
    override fun get(precision: String): PrecisionFormatter =
        when(precision) {
            "year" -> YearFormatter()
            "month" -> MonthFormatter()
            "day" -> DayFormatter()
            else -> DefaultFormatter()
        }
}