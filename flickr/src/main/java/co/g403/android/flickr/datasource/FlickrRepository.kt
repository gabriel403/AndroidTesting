package co.g403.android.flickr.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class FlickrRepository(private val flickrService: FlickrService) {
	private val searchResults = MutableLiveData<List<FlickrImageModel.SizedImage>>()

	fun getSearchResults(): LiveData<List<FlickrImageModel.SizedImage>> {
		flickrService.searchImages()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{ result ->
					searchResults.value = result.photos.photos.map {FlickrImageModel.SizedImage(it.id)}
					Timber.i("got a result")
				},
				{ error: Throwable -> Timber.e(error) }
			)

		return searchResults
	}
}
