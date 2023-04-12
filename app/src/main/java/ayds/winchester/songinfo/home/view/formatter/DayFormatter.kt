package ayds.winchester.songinfo.home.view.formatter


class DayFormatter: PrecisionFormatter() {

    override fun formatWithPrecision(date:String):String=
        "${getDaySubstring(date)}/${getMonthSubstring(date)}/${getYearSubstring(date)}"


    private fun getDaySubstring(date: String) = date.takeLast(2)
}