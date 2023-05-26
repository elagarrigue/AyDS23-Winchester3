package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb

const val ARTISTS_TABLE = "artists"
const val ID_COLUMN = "id"
const val ARTIST_COLUMN = "artist"
const val INFO_COLUMN = "info"
const val ARTIST_URL_COLUMN = "url"
const val SOURCE_COLUMN = "source"
const val SOURCE_LOGO_URL_COLUMN = "sourceLogoURL"

 val projection = arrayOf(
    ID_COLUMN,
    ARTIST_COLUMN,
    INFO_COLUMN,
    ARTIST_URL_COLUMN,
    SOURCE_COLUMN,
    SOURCE_LOGO_URL_COLUMN
)

//TODO Cambiar creacion de la tabla para incluir lo que falta
const val createArtistInfoTableQuery: String =
    "create table $ARTISTS_TABLE (" +
            "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$ARTIST_COLUMN string," +
            "$INFO_COLUMN string," + //description
            "$ARTIST_URL_COLUMN string," + //infoURL
            "$SOURCE_COLUMN string," + //source
            "$SOURCE_LOGO_URL_COLUMN string)" //sourceLogoURL

const val upgradeArtistTableQuery: String =
    "ALTER TABLE $ARTISTS_TABLE ADD COLUMN "+
            "$ARTIST_URL_COLUMN string"