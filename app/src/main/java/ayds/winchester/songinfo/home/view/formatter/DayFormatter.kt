package ayds.winchester.songinfo.home.view.formatter

class DayFormatter: PrecisionFormatter {

    override fun formatWithPrecision(date:String):String {
        val day = date.split("-")[2]
        val month = date.split("-")[1]
        val year = date.split("-")[0]
        return "$day/$month/$year"
    }
}