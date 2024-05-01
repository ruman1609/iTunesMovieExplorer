package com.rudyrachman16.itunesmovieexplorer.core.model.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(

	@field:SerializedName("trackTimeMillis")
	val trackTimeMillis: Long? = null,

	@field:SerializedName("longDescription")
	val longDescription: String? = null,

	@field:SerializedName("trackHdRentalPrice")
	val trackHdRentalPrice: Double? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("previewUrl")
	val previewUrl: String? = null,

	@field:SerializedName("trackName")
	val trackName: String? = null,

	@field:SerializedName("collectionArtistViewUrl")
	val collectionArtistViewUrl: String? = null,

	@field:SerializedName("artworkUrl60")
	val artworkUrl60: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("trackExplicitness")
	val trackExplicitness: String? = null,

	@field:SerializedName("collectionViewUrl")
	val collectionViewUrl: String? = null,

	@field:SerializedName("trackHdPrice")
	val trackHdPrice: Double? = null,

	@field:SerializedName("contentAdvisoryRating")
	val contentAdvisoryRating: String? = null,

	@field:SerializedName("releaseDate")
	val releaseDate: String? = null,

	@field:SerializedName("kind")
	val kind: String? = null,

	@field:SerializedName("trackId")
	val trackId: Long? = null,

	@field:SerializedName("trackRentalPrice")
	val trackRentalPrice: Double? = null,

	@field:SerializedName("shortDescription")
	val shortDescription: String? = null,

	@field:SerializedName("primaryGenreName")
	val primaryGenreName: String? = null,

	@field:SerializedName("trackPrice")
	val trackPrice: Double? = null,

	@field:SerializedName("collectionExplicitness")
	val collectionExplicitness: String? = null,

	@field:SerializedName("trackViewUrl")
	val trackViewUrl: String? = null,

	@field:SerializedName("artworkUrl100")
	val artworkUrl100: String? = null,

	@field:SerializedName("trackCensoredName")
	val trackCensoredName: String? = null,

	@field:SerializedName("artistName")
	val artistName: String? = null
)
