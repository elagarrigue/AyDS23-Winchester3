package ayds.winchester.songinfo.moredetails.fulllogic.presentation.view

private const val WIKIPEDIA_LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"

//TODO cambiar el uiState para que mantenga una coleccion de cards con la descripcion formateada
data class MoredetailsUIState(
    val description: String = "",
    val urlOpenButton: String? = null,
    val urlImage: String = WIKIPEDIA_LOGO_URL,
)