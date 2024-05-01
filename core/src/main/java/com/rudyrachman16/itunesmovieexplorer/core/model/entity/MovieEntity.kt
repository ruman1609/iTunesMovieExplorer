package com.rudyrachman16.itunesmovieexplorer.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteMovie")
data class MovieEntity(
	val duration: String,
	val longDescription: String,
	val trackHdRentalPrice: String,
	val country: String,
	val previewUrl: String,
	val trackName: String,
	val collectionArtistViewUrl: String,
	val artworkUrl60: String,
	val currency: String,
	val trackExplicitness: String,
	val collectionViewUrl: String,
	val trackHdPrice: String,
	val contentAdvisoryRating: String,
	val releaseDate: String,
	val kind: String,
	@PrimaryKey val trackId: Long,
	val trackRentalPrice: String,
	val shortDescription: String,
	val primaryGenreName: String,
	val trackPrice: String,
	val collectionExplicitness: String,
	val trackViewUrl: String,
	val artworkUrl100: String,
	val trackCensoredName: String,
	val artistName: String
)
