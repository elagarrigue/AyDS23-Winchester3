package ayds.winchester.songinfo.home.view.formatter

abstract class PrecisionFormatter{
    abstract fun formatWithPrecision(date:String):String

    protected fun getDaySubstring(date: String) = date.takeLast(2)

    protected fun getMonthSubstring(date: String) = date.substring(5..6)

    protected fun getYearSubstring(date: String) = date.take(4)
}