package ayds.winchester.songinfo.home.view.formatter


class DayFormatter: PrecisionFormatter {

    override fun formatWithPrecision(date:String):String=
        "${getDaySubstring(date)}/${getMonthSubstring(date)}/${getYearSubstring(date)}"

    private fun getDaySubstring(date: String) = date.split("-")[2]
    private fun getMonthSubstring(date: String) = date.split("-")[1]
    private fun getYearSubstring(date: String) = date.split("-")[0]
}