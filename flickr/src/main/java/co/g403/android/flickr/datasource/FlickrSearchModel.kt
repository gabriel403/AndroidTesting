package co.g403.android.flickr.datasource

import com.google.gson.annotations.SerializedName

object FlickrSearchModel {
	data class Response(
		@SerializedName("photos") val photos: Photos
	)

	data class Photos(
		@SerializedName("page") val page: Int,
		@SerializedName("pages") val pages: Int,
		@SerializedName("perpage") val perpage: Int,
		@SerializedName("total") val total: Int,
		@SerializedName("photo") val photos: List<SearchImage>
	)

	data class SearchImage(
		val id: String,
		val owner: String,
		val secret: String,
		val title: String,
		val isPublic: Boolean
	)
}
