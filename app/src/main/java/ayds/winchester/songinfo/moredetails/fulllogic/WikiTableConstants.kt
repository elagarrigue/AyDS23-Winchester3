package ayds.winchester.songinfo.moredetails.fulllogic

const val ARTISTS_TABLE = "artists"
const val ID_COLUMN = "id"
const val ARTIST_COLUMN = "artist"
const val INFO_COLUMN = "info"

const val createSongsTableQuery: String =
    "create table $ARTISTS_TABLE (" +
            "$ID_COLUMN string PRIMARY KEY," +
            "$ARTIST_COLUMN string," +
            "$INFO_COLUMN string)"