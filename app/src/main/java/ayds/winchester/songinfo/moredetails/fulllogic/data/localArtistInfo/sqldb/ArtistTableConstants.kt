package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb

const val ARTISTS_TABLE = "artists"
const val ID_COLUMN = "id"
const val ARTIST_COLUMN = "artist"
const val INFO_COLUMN = "info"
const val ARTIST_URL_COLUMN = "url"
const val SOURCE_COLUMN = "source"
const val SOURCE_LOGO_URL_COLUMN = "sourcelogourl"

 val projection = arrayOf(
    ID_COLUMN,
    ARTIST_COLUMN,
    INFO_COLUMN,
    ARTIST_URL_COLUMN,
    SOURCE_COLUMN,
    SOURCE_LOGO_URL_COLUMN
)

const val createArtistInfoTableQuery: String =
    "create table $ARTISTS_TABLE (" +
            "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$ARTIST_COLUMN string," +
            "$INFO_COLUMN string," +
            "$ARTIST_URL_COLUMN string," +
            "$SOURCE_COLUMN integer," +
            "$SOURCE_LOGO_URL_COLUMN string)"

const val upgradeArtistTableQuery: String =
    "ALTER TABLE $ARTISTS_TABLE ADD COLUMN "+
            "$ARTIST_URL_COLUMN string"