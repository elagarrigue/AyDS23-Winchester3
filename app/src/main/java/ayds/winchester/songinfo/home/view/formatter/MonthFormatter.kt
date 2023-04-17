package ayds.winchester.songinfo.home.view.formatter

class MonthFormatter: PrecisionFormatter {

    override fun formatWithPrecision(date:String):String{
        val year = date.split("-")[0]
        val monthNumber = date.split("-")[1]
        val monthName = getMonthName(monthNumber)
        return "$monthName, $year"
    }

    private fun getMonthName(monthNumber:String) =
        when(monthNumber){
            "01" -> "January"
            "02" -> "February"
            "03" -> "March"
            "04" -> "April"
            "05" -> "May"
            "06" -> "June"
            "07" -> "July"
            "08" -> "August"
            "09" -> "September"
            "10" -> "October"
            "11" -> "November"
            "12" -> "December"
            else -> ""
        }
}