package co.g403.android.flickr.datasource

import com.google.gson.annotations.SerializedName

object FlickrImageModel {
	data class Response(
		@SerializedName("sizes") val photos: Sizes
	)

	data class Sizes(
		@SerializedName("size") val photos: List<SizedImage>
	)

	data class SizedImage(
		val label: String,
		val width: String,
		val height: String,
		val source: String,
		val media: String
	)
}
