package co.g403.android.flickr.datasource

import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrService {
	@GET("?method=flickr.photos.search&format=json&nojsoncallback=1")
	fun searchImages(
		@Query("tags") tag: String = "kitten",
		@Query("page") page: Int = 1,
		@Query("api_key") apiKey: String = "f9cc014fa76b098f9e82f1c288379ea1"
	): Observable<FlickrSearchModel.Response>

	@GET("?method=flickr.photos.getSizes&format=json&nojsoncallback=1")
	fun image(
		@Query("photo_id") photoId: String,
		@Query("api_key") apiKey: String = "f9cc014fa76b098f9e82f1c288379ea1"
	): Observable<FlickrSearchModel.Response>

	companion object {
		fun create(): FlickrService {
			val interceptor = HttpLoggingInterceptor()
			interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
			val client =
				OkHttpClient.Builder().addInterceptor(interceptor).build()

			val retrofit = Retrofit.Builder()
				.client(client)
				.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl("https://api.flickr.com/services/rest/")
				.build()

			return retrofit.create(FlickrService::class.java)
		}
	}
}
