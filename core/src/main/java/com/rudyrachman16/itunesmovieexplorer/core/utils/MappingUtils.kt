package com.rudyrachman16.itunesmovieexplorer.core.utils

import com.rudyrachman16.itunesmovieexplorer.core.model.Movie
import com.rudyrachman16.itunesmovieexplorer.core.model.entity.MovieEntity
import com.rudyrachman16.itunesmovieexplorer.core.model.response.MovieResponse
import com.rudyrachman16.itunesmovieexplorer.core.utils.StringUtils.getCountryCode2WordsOnly
import com.rudyrachman16.itunesmovieexplorer.core.utils.StringUtils.inMillisToString
import com.rudyrachman16.itunesmovieexplorer.core.utils.StringUtils.releaseDateToReadable
import com.rudyrachman16.itunesmovieexplorer.core.utils.StringUtils.toMoneyFormat

object MappingUtils {
    /**
     * Converting MovieResponse into Movie, for showing Movie from API Response
     * @see MovieResponse
     * @see Movie
     */
    fun MovieResponse.toMovie() = Movie(
        kind = this.kind ?: "-",
        artistName = this.artistName ?: "-",
        artworkUrl100 = this.artworkUrl100 ?: "-",
        artworkUrl60 = this.artworkUrl60 ?: "-",
        collectionArtistViewUrl = this.collectionArtistViewUrl ?: "-",
        collectionExplicitness = this.collectionExplicitness ?: "-",
        collectionViewUrl = this.collectionViewUrl ?: "-",
        country = (this.country ?: "-").getCountryCode2WordsOnly(),
        currency = this.currency ?: "-",
        duration = (this.trackTimeMillis ?: 0L).inMillisToString(),
        longDescription = this.longDescription ?: "-",
        previewUrl = this.previewUrl ?: "-",
        contentAdvisoryRating = this.contentAdvisoryRating ?: "-",
        primaryGenreName = this.primaryGenreName ?: "-",
        releaseDate = (this.releaseDate ?: "-").releaseDateToReadable(),
        shortDescription = this.shortDescription ?: "-",
        trackCensoredName = this.trackCensoredName ?: "-",
        trackHdPrice = (this.trackHdPrice ?: .0).toMoneyFormat(),
        trackExplicitness = this.trackExplicitness ?: "-",
        trackId = this.trackId ?: 0,
        trackName = this.trackName ?: "-",
        trackPrice = (this.trackPrice ?: .0).toMoneyFormat(),
        trackViewUrl = this.trackViewUrl ?: "-",
        trackHdRentalPrice = (this.trackHdRentalPrice ?: .0).toMoneyFormat(),
        trackRentalPrice = (this.trackRentalPrice ?: .0).toMoneyFormat(),
    )

    /**
     * Converting MovieEntity into Movie, for showing Movie from Database
     * @see MovieEntity
     * @see Movie
     */
    fun MovieEntity.toMovie() = Movie(
        kind = this.kind,
        artistName = this.artistName,
        artworkUrl100 = this.artworkUrl100,
        artworkUrl60 = this.artworkUrl60,
        collectionArtistViewUrl = this.collectionArtistViewUrl,
        collectionExplicitness = this.collectionExplicitness,
        collectionViewUrl = this.collectionViewUrl,
        country = this.country,
        currency = this.currency,
        duration = this.duration,
        longDescription = this.longDescription,
        previewUrl = this.previewUrl,
        contentAdvisoryRating = this.contentAdvisoryRating,
        primaryGenreName = this.primaryGenreName,
        releaseDate = this.releaseDate,
        shortDescription = this.shortDescription,
        trackCensoredName = this.trackCensoredName,
        trackHdPrice = this.trackHdPrice,
        trackExplicitness = this.trackExplicitness,
        trackId = this.trackId,
        trackName = this.trackName,
        trackPrice = this.trackPrice,
        trackViewUrl = this.trackViewUrl,
        trackHdRentalPrice = this.trackHdRentalPrice,
        trackRentalPrice = this.trackRentalPrice,
        isFavorite = true
    )

    /**
     * Converting Movie into MovieEntity, for saving to database purpose
     * @see Movie
     * @see MovieEntity
     */
    fun Movie.toMovieEntity() = MovieEntity(
        kind = this.kind,
        artistName = this.artistName,
        artworkUrl100 = this.artworkUrl100,
        artworkUrl60 = this.artworkUrl60,
        collectionArtistViewUrl = this.collectionArtistViewUrl,
        collectionExplicitness = this.collectionExplicitness,
        collectionViewUrl = this.collectionViewUrl,
        country = this.country,
        currency = this.currency,
        duration = this.duration,
        longDescription = this.longDescription,
        previewUrl = this.previewUrl,
        contentAdvisoryRating = this.contentAdvisoryRating,
        primaryGenreName = this.primaryGenreName,
        releaseDate = this.releaseDate,
        shortDescription = this.shortDescription,
        trackCensoredName = this.trackCensoredName,
        trackHdPrice = this.trackHdPrice,
        trackExplicitness = this.trackExplicitness,
        trackId = this.trackId,
        trackName = this.trackName,
        trackPrice = this.trackPrice,
        trackViewUrl = this.trackViewUrl,
        trackHdRentalPrice = this.trackHdRentalPrice,
        trackRentalPrice = this.trackRentalPrice,
    )
}