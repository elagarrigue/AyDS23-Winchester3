package ayds.winchester.songinfo.home.view

import ayds.winchester.songinfo.home.model.entities.Song.EmptySong
import ayds.winchester.songinfo.home.model.entities.Song
import ayds.winchester.songinfo.home.model.entities.Song.SpotifySong
import ayds.winchester.songinfo.utils.formatter.DateFormatter
interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl(val formatter:DateFormatter) : SongDescriptionHelper {
    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release date: ${song.getFormattedReleaseDate()}"
            else -> "Song not found"
        }
    }

    private fun SpotifySong.getFormattedReleaseDate() =
        dateFormatterFactory.get(this).format()
}