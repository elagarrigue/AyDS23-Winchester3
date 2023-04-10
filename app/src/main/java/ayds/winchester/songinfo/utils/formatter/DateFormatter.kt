package ayds.winchester.songinfo.utils.formatter

interface DateFormatter{
    fun format(date:String, precision:String):String
}

class DateFormatterImpl:DateFormatter{
    override fun format(date: String, precision: String): String {
        val formatter = PrecisionFormatterFactory.getPrecisionFormatter(precision)
        return formatter.formatWithPrecision(date)
    }
}
