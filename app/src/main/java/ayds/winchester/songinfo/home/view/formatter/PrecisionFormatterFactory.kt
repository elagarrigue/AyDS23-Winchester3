package ayds.winchester.songinfo.home.view.formatter

interface PrecisionFormatterFactory{
    fun getPrecisionFormatter(precision: String): PrecisionFormatter
}

private const val YEAR = "year"
private const val MONTH = "month"
private const val DAY = "day"

object PrecisionFormatterFactoryImpl:PrecisionFormatterFactory {
    override fun getPrecisionFormatter(precision: String): PrecisionFormatter =
        when(precision) {
            YEAR -> YearFormatter()
            MONTH -> MonthFormatter()
            DAY -> DayFormatter()
            else -> DefaultFormatter()
        }
}