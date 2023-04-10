package ayds.winchester.songinfo.utils.formatter


class DayFormatter: PrecisionFormatter() {

    override fun formatWithPrecision(date:String):String=
        "${getDaySubstring(date)}/${getMonthSubstring(date)}/${getYearSubstring(date)}"

}